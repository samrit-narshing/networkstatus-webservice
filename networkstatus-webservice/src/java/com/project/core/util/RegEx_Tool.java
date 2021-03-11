/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.util;

/**
 *
 * @author Samrit
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx_Tool {

    public String capture(String paramString1, String paramString2) {
        return capture(paramString1, paramString2, 1);
    }

    public String capture(String paramString1, String paramString2, int paramInt) {
        String str1 = paramString1;
        String str2 = paramString2;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        if (bool) {
            return localMatcher.group(paramInt);
        }
        return null;
    }

    public String replace(String paramString1, String paramString2, String paramString3) {
        String str1 = paramString1;
        String str2 = paramString2;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        return localMatcher.replaceAll(paramString3);
    }

    public static void main(String args[]) {
        RegEx_Tool tool = new RegEx_Tool();
        String text = tool.replace("(\\$EXT_NIC)", "nat on $EXT_NIC from $INT_NIC:network to any -> ($EXT_NIC)", "\\$EXT_NIC");
        System.out.println(text);
    }
}
