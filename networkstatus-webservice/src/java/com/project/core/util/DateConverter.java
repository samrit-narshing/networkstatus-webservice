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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author SSC-Client
 */
public class DateConverter {

    public static int getHourfromUnixTIme(long unixTime) {
        DateTime jodaTime = new DateTime(unixTime * 1000L);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH");
        int hour = Integer.parseInt(formatter.print(jodaTime));
        return hour;
    }

    //get unixdate from string date of format yyyy-MM-dd HH:mm:ss
    public static long getLocalUnixTimeFromTimeZoneStringFormat(String dateFormat) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTime d1 = formatter.parseDateTime(dateFormat);
            long convertedUnixTime = d1.getMillis() / 1000L;
            return convertedUnixTime;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getLocalTimeZoneID() {
        DateTimeZone dtz = DateTimeZone.getDefault();
        return dtz.getID();
    }

    //For DateConversion from localunixdate to timezone format to make it input for Search
    //Output format >>>> yyyy-MM-dd'T'HH:mm:ss
    public static String getLocalTimeZoneStringFormatFromUnixDate(final int unixDateTime) {
        try {
            // System.out.println("For Format yyyy-MM-dd'T'HH:mm:ss");
            long timestamp = new Long(unixDateTime);
            timestamp = timestamp * 1000L;
            Date dates = new Date(timestamp);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return format.format(dates);
        } catch (Exception e) {
            return "invalid date";
        }
    }

    public static String getUTCTimeZoneStringFormatFromUnixDate(final int unixDateTime) {

        final DateTimeZone fromTimeZone;
        final DateTimeZone toTimeZone;

        fromTimeZone = DateTimeZone.getDefault();
        toTimeZone = DateTimeZone.forID("UTC");

        final DateTime dateTime = new DateTime(unixDateTime * 1000L, fromTimeZone);

        final DateTimeFormatter outputFormatter
                = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(toTimeZone);
        return outputFormatter.print(dateTime);
    }

    public static long getLocalUnixTimeFromUTCTimeZoneStringFormat(String dateFormat) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            DateTime d1 = formatter.parseDateTime(dateFormat);
            long convertedUnixTime = DateTimeZone.getDefault().convertUTCToLocal(d1.getMillis()) / 1000L;
            return convertedUnixTime;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getConvertedDateAndTimeFromUnixDate(String unixDateTime) {
        try {
            long timestamp = new Long(unixDateTime);
            //  javax.swing.JOptionPane.showMessageDialog(null, "Unix Time Stamp" + timestamp);
            timestamp = timestamp * 1000L;
            //javax.swing.JOptionPane.showMessageDialog(null, "Java Time Stamp" + timestamp);
            Date dates = new Date(timestamp);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(dates);
        } catch (Exception e) {
            return "invalid date";
        }
    }

    public static String getConvertedDateAndTimeFromUnixDate_Full(String unixDateTime) {
        try {
            long timestamp = new Long(unixDateTime);
            //  javax.swing.JOptionPane.showMessageDialog(null, "Unix Time Stamp" + timestamp);
            //javax.swing.JOptionPane.showMessageDialog(null, "Java Time Stamp" + timestamp);
            Date dates = new Date(timestamp);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return format.format(dates);
        } catch (Exception e) {
            return "invalid date";
        }
    }

    public static String getConvertedDateFromUnixDate(String unixDateTime) {
        try {
            long timestamp = new Long(unixDateTime);
            //  javax.swing.JOptionPane.showMessageDialog(null, "Unix Time Stamp" + timestamp);
            timestamp = timestamp * 1000L;
            //javax.swing.JOptionPane.showMessageDialog(null, "Java Time Stamp" + timestamp);
            Date dates = new Date(timestamp);
            DateFormat format = new SimpleDateFormat("MMM, dd");
            return format.format(dates);
        } catch (Exception e) {
            return "invalid date";
        }
    }

    public static String getConvertedDateAndTimeInUnixDate(Date date) {
        int filter_val_date = (int) (date.getTime() / 1000L);
        return new Integer(filter_val_date).toString();
    }

