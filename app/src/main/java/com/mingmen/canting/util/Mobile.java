package com.mingmen.canting.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class Mobile {
    /**
     * 手机号验证
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
//        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        p= compile("^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$");

        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
