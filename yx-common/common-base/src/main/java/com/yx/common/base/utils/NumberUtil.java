package com.yx.common.base.utils;

import org.springframework.util.Assert;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2019/1/10
 * @time 10:12 AM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class NumberUtil {

    private static final String upperCaseChar = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lowerCaseChar = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final String fixedLowerCaseChar = "23456789abcdefghijkmnpqrstuvwxyz";
    private static String[] units =
            {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "万亿"};
    private static char[] numArray = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};


    /**
     * 获取6位验证码
     *
     * @return
     */
    public static String getVerifyCode() {
        return new Random().nextInt(899999) + 100000+"";
    }

    /**
     * 获取6位验证码
     *
     * @return
     */
    public static String getVerifyCode2() {
        return randomString(6);
    }

    /**
     * 获取指定位数的的string随机数，随机范围为a-z A-Z 0-9
     *
     * @param length string的长度
     * @return 指定lenght的随机字符串
     */
    public static String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();

    }

    /**
     * 获取指定位数的随机数串，随机范围为0-9
     *
     * @param length string的长度
     * @return 指定lenght的随机字符串
     */
    public static String randomNumStr(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(10);
            buf.append(str.charAt(num));
        }
        return buf.toString();

    }

    /**
     * 获取指定位数的随机密码，随机范围为a-z A-Z 0-9 + 特殊字符
     *
     * @param length
     * @return
     */
    public static String randomPassword(int length) {
        String str = "I?DzB/jTmZf|P>ru`p%_v79EJ-=oG+[kx!Hwt)O5;.WsU2(*c{l~}gX0C$N:QFb^YS&,aM6VKin@]y#8'R4d3LqAe1<h";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(92);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    /**
     * 保留2位小数
     * @param num
     * @return
     */
    private static String getTwoDecimalNumber(Double num) {
        //方式1：转换方便（#.00会在小于1时丢失整数位的0）
        DecimalFormat df = new DecimalFormat("0.00");

        //方式2：打印方便
//        System.out.println("2===>>"+String.format("%.2f", num));

        //方式3：BigDecimal可以控制精度
//        BigDecimal bg = new BigDecimal(num);
//        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return df.format(num);
    }
    /**
     * 数字转汉字
     *
     * @param num
     * @return
     */
    public static String numToChinese(int num) {
        char[] val = String.valueOf(num).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < val.length; i++) {
            if (val[i] - 48 == 0) {
                if (sb.length() == 0 || sb.lastIndexOf("零") != sb.length() - 1) {
                    sb.append(numArray[0]);
                }
            } else {
                sb.append(numArray[val[i] - 48]);
                sb.append(units[len - i - 1]);
            }
        }
        if (sb.indexOf("一十") == 0) {
            sb.deleteCharAt(0);
        }
        if (sb.length() > 1 && sb.indexOf("零") == sb.length() - 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 将数字转换成小写字符串
     *
     * @param num
     * @param len
     * @return
     */
    public static String numToLowerString(long num, int len) {
        StringBuilder sb = new StringBuilder();
        Assert.state(num > 0,"数字要大于0");
        while (num > 0 && sb.length() < len) {
            sb.insert(0, lowerCaseChar.charAt((int) (num % lowerCaseChar.length())));
            num = num / lowerCaseChar.length();
        }
        int length = sb.length();
        for (int i = 0; i < len - length; i++) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    public static String numToFixedLowerString(long num, int len) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.insert(0, fixedLowerCaseChar.charAt((int) (num % fixedLowerCaseChar.length())));
            num = num / fixedLowerCaseChar.length();
        }
        int length = sb.length();
        for (int i = 0; i < len - length; i++) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    /**
     * numToLowerString 的反函数
     */
    public static long lowerStringToNum(String lowerString) {

        long total = 0L;
        for (char c : lowerString.toCharArray()) {
            int charIndex = lowerCaseChar.indexOf(c);
            if (lowerCaseChar.indexOf(c) == -1) {
                throw new IllegalArgumentException("不合法字串: " + lowerString);
            }

            total *= lowerCaseChar.length();
            total += charIndex;
        }

        return total;
    }

    /**
     * 将数字转换成大写字符串
     *
     * @param num
     * @param len
     * @return
     */
    public static String numToUpperString(long num, int len) {
        StringBuilder sb = new StringBuilder();
        while (num > 0 && sb.length() < len) {
            sb.insert(0, upperCaseChar.charAt((int) (num % upperCaseChar.length())));
            num = num / upperCaseChar.length();
        }
        int length = sb.length();
        for (int i = 0; i < len - length; i++) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    /**
     * 获取修正后的字符
     *
     * @param str
     * @param len
     * @return
     */
    public static String getFixedLowerString(String str, int len) {
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        while (sb.length() < len && idx < str.length()) {
            if (fixedLowerCaseChar.contains(str.charAt(idx) + "")) {
                sb.append(str.charAt(idx));
            }
            idx++;
        }
        int length = sb.length();
        for (int i = 0; i < len - length; i++) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    /**
     * 将数字转换成6位大写字符串
     *
     * @param num
     * @return
     */
    public static String numToSixUpperString(long num) {
        return numToUpperString(num, 6);
    }

    /**
     * 将数字转换成6位小写字符串
     *
     * @param num
     * @return
     */
    public static String numToSixLowerString(long num) {
        return numToLowerString(num, 6);
    }

    public static void main(String[] args) {
        getTwoDecimalNumber(1234.0);
        System.out.println("密码===>>"+randomPassword(6));
        System.out.println("随机数===>>"+randomString(6));
        System.out.println("6位大写===>>"+numToSixUpperString(System.currentTimeMillis()));
        System.out.println("6位小写===>>"+numToSixLowerString(System.currentTimeMillis()));
    }
}
