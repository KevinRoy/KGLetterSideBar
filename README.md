# KGLetterSideBar

有时候遇到侧边栏这种拼音列表方面，之前做过，但是没有系统拿出来，经常又重新做一遍感觉比较麻烦，现在想单独提出来试试，但是感觉没有把这个控件系统化，等后面慢慢修改和完善，能支持**Listview**和**Recycleview**两款，先走好第一步吧

**KGLetterSideBar**用法很简单, 网上也挺多且大同小异
```java
 <com.kevin.kglettersidebar.KGLetterSideBar
        android:layout_width="20dp"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true"
        app:textNormalColor="@color/colorPrimary"
        app:textPressColor="@color/orange" />
```
目前只有三个属性
> * textNormalColor 字母normal的字体颜色
> * textPressColor 字母按下的字体颜色
> * bg 字母条的背景

**KGLetterSideBar**主要有两个方法:
### 1. 设置字母表滑动点击
```java
KGLetterSideBar.setOnTouchingLetterChangedListener(new KGLetterSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
            }
        });
```
### 2. 设置显示在中央的字母的样式（目前只支持设置一个textView进去）
```java
KGLetterSideBar.setTextView(dialog)
```

而获取一个汉字的拼音，我这边用的也是网上提到的一个方法，通过copy了一份**HanziToPinyin**类，主要就是转化汉语拼音的。但是我转了半天没有转出一个所以然，通过debug发现了其中一个问题:
```java
Locale[] localeArray = Collator.getAvailableLocales();
        if (localeArray.length > 0) {
            List<Locale> locales = Arrays.asList(localeArray);
            for (int i = 0; i < locales.size(); i++) {
                if (Locale.CHINA.equals(locales.get(i))) {
                    return true;
                }
            }
        }
```

因为Collator本来就是去调的icu (International Components for Unicode)
我们可以通过Collator.getAvailableLocales()来查看有没有支持的语言
但是每个手机又不尽相同, 想说爱你不容易啊
先看看自带只能获取的locale
和中文相关的就一下这些:
 > * zh
 > * zh_HANS 简体中文
 > * zh_HANS_CN 大陆简体中文
 > * zh_HANS_SG 新加坡简体中文
 > * zh_HANT 繁体中文
 > * zh_HANT_HK 香港繁体中文
 > * zh_HANT_MO 澳门繁体中文
 > * zh_HANT_TW 台湾繁体中文

目前只有这几款(HANS 简体  HANT 繁体)

这个类主要是适配一些机型获取的Locale,让它们来适配上面这几款,所以这个工具类会一直持续更新...
作者这边用的是6.0系统的nexus5，因为使用Locale.getDefault() = zh_CN,但是上面没有的，所以特地在判断里面添加了这么一句
```java
if (Locale.getDefault().equals(Locale.CHINA)) {
        }
```

现在这个view还不够完善，等后面完善了希望能做到易扩展的一个view

# License
```
The MIT License (MIT)

Copyright (c) 2016 KevinRoy

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

