package com.kevin.kglettersidebar.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 语言工具类
 * Created by kevin on 16/2/5.
 */
public class LanguageUtil {

    private static StringBuilder sb;

    /**
     * 获取字体的拼音 默认是小写
     *
     * @param inputString
     * @return
     */
    public static String getPinYin(String inputString) {
        return getPinYin(inputString, true);
    }

    /**
     * 获取字体的拼音 可选择大小写
     *
     * @param inputString
     * @param isLowerCase true 表示小写， false 表示大写
     * @return
     */
    public static String getPinYin(String inputString, boolean isLowerCase) {
        if (isLetter(inputString) || isHanZi(inputString)) {
            ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(inputString);
            sb = new StringBuilder();
            if (tokens != null && tokens.size() > 0) {
                for (HanziToPinyin.Token token : tokens) {
                    if (token.type == HanziToPinyin.Token.PINYIN) {
                        sb.append(token.target);
                    } else {
                        sb.append(token.source);
                    }
                }
            }
        } else {
            return "#";
        }
        return isLowerCase == true ? sb.toString().toLowerCase() : sb.toString().toUpperCase();
    }

    private static boolean isLetter(String s) {
        if (TextUtils.isEmpty(s))
            return false;

        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isHanZi(String s) {
        if (TextUtils.isEmpty(s))
            return false;

        char c = s.charAt(0);
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
}
