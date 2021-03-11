/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author SAM
 */
public class Validate {

    public boolean isValidLocationName(String paramString) {
        String str1 = "[a-zA-Z0-9_\\-\\s]+";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidDefaultTextLength(String paramString) {
        try {
            int length = paramString.length();
            return length <= 200;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidTextLengthUnderSize(String paramString, int size) {
        try {
            int length = paramString.length();
            return length <= size;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidLatitude(String paramString) {
        try {
            Double latitude = Double.parseDouble(paramString);
            return latitude <= 90 && latitude >= -90;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidLongitude(String paramString) {
        try {
            Double longitude = Double.parseDouble(paramString);
            return longitude <= 180 && longitude >= -180;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidDistance(String paramString) {
        try {
            Double longitude = Double.parseDouble(paramString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidPhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{3,20}")) {
            return true;
        } //validating phone number with -, . or spaces
        else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        } //validating phone number with extension length from 3 to 5
        else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        } //validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        } //return false if nothing matches the input
        else {
            return false;
        }

    }

    public boolean isNULL(Object obj) {
        return obj == null;
    }

    public boolean userTMP(String paramString) {
        String str1 = "[a-zA-Z0-9][a-zA-Z0-9_\\-\\.]{0,49}";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean passwordTMP(String paramString) {
        String str1 = "[a-zA-Z0-9_\\-\\s!@#$%\\^&amp;\\*\\(\\)\\+=\\|\\\\{}\\[\\]:;\\<\\>,\\./\\?]+";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isEmptyString(String paramString) {
        boolean bool = false;
        if (paramString == null) {
            paramString = "";
        }
        if (paramString.trim().equals("")) {
            bool = true;
        }
        return bool;
    }

    public boolean isValidHostDomain(String paramString) {
        String str1 = "[a-zA-Z][a-zA-Z0-9_\\-]*(\\.[a-zA-Z][a-zA-Z0-9_\\-]*)*";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidIP(String paramString) {
        String str1 = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isIntegerAndGreaterThanZero(String integerString) {

        boolean status = false;
        try {
            int val = Integer.parseInt(integerString);
            if (val > 0) {
                status = true;
            }
        } catch (Exception e) {
            status = false;
        }
        return status;
    }

    public boolean isValidFilter(String paramString) {
        String str1 = "([a-zA-Z0-9\\*]+\\.(!=|=)?[a-zA-Z0-9\\*]+(;[a-zA-Z0-9\\*]+\\.(!=|=)?[a-zA-Z0-9\\*]+)*)|(:[a-zA-Z0-9]+[\\s]*,[\\s]*[!]?(contains|isequal|startswith|regex|ereregex)[\\s]*,[\\s]*\\\".+\\\")";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidProtocol(String paramString) {
        String str1 = "UDP|TCP";
        String str2 = paramString.toUpperCase();
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidIP_NET(String paramString) {
        String str1 = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)/(3[0-2]|[1-2][0-9]|[1-9])";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidIPWithorWithoutSubnet(String paramString) {
        String str1 = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(/(3[0-2]|[1-2][0-9]|[1-9]))?";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidPort(String paramString) {
        String str1 = "(6553[0-5]|655[0-2][0-9]|65[0-4][0-9][0-9]|6[0-4][0-9][0-9][0-9]|[0-5]?[0-9][0-9]?[0-9]?[0-9]?)";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidTemplate(String paramString) {
        String str1 = "[a-zA-Z0-9_\\-]*";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidID_VLAN(String paramString) {
        String str1 = "[0-9]{1,19}";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidIFname(String paramString) {
        String str1 = "[a-z]+[0-9]+";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidHostName(String paramString) {
        String str1 = "(?i)((l?o?c?a?l?h?o?s?t?)|(h?o?s?t?n?a?m?e?))";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean emptyString(String str) {
        boolean compare = false;
        if (str.trim().equals("")) {
            compare = true;
        }
        return compare;
    }

    public boolean DB(String paramString) {
        String str1 = "[a-zA-Z0-9_\\-]+";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidRADIUS_Timeout(String paramString) {
        return ID_VLAN(paramString);
    }

    private boolean ID_VLAN(String paramString) {
        String str1 = "[0-9]{1,19}";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidCalendarDate(String paramString) {
        String str1 = "[0-9]{4}-(1[0-2]|[0][0-9])-(3[0-1]|[0-2][0-9])";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        try {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            localSimpleDateFormat.setLenient(false);
            localSimpleDateFormat.parse(paramString);
        } catch (ParseException localParseException) {
            return false;
        }
        return bool;
    }

    public boolean isCommonBridgeInterface(String interface1, String interface2) {
        boolean compare = false;
        if (interface1.equalsIgnoreCase(interface2)) {
            compare = true;
        }
        return compare;
    }

    public boolean isBridgeInterfaceMembers(String interface1, String interface2, String interface3) {
        boolean compare = false;
        if (interface1.equalsIgnoreCase(interface3) || interface2.equalsIgnoreCase(interface3)) {
            compare = true;
        }
        return compare;
    }

    public boolean isValidEmail(String paramString) {
        String str1 = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public static void main(String args[]) {
        Validate v = new Validate();
        System.out.println(v.isValidIPWithorWithoutSubnet("1.2.3.4"));
    }

    public boolean isValidMyInteger(String paramString) {
        String str1 = "[0-9]{1,19}";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidFile(String paramString) {
        String str1 = "\\-?/([a-zA-Z0-9_\\-\\.]+/)*[a-zA-Z0-9_\\-\\.]+";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }

    public boolean isValidPercent(String paramString) {
        String str1 = "100|[0-9][0-9]|[0-9]";
        String str2 = paramString;
        Pattern localPattern = Pattern.compile(str1);
        Matcher localMatcher = localPattern.matcher(str2);
        boolean bool = localMatcher.matches();
        return bool;
    }
}