    public static Long getConvertedDateAndTimeInUnixDateAddDays(int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_YEAR, day);
        long filter_val_date = (cal.getTimeInMillis() / 1000L);
        return filter_val_date;
    }

    public static Long getConvertedDateAndTimeEndInUnixDateAddDays(int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        cal.add(Calendar.DAY_OF_YEAR, day);
        long filter_val_date = (cal.getTimeInMillis() / 1000L);
        return filter_val_date;
    }

    public static Long getConvertedDateAndTimeInUnixDateAddMinute(int minute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minute);
        long filter_val_date = (cal.getTimeInMillis() / 1000L);
        return filter_val_date;
    }

    public static Long getCurrentConvertedDateAndTimeInUnixDate() {
        Calendar cal = Calendar.getInstance();
        long filter_val_date = (cal.getTimeInMillis() / 1000L);
        return filter_val_date;
    }

    public static Long getCurrentConvertedDateInUnixDate() {
        //Calendar cal = Calendar.getInstance();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long filter_val_date = (cal.getTimeInMillis() / 1000L);
        return filter_val_date;
    }

    public static String getRtIndexName_AddHour(int addHour) {
        StringBuilder builder = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        DateFormat dayNameFormat = new SimpleDateFormat("EEEE");
        DateFormat currentHourFormat = new SimpleDateFormat("H");
        int hour = getSignInteger(addHour);
        for (int i = 0; i <= Math.abs(addHour); i++) {
            // condition for including current hour
            if (i != 0) {
                cal.add(Calendar.HOUR, hour);
            }
            Date todayDate = new Date(cal.getTimeInMillis());
            String dateHour = dayNameFormat.format(todayDate) + currentHourFormat.format(todayDate);
            builder.append(dateHour.toLowerCase());
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    private static int getSignInteger(int i) {
        if (i == 0) {
            return 0;
        }
        if (i >> 31 != 0) {
            return -1;
        }
        return +1;
    }

    private static int getSignLong(long i) {
        if (i == 0) {
            return 0;
        }
        if (i >> 63 != 0) {
            return -1;
        }
        return +1;
    }

    private static int getSignInteger(double f) {
        if (f != f) {
            throw new IllegalArgumentException("NaN");
        }
        if (f == 0) {
            return 0;
        }
        f *= Double.POSITIVE_INFINITY;
        if (f == Double.POSITIVE_INFINITY) {
            return +1;
        }
        if (f == Double.NEGATIVE_INFINITY) {
            return -1;
        }

        //this should never be reached, but I've been wrong before...
        throw new IllegalArgumentException("Unfathomed double");
    }

    public static String getRtIndexName_CurrentDay(boolean wholeDay) {
        StringBuilder builder = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        DateFormat dayNameFormat = new SimpleDateFormat("EEEE");
        DateFormat currentHourFormat = new SimpleDateFormat("H");
        Date todayDate = new Date(cal.getTimeInMillis());
        int hour = 23;
        if (!wholeDay) {
            int currenthour = Integer.parseInt(currentHourFormat.format(todayDate));
            hour = currenthour;
        }
        for (int i = 0; i <= hour; i++) {

            String dateHour = dayNameFormat.format(todayDate) + i;
            builder.append(dateHour.toLowerCase());
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    public static String getRtIndexName_SelectedDay(long date) {
        StringBuilder builder = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date * 1000L);
        DateFormat dayNameFormat = new SimpleDateFormat("EEEE");
        Date todayDate = new Date(cal.getTimeInMillis());
        int hour = 23;
        for (int i = 0; i <= hour; i++) {
            String dateHour = dayNameFormat.format(todayDate) + i;
            builder.append(dateHour.toLowerCase());
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    public static Long getStartDate_UnixDate(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date * 1000L);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 00);
        long filter_val_date = (cal.getTimeInMillis() / 1000L);
        return filter_val_date;
    }

    public static Long getEndDate_UnixDate(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date * 1000L);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        long filter_val_date = (cal.getTimeInMillis() / 1000L);
        return filter_val_date;
    }

    public static Long getStartDate_CurrentDay_UnixDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 00);
        long filter_val_date = (cal.getTimeInMillis() / 1000L);
        return filter_val_date;
    }

    public static Long getEndDate_CurrentDay_UnixDate() {
        Calendar cal = Calendar.getInstance();
        //  cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        long filter_val_date = (cal.getTimeInMillis() / 1000L);
        return filter_val_date;
    }

