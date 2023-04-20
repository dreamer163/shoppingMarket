package com.sfh.shopping.util;

import java.util.List;

/**
 * 切片工具类
 */
public class SliceUtils {
    /**
     * 对一个集合进行切片
     *
     * @param target 目标集合
     * @param start  开始位置，包含
     * @param <T>    集合类型
     * @return 切片
     */
    public static <T> List<T> slice(List<T> target, int start, int count) {
        return target.stream().skip(start).limit(count).toList();
    }
}
