package com.techzealot.collection.tree.segmenttree;

/**
 * Range Minimum/Maximum Query
 * 线段树以O(mlogn)解决RMQ问题(m是查询次数)
 * 但是如果m很大(一般1e6以上)，但是n比较小，就可以用ST算法解决RMQ问题
 * O(nlogn)预处理+O(1)查询
 * <p>
 * a[1…n]表示一组数,设f[i][j]表示从a[i]到a[i+2 j-1]这个范围内的最大值
 * 也就是以a[i]为起点连续2 j个数的最大值
 * 转移方程为: f[i][j]=max(f[i][j-1],f[i+(1<<j-1)][j-1])
 * 边界条件: f[i][0]=a[i]
 * 因此:
 * 若k为满足2^k<=r-l+1的最大值则:
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
    public SparseTable(int[] range) {
        //todo
    }

    /**
     * O(1)
     *
     * @param l
     * @param r
     * @return
     */
    public int max(int l, int r) {
        int k = (int) Math.log(r - l + 1.0);
        return Math.max(fmax[l][k], fmax[r - (k << 1) + 1][k]);
    }

    /**
     * O(1)
     *
     * @param l
     * @param r
     * @return
     */
    public int min(int l, int r) {
        int k = (int) Math.log(r - l + 1.0);
        return Math.min(fmin[l][k], fmin[r - k << 1 + 1][k]);
    }
}
