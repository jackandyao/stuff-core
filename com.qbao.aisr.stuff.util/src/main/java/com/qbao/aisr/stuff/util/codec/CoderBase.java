/*
 * Copyright (C), 2002-2016
 * FileName: CoderBase.java
 * Author:   luwanchuan
 * Date:     2016年4月14日 上午10:35:13
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.qbao.aisr.stuff.util.codec;

import java.nio.charset.Charset;

/**
 * 基础编码转换实现类
 *
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CoderBase {

    /** Hex值常量 */
    protected static final int OX0F = 0x0f;
    protected static final int OXF0 = 0xf0;
    protected static final int OX7F = 0x7F;
    protected static final int OX80 = 0x80;
    protected static final int OXFF = 0xFF;

    protected static final int BASE_2 = 2;
    protected static final int BASE_3 = 3;
    protected static final int BASE_4 = 4;
    protected static final int BASE_5 = 5;
    protected static final int BASE_6 = 6;
    protected static final int BASE_7 = 7;
    protected static final int BASE_8 = 8;
    protected static final int BASE_9 = 9;
    protected static final int BASE_10 = 10;
    protected static final int BASE_14 = 14;
    protected static final int BASE_15 = 15;
    protected static final int BASE_16 = 16;
    protected static final int BASE_19 = 19;
    protected static final int BASE_20 = 20;
    protected static final int BASE_24 = 24;
    protected static final int BASE_30 = 30;
    protected static final int BASE_32 = 32;
    protected static final int BASE_39 = 39;
    protected static final int BASE_40 = 40;
    protected static final int BASE_48 = 48;
    protected static final int BASE_55 = 55;
    protected static final int BASE_56 = 56;
    protected static final int BASE_59 = 59;
    protected static final int BASE_60 = 60;
    protected static final int BASE_63 = 63;
    protected static final int BASE_64 = 64;
    protected static final int BASE_79 = 79;
    protected static final int BASE_80 = 80;
    protected static final int BASE_127 = 127;
    protected static final int BASE_128 = 128;
    protected static final int BASE_255 = 255;

    static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private static final int NUM_8 = 8;

    /**
     * 十六进制（字符）转换为十进制（数字） 忽略大小写
     * 
     * 参数范围：'0'-'9', 'A'-'F'
     * 
     * @param c 十六进制字符(0-F)
     * @return 整形数字
     */
    public static int hexCharToInt(char c) {
        return (int) hexCharToByte(c);
    }

    /**
     * 十六进制（字符）转换为字节形式 忽略大小写
     * 
     * 参数范围：'0'-'9', 'A'-'F'
     * 
     * @param c 十六进制字符(0-F)
     * @return 字节
     */
    public static byte hexCharToByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(Character.toUpperCase(c));
        return b;
    }

    /**
     * 十进制（数字）转换为十六进制（字符串）
     * 
     * @param c 十六进制（数字） 0-16
     * @return
     */
    public static String baseIntToHex(int c) {
        char s = "0123456789ABCDEF".charAt(c);
        return String.valueOf(s);
    }

    /**
     * 10进制数字转换为16进制字符串
     * 
     * @param n 10进制数字
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String intToHex(int n) {
        StringBuilder sb = new StringBuilder();
        if (n / BASE_16 == 0) {
            return baseIntToHex(n);
        } else {
            String t = intToHex(n / BASE_16);
            int nn = n % BASE_16;
            sb.append(t).append(baseIntToHex(nn));
        }
        return sb.toString();
    }

    /**
     * 将16进制字符串转换为字节数组 有三种实现方式 1：Integer工具包中的方法 2：字节换算1 3：字节换算2
     * 
     * @param hexString
     * @return
     * @since 9.2
     */
    public static byte[] hexStringToBytes(String hex) {
        // 有三种实现方式：
        // 1：Integer.parseInt方式
        // hexStrToBytesByInteger1(hex)

        // 2: 基本原理
        // hexStrToBytesByBase1(hex);

        // 3: Integer.parseINt方式
        // hexStrToBytesByInteger2(hex);

        // 4: hexStrToBytesByBase2
        return hexStrToBytesByBase2(hex);
    }

    /**
     * 将16进制字符串转换为字节数组
     * 
     * @param val 字符串
     * @return
     * @since 9.2
     */
    private static byte[] hexStrToBytesByBase2(String hex) {
        String hexTemp = hex.toUpperCase();
        int len = hexTemp.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hexTemp.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            // 相邻两个字符转化成十六进制，前一个数左移4位，再与后一个数进行或运算，得到一个八位的字节
            result[i] = (byte) (hexCharToByte(achar[pos]) << BASE_4 | hexCharToByte(achar[pos + 1]));
        }
        return result;
    }

    // /**
    // * 将16进制字符串转换为字节数组
    // *
    // * @param strIn 需要转换的字符串
    // * @return 转换后的byte数组
    // * @throws Exception 本方法不处理任何异常，所有异常全部抛出
    // * @author LiGuoQing
    // */
    // public static byte[] hexStrToBytesByInteger2(String strIn) {
    // byte[] arrB = strIn.getBytes();
    // int iLen = arrB.length;
    //
    // // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
    // byte[] arrOut = new byte[iLen / 2];
    // for (int i = 0; i < iLen; i = i + 2) {
    // String strTmp = new String(arrB, i, 2);
    // arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
    // }
    // return arrOut;
    // }

    // /**
    // * 将16进制字符串转换为字节数组
    // *
    // * @param s
    // * @return
    // * @see [相关类/方法](可选)
    // * @since [产品/模块版本](可选)
    // */
    // private static final byte[] hexStrToBytesByInteger1(String s) {
    // byte[] bytes = new byte[s.length() / 2];
    //
    // for (int i = 0; i < bytes.length; i++) {
    // bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
    // }
    //
    // return bytes;
    // }
    // /**
    // * 将16进制字符串转换为字节数组
    // *
    // * @param val 字符串
    // * @return
    // * @since 9.2
    // */
    // private static byte[] hexStrToBytesByBase1(String val) {
    // byte[] interByte = val.getBytes();
    // byte[] result = new byte[interByte.length / 2];
    // for (int i = 0; i < result.length; i++) {
    // byte hi = interByte[i * 2];
    // byte lo = interByte[i * 2 + 1];
    // int h = hi > 0x40 ? 10 + hi - 0x41 : hi - 0x30;
    // int l = lo > 0x40 ? 10 + lo - 0x41 : lo - 0x30;
    // result[i] = (byte) (h << 4 | (l & 0x0f));
    // }
    // return result;
    // }

    /**
     * 字节数组转换为BCD码（十六进制字符串）
     * 
     * 有三种实现方式： 1：Integer工具包实现 2：Character工具包实现 3：字节转换
     * 
     * @param bArray
     * @return
     */
    public static String bytesToHexString(byte[] bArray) {
        // 1: Integer工具包实现
        return bytesToHexStringByInteger1(bArray);

        // 2: Integer 工具包实现
        // bytesToHexStringByInteger2(bArray);

        // 2: Character工具包实现
        // bytesToHexStringByCharacter(bArray);

        // 3：字节转换
        // bytesToHexStringByHex(bArray);
    }

    /**
     * 字节数组转换为BCD码（十六进制字符串）
     * 
     * @param bArray
     * @return
     */
    private static String bytesToHexStringByInteger1(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(OXFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    // /**
    // * 字节数组转换为BCD码（十六进制字符串）
    // *
    // * @param arrB 需要转换的byte数组
    // * @return 转换后的字符串
    // * @throws Exception 本方法不处理任何异常，所有异常全部抛出
    // */
    // public static String bytesToHexStringByInteger2(byte[] arrB) {
    // int iLen = arrB.length;
    // // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
    // StringBuffer sb = new StringBuffer(iLen * 2);
    // for (int i = 0; i < iLen; i++) {
    // int intTmp = arrB[i];
    // // 把负数转换为正数
    // while (intTmp < 0) {
    // intTmp = intTmp + 256;
    // }
    // // 小于0F的数需要在前面补0
    // if (intTmp < 16) {
    // sb.append(0);
    // }
    // sb.append(Integer.toString(intTmp, 16));
    // }
    // return sb.toString();
    // }

    // /**
    // * 字节数组转换为BCD码（十六进制字符串）
    // *
    // * @param bcd
    // * @return
    // */
    // private static final String bytesToHexStringByHex(byte[] bcd) {
    // StringBuffer s = new StringBuffer(bcd.length * 2);
    //
    // for (int i = 0; i < bcd.length; i++) {
    // s.append(HEX[(bcd[i] >>> 4 & 0xF)]);
    // s.append(HEX[(bcd[i] & 0xF)]);
    // }
    //
    // return s.toString();
    // }
    //
    // /**
    // * 字节数组转换为BCD码（十六进制字符串）
    // *
    // * @param val
    // * @return
    // */
    // private static String bytesToHexStringByCharacter(byte[] val) {
    // StringBuffer str = new StringBuffer(val.length * 2);
    // for (int i = 0; i < val.length; i++) {
    // char hi = Character.forDigit((val[i] >> 4) & 0x0f, 16);
    // char lo = Character.forDigit(val[i] & 0x0f, 16);
    // str.append(Character.toUpperCase(hi));
    // str.append(Character.toUpperCase(lo));
    // }
    // return str.toString();
    // }

    /**
     * 字节数组转换为十六进制编码字符数组
     * 
     * @param content
     * @return
     */
    public static char[] bytesToHexChars(byte[] content) {
        char[] out = new char[content.length << 1];
        int i = 0;
        int j = 0;
        final int maxLength = 240;
        final int byteLength = 4;
        final int lineLength = 15;
        for (; i < content.length; i++) {
            out[j++] = HEX[(maxLength & content[i]) >>> byteLength];
            out[j++] = HEX[lineLength & content[i]];
        }
        return out;
    }

    /**
     * 
     * 将字符串转换成二进制数组
     * 
     * @param source : 16进制字符串
     * @return 二进制数字数组
     */

    public static int[] hexStringToBinary(String source) {
        int len = source.length();
        int[] dest = new int[len * BASE_4];
        char[] arr = source.toCharArray();
        for (int i = 0; i < len; i++) {
            int t = 0;
            t = hexCharToInt(arr[i]);
            String[] str = Integer.toBinaryString(t).split("");
            int k = i * BASE_4 + BASE_4 - 1;
            for (int j = str.length - 1; j > 0; j--) {
                dest[k] = Integer.parseInt(str[j]);
                k--;
            }
        }
        return dest;
    }

    /**
     * 
     * 将字符串转换成二进制数组
     * 
     * @param source : 16进制字符串
     * @return 二进制数字数组
     */

    public static int[] bytesToBinary(byte[] source) {
        int[] dest = new int[source.length * BASE_8];
        for (int i = 0; i < source.length; i++) {
            String[] temp = Integer.toBinaryString(source[i]).split("");

            for (int k = 1; k < temp.length; k++) {
                dest[NUM_8 * i + k + NUM_8 - temp.length] = Integer.parseInt(temp[k]);
            }
        }
        return dest;
    }

    /**
     * 
     * 返回x的y次方
     * 
     * @param x
     * 
     * @param y
     * 
     * @return
     */

    private static int getXY(int x, int y) {
        int result = x;
        if (y == 0) {
            result = 1;
        }
        for (int i = 2; i <= y; i++) {
            result *= x;
        }
        return result;
    }

    /**
     * 
     * 位长度的二进制字符串
     * 
     * @param s
     * @return
     */
    public static String baseBinaryStrToHexString(String s) {
        int len = s.length();
        int result = 0;
        int k = 0;
        if (len > BASE_4) {
            return null;
        }
        for (int i = len; i > 0; i--) {
            result += Integer.parseInt(s.substring(i - 1, i)) * getXY(2, k);
            k++;
        }
        return Integer.toHexString(result).toUpperCase();
    }

    /**
     * 
     * 将二进制字符串转换成十六进制字符串
     * 
     * @param s
     * @return
     */

    public static String binaryStrToHexString(String s) {
        StringBuffer info = new StringBuffer("");
        int ii = 0;
        int len = s.length();
        String temp = s;
        // 不够4bit左补0
        if (len % BASE_4 != 0) {
            while (ii++ < BASE_4 - len % BASE_4) {
                temp = "0" + temp;
            }
        }
        for (int i = 0; i < len / BASE_4; i++) {
            info.append(baseBinaryStrToHexString(temp.substring(i * BASE_4, i * BASE_4 + BASE_4)));
        }
        return info.toString();
    }

    /**
     * BCD码转换为字符串
     * 
     * @param bytes BCD码
     * @return
     */
    public static String bcdToString(byte[] bytes) {

        char[] temp = new char[bytes.length * 2];
        char val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & OXF0) >> BASE_4) & OX0F);
            temp[i * 2] = (char) (val > BASE_9 ? val + 'A' - BASE_10 : val + '0');

            val = (char) (bytes[i] & OX0F);
            temp[i * 2 + 1] = (char) (val > BASE_9 ? val + 'A' - BASE_10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * ASCII码转BCD码
     * 
     * @param asc ASCII码
     * @return BCD 码
     * 
     */
    public static byte[] stringToBcd(String iasc) {
        String asc = iasc;
        final int hex = 0x0a;
        final int index = 4;

        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        if (len >= 2) {
            len = len / 2;
        }

        byte[] bbt = new byte[len];
        byte[] abt = asc.getBytes(Charset.defaultCharset());
        int j;
        int k;

        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + hex;
            } else {
                j = abt[2 * p] - 'A' + hex;
            }

            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + hex;
            } else {
                k = abt[2 * p + 1] - 'A' + hex;
            }

            int a = (j << index) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

}
