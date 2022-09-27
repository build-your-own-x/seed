package com.techzealot.collection.tree.segmenttree;

import lombok.NonNull;

import java.text.MessageFormat;

/**
 * Range Minimum/Maximum Query
 * 线段树以O(mlogn)解决RMQ问题(m是查询次数)
 * 但是如果m很大(一般1e6以上)，但是n比较小，就可以用ST算法解决RMQ问题
 * O(nlogn)预处理+O(1)查询
 * <p>
 * a[1…n]表示一组数,设f[i][j]表示从a[i]到a[i+2^j-1)]这个范围内的最大值
 * 也就是以a[i]为起点连续2^j个数的最大值
 * 转移方程为: f[i][j]=max(f[i][j-1],f[i+(1<<j-1)][j-1])
 * 边界条件: f[i][0]=a[i]
 * 因此:
 * 若k为满足以下两个条件:
 * (1)2^k<=r-l+1 保证f(l,k)不超过f(l,r)
 * (2)r-l+1<=2^(k+1) 保证f(l,k)超过f(l,r)的一半,使得两个子区间可以完全覆盖原区间
 * 的最大整数,
 * 整理以上两个条件可得 log2(r-l+1)-1<=k<=log2(r-l+1) 区间长度为1,一定包含一个整数
 * 当k=[log2(r-l+1)]时同时满足上述两个条件
 * 则:
 * f[l,r]=max(f[l,k],f[r-2^k+1,k])
 */
public class SparseTable {

    private int[][] fmax;
    private int[][] fmin;

    /**
     * O(nlogn)
     *
     * @param range
     */
    public SparseTable(@NonNull int[] range) {
        int len = range.length;
        if (len == 0) {
            throw new IllegalArgumentException("empty range");
        }
        //32位即可覆盖全部int可表示的整数范围
        int n = (int) Math.ceil(Math.log(len) / Math.log(2)) + 1;
        fmax = new int[len][n];
        fmin = new int[len][n];
        //设置初始值
        for (int i = 0; i < len; i++) {
            fmax[i][0] = range[i];
            fmin[i][0] = range[i];
        }
        //按列进行迭代
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < len; i++) {
                //防止越界
                if (i + (1 << (j - 1)) >= len) break;
                //按此种方式计算的f[i][j]可能会由于数组越界会在最后几列计算出错误的数据，但是这些错误数据不会使用到，因此无需处理
                fmax[i][j] = Math.max(fmax[i][j - 1], fmax[i + (1 << (j - 1))][j - 1]);
                fmin[i][j] = Math.min(fmin[i][j - 1], fmin[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    /**
     * O(1)
     *
     * @param l
     * @param r
     * @return
     */
    public int max(int l, int r) {
        //推导下k的计算
        int k = (int) (Math.log(r - l + 1.0) / Math.log(2));
        System.out.println(MessageFormat.format("[{0},{1}]=>[{2},{3}][{4},{5}]", l, r, l, k, r - (1 << k) + 1, k));
        //证明查询使用的数据满足条件:i + (1 << (j - 1)) < len
        //f(l,k)=max(f(l,k-1)),f(l+(1<<(k-1)),k-1)) : l+(1<<k-2)<=l+(1<<k-1)+(1<<k-2)<=l+(1<<k)-1<=r<length (k>=1)
        //f(r - (1 << k) + 1,k)=max(f(r - (1 << k) + 1,k-1),f(r - (1 << k) + 1+(1<<k-1),k-1)) : r - (1 << k) + 1+(1<<k-1) +(1<<(k-2))= r-(2^k -1 -2^(k-1)-2^(k-2))<=r<length
        //由上述证明可得查询时不会用到因默认值产生的不正确部分数据
        return Math.max(fmax[l][k], fmax[r - (1 << k) + 1][k]);
    }

    /**
     * O(1)
     *
     * @param l
     * @param r
     * @return
     */
    public int min(int l, int r) {
        int k = (int) (Math.log(r - l + 1.0) / Math.log(2));
        return Math.min(fmin[l][k], fmin[r - (1 << k) + 1][k]);
    }
}
