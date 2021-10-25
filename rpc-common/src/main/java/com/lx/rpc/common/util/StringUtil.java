package com.lx.rpc.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description:字符串工具类
 * @Author: LinXin_
 * @CreateTime: 2021/10/25 13:14
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if(str != null) {
           str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 分割固定格式的字符串
     */
    public static String[] split(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }
}
