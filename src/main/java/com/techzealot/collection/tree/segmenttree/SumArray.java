package com.techzealot.collection.tree.segmenttree;

import lombok.NonNull;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 可以比线段树更高效解决元素不更新时的区间求和问题，更新效率太低
 */
public class SumArray implements RangeOperation<Integer> {

    private final int[] data;
    /**
     * sum[i]表示<i的元素之和则query(i,j)=sum[j+1]-sum[i]
     * 若sum[i]表示<=i的元素之和则query(i,j)=sum[j]-sum[i-1],该表达式在i==0时不成立,无法统一计算逻辑
     */
    private Integer[] sum;

    public SumArray(@NonNull int[] data) {
        int length = data.length;
        if (length == 0) {
            throw new IllegalArgumentException("array is empty");
        }
        this.data = Arrays.copyOf(data, length);
        sum = new Integer[length + 1];
        sum[0] = 0;
        for (int i = 1; i <= length; i++) {
            sum[i] = sum[i - 1] + data[i - 1];
        }
    }

    /**
     * O(n)
     *
     * @param index
     * @param e
     */
    @Override
    public void update(int index, Integer e) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException();
        }
        data[index] = e;
        int length = data.length;
        for (int i = index + 1; i <= length; i++) {
            sum[i] = sum[i - 1] + data[i - 1];
        }
    }

    /**
     * O(1)
     *
     * @param i
     * @param j
     * @return
     */
    @Override
    public Integer query(int i, int j) {
        if (i < 0 || i >= data.length || j < 0 || j >= data.length || i > j) {
            throw new IllegalArgumentException(MessageFormat.format("illegal range[{0},{1}]", i, j));
        }
        return sum[j + 1] - sum[i];
    }
}
