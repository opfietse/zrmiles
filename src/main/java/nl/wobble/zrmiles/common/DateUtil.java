/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: DateUtil.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author rvk
 */
public class DateUtil {

    private static final SimpleDateFormat yearMonthFormat =
        new SimpleDateFormat("yyyyMM");

    private static final SimpleDateFormat monthYearFormat =
        new SimpleDateFormat("MMyyyy");

    private static final SimpleDateFormat yearMonthDayFormat =
        new SimpleDateFormat("yyyyMMdd");

    private static final GregorianCalendar calendar = new GregorianCalendar();

    /**
     * Converts a java.util.Date to a java.sql.Date
     * 
     * @param date the date to convert
     * @return the converted date
     */
    public static java.sql.Date dateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * Converts a java.sql.Date to a java.util.Date
     * 
     * @param date the date to convert
     * @return the converted date
     */
    public static java.util.Date sqlDateToDate(java.sql.Date date) {
        return new java.util.Date(date.getTime());
    }

    /**
     * Converts a date to a string (yyyyMM)
     * @param date the date to convert
     * @return the converted date
     */
    public static String makeYearMonthString(Date date) {
        return yearMonthFormat.format(date);
    } // makeYearMonthString()

    /**
     * Converts a date to a string (MMyyyy)
     * @param date the date to convert
     * @return the converted date
     */
    public static String makeMonthYearString(Date date) {
        return monthYearFormat.format(date);
    } // makeMonthYearString()

    public static Date makeDateFromYearMonthDay(String yearMonthDay) throws ParseException {
        return yearMonthDayFormat.parse(yearMonthDay);
    }

    /**
     * Checks wether 2 dates are in different months.
     * 
     * @param date1 first date
     * @param date2 second date
     * @return true if both dates are in different months or both null, false otherwise
     */
    public static boolean isDifferentMonth(Date date1, Date date2) {
        int month;
        int year;

        if (date1 == null && date2 != null) {
            return true;
        }

        if (date1 != null && date2 == null) {
            return true;
        }

        if (date1 == null && date2 == null) {
            return false;
        }

        calendar.setTime(date1);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        calendar.setTime(date2);
        if (calendar.get(Calendar.MONTH) != month) {
            return true;
        } else if (calendar.get(Calendar.YEAR) != year) {
            return true;
        } else {
            return false;
        }
    } // isDifferentMonth()
}
