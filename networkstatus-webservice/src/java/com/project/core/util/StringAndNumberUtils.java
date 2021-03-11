/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.UUID;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Samrit
 */
public class StringAndNumberUtils {

    public static double getDoubleValueRoundingToUserDefinedDecimalPoints(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double getDoubleValueRoundingToGeoPointSpecificDecimalPoints(double value) {
        int places = 6;
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String getHtmlEncodeString(final String string) {
        final StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            final Character character = string.charAt(i);
            if (CharUtils.isAscii(character)) {

                // Encode common HTML equivalent characters
                stringBuffer.append(
                        StringEscapeUtils.escapeHtml4(character.toString()));
            } else {
                // Why isn't this done in escapeHtml4()?
                stringBuffer.append(
                        String.format("&#x%x;",
                                Character.codePointAt(string, i)));
            }
        }
        return stringBuffer.toString();
    }

    public static String getHtmlDecodeString(final String encodedString) {
        return StringEscapeUtils.unescapeHtml4(encodedString);

    }

    public static String generateRandomString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String generateRandomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

}
