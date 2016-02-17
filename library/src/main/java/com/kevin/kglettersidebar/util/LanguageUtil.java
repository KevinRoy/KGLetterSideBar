package com.kevin.kglettersidebar.util;

import java.util.ArrayList;

/**
 * 语言工具类
 * Created by kevin on 16/2/5.
 */
public class LanguageUtil {

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
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(inputString);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (token.type == HanziToPinyin.Token.PINYIN) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return isLowerCase == true ? sb.toString().toLowerCase() : sb.toString().toUpperCase();
    }
}
