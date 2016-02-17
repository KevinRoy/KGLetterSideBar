package com.kevin.kglettersidebar.util;

import java.text.Collator;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * HanziToPinyin工具类里面的
 * Locale locale[] = Collator.getAvailableLocales();
 * 因为Collator本来就是去调的icu (International Components for Unicode)
 * 我们可以通过Collator.getAvailableLocales()来查看有没有支持的语言
 * 但是每个手机又不尽相同, 想说爱你不容易啊
 * 先看看自带只能获取的locale
 * zh
 * zh_HANS 简体中文
 * zh_HANS_CN 大陆简体中文
 * zh_HANS_SG 新加坡简体中文
 * zh_HANT 繁体中文
 * zh_HANT_HK 香港繁体中文
 * zh_HANT_MO 澳门繁体中文
 * zh_HANT_TW 台湾繁体中文
 * <p/>
 * 目前只有这几款(HANS 简体  HANT 繁体)
 * <p/>
 * 这个类主要是适配一些机型获取的Locale,让它们来适配上面这几款
 * <p/>
 * <p/>
 * 根据各个手机获取的不同做出适配, 持续更新...
 * Created by kevin on 16/2/15.
 */
public class LanguageHelper {
    private static final String TAG = "LanguageHelper";
    /**
     * 是否支持简体中文
     *
     * @return
     */
    public static boolean isChinaCollator() {
        Locale[] localeArray = Collator.getAvailableLocales();
        if (localeArray.length > 0) {
            List<Locale> locales = Arrays.asList(localeArray);
            for (int i = 0; i < locales.size(); i++) {
                if (Locale.CHINA.equals(locales.get(i))) {
                    return true;
                }
            }
        }

        if (isAdaptationLocale()) {
            return true;
        }

        return false;
    }

    /**
     * 适配
     *
     * @return
     */
    private static boolean isAdaptationLocale() {
        boolean isAdaptation = false;
        if (Locale.getDefault().equals(Locale.CHINA)) {
            isAdaptation = true;
        }
        return isAdaptation;
    }
}