//    public static String getRtIndexName_RangeDay(long fromdate, long todate) {
//
//        StringBuilder builder = new StringBuilder();
//
//        if (todate > fromdate) {
//            Calendar cal = Calendar.getInstance();
//            cal.setTimeInMillis(fromdate * 1000L);
//            DateFormat dayNameFormat = new SimpleDateFormat("EEEE");
//            Date todayDate = new Date(cal.getTimeInMillis());
//            int hour = 23;
//            for (int i = 0; i <= hour; i++) {
//                String dateHour = dayNameFormat.format(todayDate) + i;
//                builder.append(dateHour.toLowerCase());
//                builder.append(" ");
//            }
//        }
//        return builder.toString().trim();
//    }
    public static String getRtDayName_SelectedDay(long date) {
        String day = "";
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date * 1000L);
        DateFormat dayNameFormat = new SimpleDateFormat("EEEE");
        Date todayDate = new Date(cal.getTimeInMillis());
        day = dayNameFormat.format(todayDate).toLowerCase();
        return day.trim();
    }

    public static String getRtIndexName_RangeDayHour(long startDate, long endDate, int fromHour, int toHour) {
        StringBuilder builder = new StringBuilder();
        int fromHourLocal = Math.abs(fromHour) <= 23 ? Math.abs(fromHour) : 23;
        int toHourLocal = Math.abs(toHour) <= 23 ? Math.abs(toHour) : 23;
        DateMidnight fromdate = new DateMidnight(startDate * 1000);
        DateMidnight todate = new DateMidnight(endDate * 1000);
        List<Long> dates = new ArrayList<Long>();
        int days = Days.daysBetween(fromdate, todate).getDays();

        if (days <= 6 && startDate != 0 && endDate != 0) {
            for (int i = 0; i <= days; i++) {
                DateMidnight d = fromdate.withFieldAdded(DurationFieldType.days(), i);
                dates.add(d.getMillis() / 1000);
            }
            if (dates.size() == 1) {
                if (toHourLocal >= fromHourLocal) {
                    for (int s = fromHourLocal; s <= toHourLocal; s++) {
                        builder.append(getRtDayName_SelectedDay(dates.get(0)) + s);
                        builder.append(" ");
                    }
                } else {
//                    builder.append(getRtDayName_SelectedDay(dates.get(0)) + Math.min(fromHourLocal, toHourLocal));
//                    builder.append(" ");

                    for (int m = 0; m <= Math.min(fromHourLocal, toHourLocal); m++) {
                        builder.append(getRtDayName_SelectedDay(dates.get(0)) + m);
                        builder.append(" ");
                    }
                }
            } else {
                for (int i = 0; i < dates.size(); i++) {
                    if (i == 0) {
                        for (int start = fromHourLocal; start <= 23; start++) {
                            builder.append(getRtDayName_SelectedDay(dates.get(i)) + start);
                            builder.append(" ");
                        }
                        builder.trimToSize();
                    } else if (i == dates.size() - 1) {
                        for (int end = 0; end <= toHourLocal; end++) {
                            builder.append(getRtDayName_SelectedDay(dates.get(i)) + end);
                            builder.append(" ");
                        }
                        builder.trimToSize();
                    } else {
                        builder.append(getRtIndexName_SelectedDay(dates.get(i)));
                        builder.append(" ");
                    }
                }
            }
        } else {
            builder.append("Syslog");
        }
        return builder.toString().trim();
    }

    public static String getCurrentDate() {
        String str7 = "en";
        String str8 = "US";
        Locale localLocale = new Locale(str7, str8);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", localLocale);
        long l1 = Calendar.getInstance().getTimeInMillis();
        String str9 = localSimpleDateFormat.format(Long.valueOf(l1));
        return str9;

    }

    public static String getCurrentMonth() {
        String str7 = "en";
        String str8 = "US";
        Locale localLocale = new Locale(str7, str8);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("M", localLocale);
        long l1 = Calendar.getInstance().getTimeInMillis();
        String str9 = localSimpleDateFormat.format(Long.valueOf(l1));
        return str9;
    }

    public static String getCurrentYear() {
        String str7 = "en";
        String str8 = "US";
        Locale localLocale = new Locale(str7, str8);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy", localLocale);
        long l1 = Calendar.getInstance().getTimeInMillis();
        String str9 = localSimpleDateFormat.format(Long.valueOf(l1));
        return str9;
    }

    public static long getLastDateOfMonth(int year, int month) {
        month = month - 1;
        Calendar calendar = new GregorianCalendar(year, month, Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long filter_val_date = (calendar.getTimeInMillis() / 1000L);
        return filter_val_date;

    }

    public static long getFirstDateOfMonth(int year, int month) {
        month = month - 1;
        Calendar calendar = new GregorianCalendar(year, month, Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long filter_val_date = (calendar.getTimeInMillis() / 1000L);
        return filter_val_date;
    }

    public static long getLastDateOfMonth_Full(int year, int month) {
        month = month - 1;
        Calendar calendar = new GregorianCalendar(year, month, Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long filter_val_date = (calendar.getTimeInMillis());
        return filter_val_date;

    }

    public static long getFirstDateOfMonth_Full(int year, int month) {
        month = month - 1;
        Calendar calendar = new GregorianCalendar(year, month, Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long filter_val_date = (calendar.getTimeInMillis());
        return filter_val_date;
    }

    public static String getStringYearMonth(int year, int month) {
        try {

            month = month - 1;
            Calendar calendar = new GregorianCalendar(year, month, Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            long filter_val_date = (calendar.getTimeInMillis());

            Date dates = new Date(filter_val_date);
            DateFormat format = new SimpleDateFormat("yyyy-MMMM");
            return format.format(dates);
        } catch (Exception e) {
            return "invalid date";
        }
    }

    public static boolean validDateForFtp(String stringDate, int numberOfDays) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime fileDateTime = formatter.parseDateTime(stringDate);
        DateTime currentDateTime = new DateTime();
        DateTime subtractedDateTime = currentDateTime.minusDays(numberOfDays);
        return fileDateTime.isBefore(subtractedDateTime);

    }

    public static String getCurrentDateTimeFormat1() {
        String str7 = "en";
        String str8 = "US";
        Locale localLocale = new Locale(str7, str8);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", localLocale);
        long l1 = Calendar.getInstance().getTimeInMillis();
        String str9 = localSimpleDateFormat.format(Long.valueOf(l1));
        return str9;
    }

    public static long getConvertedDateTimeFromat1(String dateTime) {
        try {
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date d1 = format.parse(dateTime);
            return d1.getTime() / 1000;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getCurrentDateTimeFormat2() {
        String str7 = "en";
        String str8 = "US";
        Locale localLocale = new Locale(str7, str8);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", localLocale);
        long l1 = Calendar.getInstance().getTimeInMillis();
        String str9 = localSimpleDateFormat.format(Long.valueOf(l1));
        return str9;
    }

    public static long getConvertedDateTimeFromat2(String dateTime) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date d1 = format.parse(dateTime);
            return d1.getTime() / 1000;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getConvertedDateTimeFromat_Full(String dateTime) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date d1 = format.parse(dateTime);
            return d1.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getCurrentDateFormat2() {
        String str7 = "en";
        String str8 = "US";
        Locale localLocale = new Locale(str7, str8);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", localLocale);
        long l1 = Calendar.getInstance().getTimeInMillis();
        String str9 = localSimpleDateFormat.format(Long.valueOf(l1));
        return str9;
    }

    public static long getFirstHourOfDate(String dateTime) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            // System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long filter_val_date = (calendar.getTimeInMillis() / 1000L);
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getLastHourOfDate(String dateTime) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            //System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            long filter_val_date = (calendar.getTimeInMillis() / 1000L);
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getFirstHourOfDate_Full(String dateTime) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            // System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long filter_val_date = (calendar.getTimeInMillis());
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getLastHourOfDate_Full(String dateTime) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            //System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            long filter_val_date = (calendar.getTimeInMillis());
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static void printCurrentTimeInGMT() {
        Date date = new Date();
        DateFormat gmtFormat = new SimpleDateFormat();
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        gmtFormat.setTimeZone(gmtTime);
        System.out.println("Current Time: " + date);
        System.out.println("GMT Time: " + gmtFormat.format(date));

    }

    public static void printTimeInUTC() {
        final Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        Date dateWithoutTime = cal.getTime();

        final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS zzz";
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utc);
        System.out.println(sdf.format(dateWithoutTime));
    }

    public static void printCurrentTimeDifferenceLocalAndUTC() {
        String dateStart = "113/06/01 18:15:00";
        String dateStop = "113/06/02 00:00:00";

        // Custom date format
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        System.out.println("Time in seconds: " + diffSeconds + " seconds.");
        System.out.println("Time in minutes: " + diffMinutes + " minutes.");
        System.out.println("Time in hours: " + diffHours + " hours.");
    }

    public static String getNPrintTimeInUTC() {
        final Date date = new Date();
        final String ISO_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utc);

        //System.out.println(sdf.format(date));
        return sdf.format(date);
    }

    public static String getCurrentUTCDateTimeFormat2() {
        final Date date = new Date();
        final String ISO_FORMAT = "yyyy-MM-dd HH:mm";
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utc);
        //System.out.println(sdf.format(date));
        return sdf.format(date);
    }

    public static String getConvertedSolrDateTimeToLocalDateTime_String(String solrDateTime) {

        //  2013-05-23T17:00:23Z
        //  solrDateTime = "2013-05-23T17:00:23Z";
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z");
//        DateTime dt = formatter.parseDateTime(string);
//        Date d = dt.toDate();
//
//        String dateStart = getNPrintTimeInUTC();
//        // Custom date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println("Read UTC TIME :" + solrDateTime);
        // System.out.println(format.getTimeZone());
        Date d1 = null;
        try {
            d1 = format.parse(solrDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String ISO_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);

        final TimeZone utc = TimeZone.getDefault();
        // System.out.println(TimeZone.getDefault());
        sdf.setTimeZone(utc);

        System.out.println("Converted UTC TO LOCAL TIME :" + sdf.format(d1));
        return sdf.format(d1);
    }

    public static Date getConvertedSolrDateTimeToLocalDateTime_Date(String solrDateTime) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println("Read UTC TIME :" + solrDateTime);
        // System.out.println(format.getTimeZone());
        Date d1 = null;
        try {
            d1 = format.parse(solrDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String ISO_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);

        final TimeZone utc = TimeZone.getDefault();
        // System.out.println(TimeZone.getDefault());
        sdf.setTimeZone(utc);

        String convertedDate = sdf.format(d1);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        DateTime dt = formatter.parseDateTime(convertedDate);
        return dt.toDate();
    }

    public static String getConvertedSolrDateTimeToLocalHour_String(String solrDateTime) {

        //  2013-05-23T17:00:23Z
        //  solrDateTime = "2013-05-23T17:00:23Z";
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z");
//        DateTime dt = formatter.parseDateTime(string);
//        Date d = dt.toDate();
//
//        String dateStart = getNPrintTimeInUTC();
//        // Custom date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println("Read UTC TIME :" + solrDateTime);
        // System.out.println(format.getTimeZone());
        Date d1 = null;
        try {
            d1 = format.parse(solrDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String ISO_FORMAT = "HH";
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);

        final TimeZone utc = TimeZone.getDefault();
        // System.out.println(TimeZone.getDefault());
        sdf.setTimeZone(utc);

        System.out.println("Converted UTC TO LOCAL TIME :" + sdf.format(d1));
        return sdf.format(d1);
    }

    public static String getConvertedSolrDateTimeToLocalDay_String(String solrDateTime) {

        //  2013-05-23T17:00:23Z
        //  solrDateTime = "2013-05-23T17:00:23Z";
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z");
//        DateTime dt = formatter.parseDateTime(string);
//        Date d = dt.toDate();
//
//        String dateStart = getNPrintTimeInUTC();
//        // Custom date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println("Read UTC TIME :" + solrDateTime);
        // System.out.println(format.getTimeZone());
        Date d1 = null;
        try {
            d1 = format.parse(solrDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String ISO_FORMAT = "dd";
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);

        final TimeZone utc = TimeZone.getDefault();
        // System.out.println(TimeZone.getDefault());
        sdf.setTimeZone(utc);

        System.out.println("Converted UTC TO LOCAL TIME :" + sdf.format(d1));
        return sdf.format(d1);
    }

    public static String getConvertedSolrDateTimeToLocalMinute_String(String solrDateTime) {

        //  2013-05-23T17:00:23Z
        //  solrDateTime = "2013-05-23T17:00:23Z";
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z");
//        DateTime dt = formatter.parseDateTime(string);
//        Date d = dt.toDate();
//
//        String dateStart = getNPrintTimeInUTC();
//        // Custom date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println("Read UTC TIME :" + solrDateTime);
        // System.out.println(format.getTimeZone());
        Date d1 = null;
        try {
            d1 = format.parse(solrDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String ISO_FORMAT = "m";
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);

        final TimeZone utc = TimeZone.getDefault();
        // System.out.println(TimeZone.getDefault());
        sdf.setTimeZone(utc);

        System.out.println("Converted UTC TO LOCAL TIME :" + sdf.format(d1));
        return sdf.format(d1);
    }

    public static String getConvertedSolrDateTimeToLocalSecond_String(String solrDateTime) {

        //  2013-05-23T17:00:23Z
        //  solrDateTime = "2013-05-23T17:00:23Z";
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z");
//        DateTime dt = formatter.parseDateTime(string);
//        Date d = dt.toDate();
//
//        String dateStart = getNPrintTimeInUTC();
//        // Custom date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println("Read UTC TIME :" + solrDateTime);
        // System.out.println(format.getTimeZone());
        Date d1 = null;
        try {
            d1 = format.parse(solrDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String ISO_FORMAT = "s";
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);

        final TimeZone utc = TimeZone.getDefault();
        // System.out.println(TimeZone.getDefault());
        sdf.setTimeZone(utc);

        System.out.println("Converted UTC TO LOCAL TIME :" + sdf.format(d1));
        return sdf.format(d1);
    }

    public static String getConvertedUnixDateToSolrDateTime_String(long unixDate) {
        String uTCDate = "";
        try {
            String unixStringDate = getConvertedDateAndTimeFromUnixDate(new Long(unixDate).toString());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(unixStringDate);
            System.out.println("Real DATE :" + date);

            final TimeZone utc = TimeZone.getTimeZone("UTC");
            //Convert Date TO UTC String Date
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            format.setTimeZone(utc);
            uTCDate = format.format(date);

            System.out.println("UTC Date :" + uTCDate);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return uTCDate;
    }

    public static String getConvertedUnixDateToSolrDateTime_String_Full(long unixDate) {
        String uTCDate = "";
        try {
            String unixStringDate = getConvertedDateAndTimeFromUnixDate_Full(new Long(unixDate).toString());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date = format.parse(unixStringDate);
            System.out.println("Real DATE :" + date);

            final TimeZone utc = TimeZone.getTimeZone("UTC");
            //Convert Date TO UTC String Date
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            format.setTimeZone(utc);
            uTCDate = format.format(date);

            System.out.println("UTC Date :" + uTCDate);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return uTCDate;
    }

    public static String getCurrentHour() {
        String str7 = "en";
        String str8 = "US";
        Locale localLocale = new Locale(str7, str8);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("H", localLocale);
        long l1 = Calendar.getInstance().getTimeInMillis();
        String str9 = localSimpleDateFormat.format(Long.valueOf(l1));
        return str9;
    }

    public static String getCurrentMinute() {
        String str7 = "en";
        String str8 = "US";
        Locale localLocale = new Locale(str7, str8);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("m", localLocale);
        long l1 = Calendar.getInstance().getTimeInMillis();
        String str9 = localSimpleDateFormat.format(Long.valueOf(l1));
        return str9;
    }

    public static long getFirstMinuteOfHour(String dateTime, String hour) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            // System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long filter_val_date = (calendar.getTimeInMillis() / 1000L);
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getLastMinuteOfHour(String dateTime, String hour) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            //System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            long filter_val_date = (calendar.getTimeInMillis() / 1000L);
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getFirstMinuteOfHour_Full(String dateTime, String hour) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            // System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long filter_val_date = (calendar.getTimeInMillis());
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getLastMinuteOfHour_Full(String dateTime, String hour) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            //System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            long filter_val_date = (calendar.getTimeInMillis());
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getFirstSecondOfMinute(String dateTime, String hour, String minute) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            // System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long filter_val_date = (calendar.getTimeInMillis() / 1000L);
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getLastSecondOfMinute(String dateTime, String hour, String minute) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            //System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            long filter_val_date = (calendar.getTimeInMillis() / 1000L);
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getFirstSecondOfMinute_Full(String dateTime, String hour, String minute) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            // System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long filter_val_date = (calendar.getTimeInMillis());
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getLastSecondOfMinute_Full(String dateTime, String hour, String minute) {

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(dateTime);
            //System.out.println(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            long filter_val_date = (calendar.getTimeInMillis());
            return filter_val_date;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getNumberOfDaysInMonth(int year, int month) {
        DateTime dateTime = new DateTime(year, month, 14, 12, 0, 0, 000);
        return dateTime.dayOfMonth().getMaximumValue();
    }

    //For DateConversion from localunixdate to date format to make it input for Search
    //Output format >>>> yyyy-MM-dd
    public static String getConvertedUnixDateToStringFormat1(final long unixDateTime) {
        try {
            long timestamp = (long) unixDateTime;
            timestamp = timestamp * 1000L;
            Date dates = new Date(timestamp);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(dates).trim();
        } catch (Exception e) {
            return "invalid date";
        }
    }

    public static String getCurrentNameOfMonth() {
        String str7 = "en";
        String str8 = "US";
        Locale localLocale = new Locale(str7, str8);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MMMM", localLocale);
        long l1 = Calendar.getInstance().getTimeInMillis();
        String str9 = localSimpleDateFormat.format(Long.valueOf(l1));
        return str9;
    }

    public static int getOverAllMinutesFromCurrentTime() {

        DateTime jodaTime = new DateTime();  // current time
        System.out.println("year = " + jodaTime.getYear());
        System.out.println("month = " + jodaTime.getMonthOfYear());
        System.out.println("day = " + jodaTime.getDayOfMonth());
        System.out.println("hour = " + jodaTime.getHourOfDay());
        System.out.println("minute = " + jodaTime.getMinuteOfHour());
        int hour = jodaTime.getHourOfDay();
        int mins = jodaTime.getMinuteOfHour();
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    public static int getOverAllMinutesFromHourAndMinutes(int hour, int mins) {
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    public static String getHoursAndMinutesFromOverAllMinutes(int mins) {
        int hours = mins / 60; //since both are ints, you get an int
        int minutes = mins % 60;
        String time = String.format("%d:%02d", hours, minutes);
        return time;
    }

    public static String getHoursFromOverAllMinutes(int mins) {
        int hours = mins / 60; //since both are ints, you get an int
        String time = String.format("%d", hours);
        return time;
    }

    public static String getMinutesFromOverAllMinutes(int mins) {
        int minutes = mins % 60;
        String time = String.format("%02d", minutes);
        return time;
    }

    public static String getCurrentDayName() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String todaysDayName = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        return todaysDayName;
    }

    public static String getDayNameFromUnixTimeStamp(final long unixDateTime) {
        long timestamp = (long) unixDateTime;
        timestamp = timestamp * 1000L;
        Date date = new Date(timestamp);
        String todaysDayName = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        return todaysDayName;
    }

    public static int getTotalNoOfDaysOfMonth(int year, int month) {
        DateTime dateTime = new DateTime(year, month, 14, 12, 0, 0, 000);
        return dateTime.dayOfMonth().getMaximumValue();
    }

    
    public static void main(String args[]) {

//        System.out.println(getLastDateOfMonth(2018,12));
//        DateMidnight first = new DateMidnight(new Date()).withDayOfMonth(1);
//        System.out.println(first);
//        // last midnight in this month
//        DateMidnight last = first.plusMonths(1).minusDays(1);
//        System.out.println(last);
//        int year = 2015;
//        int month = 9;
//        int startUnixDate = (int) (getStartDateOfMonth(year, month).getTime() / 1000);
//        int endUnixDate = (int) (getEndDateOfMonth(year, month).getTime() / 1000);
//        System.out.println(getConvertedUnixDateToStringFormat1(startUnixDate));
//        System.out.println(getConvertedUnixDateToStringFormat1(endUnixDate));
//2015-09-27
//        DateTime jodaTime = new DateTime();  // current time
//        System.out.println("year = " + jodaTime.getYear());
//        System.out.println("month = " + jodaTime.getMonthOfYear());
//        System.out.println("day = " + jodaTime.getDayOfMonth());
//        System.out.println("hour = " + jodaTime.getHourOfDay());
//        System.out.println("minute = " + jodaTime.getMinuteOfHour());
//        System.out.println("second = " + jodaTime.getSecondOfMinute());
//        System.out.println("millis = " + jodaTime.getMillisOfSecond());
//
//        System.out.println(getNumberOfDaysInMonth(2015, 10));
//        getConvertedSolrDateTimeToLocalDateTime_Date("2013-06-02T17:00:00Z");
//        System.out.println(getConvertedUnixDateToSolrDateTime_String_Full(getFirstSecondOfMinute_Full("2013-06-03", "2","4")));
//        System.out.println(getConvertedUnixDateToSolrDateTime_String_Full(getLastSecondOfMinute_Full("2013-06-03", "2","4")));
//
//        System.out.println(getFirstMinuteOfHour_Full("2013-06-03","2"));
//
//
//        Date d = new Date();
//        System.out.println(getStartDate_CurrentDay_UnixDate());
//        System.out.println(getEndDate_CurrentDay_UnixDate());
//        System.out.println(getFirstMinuteOfHour("2013-06-03", "2"));
//        System.out.println(getLastMinuteOfHour("2013-06-03", "2"));
//        
//            System.out.println(getConvertedDateAndTimeFromUnixDate(new Long(getFirstMinuteOfHour("2013-06-03", "2")).toString()));
//        System.out.println(getConvertedDateAndTimeFromUnixDate(new Long(getLastMinuteOfHour("2013-06-03", "2")).toString()));
//        
//        System.out.println(getConvertedDateAndTimeFromUnixDate(new Long(getFirstSecondOfMinute("2013-06-03", "2","4")).toString()));
//        System.out.println(getConvertedDateAndTimeFromUnixDate(new Long(getLastSecondOfMinute("2013-06-03", "2","6")).toString()));
        // System.out.println(d);
        
        Timestamp fromDeviceTimestamp = Timestamp.valueOf("2021-01-02" + " 00:00:00.000000000");
          int fromTime = (int) (fromDeviceTimestamp.getTime() / 1000L);
          System.out.println(getCurrentDate());
          
    }
}
