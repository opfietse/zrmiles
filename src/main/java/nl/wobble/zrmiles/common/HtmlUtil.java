/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: HtmlUtil.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.common;

import java.util.Iterator;
import java.util.List;

/**
 * Holds some utilities to generate HTML code.
 * @author rvk
 */
public class HtmlUtil {

    /**
     * Makes a &lt;OPTION&gt; object of months with
     * one of the months preselected 
     * 
     * @param defaultMonth the month to preselect (0 for january, 11 for december)
     * @return the HTML code
     */
    public static String makeMonthSelectWithDefault(int defaultMonth) {
        StringBuffer sb = new StringBuffer();

        sb.append("<OPTION ");
        if (defaultMonth == 0) {
            sb.append("selected ");
        }
        sb.append("value=\"0\">January</OPTION>");
        sb.append("<OPTION ");
        if (defaultMonth == 1) {
            sb.append("selected ");
        }
        sb.append("value=\"1\">February</OPTION>");
        sb.append("<OPTION ");
        if (defaultMonth == 2) {
            sb.append("selected ");
        }
        sb.append("value=\"2\">March</OPTION>");
        sb.append("<OPTION ");
        if (defaultMonth == 3) {
            sb.append("selected ");
        }
        sb.append("value=\"3\">April</OPTION>\n");
        sb.append("<OPTION ");
        if (defaultMonth == 4) {
            sb.append("selected ");
        }
        sb.append("value=\"4\">May</OPTION>");
        sb.append("<OPTION ");
        if (defaultMonth == 5) {
            sb.append("selected ");
        }
        sb.append("value=\"5\">June</OPTION>");
        sb.append("<OPTION ");
        if (defaultMonth == 6) {
            sb.append("selected ");
        }
        sb.append("value=\"6\">July</OPTION>");
        sb.append("<OPTION ");
        if (defaultMonth == 7) {
            sb.append("selected ");
        }
        sb.append("value=\"7\">August</OPTION>\n");
        sb.append("<OPTION ");
        if (defaultMonth == 8) {
            sb.append("selected ");
        }
        sb.append("value=\"8\">September</OPTION>");
        sb.append("<OPTION ");
        if (defaultMonth == 9) {
            sb.append("selected ");
        }
        sb.append("value=\"9\">October</OPTION>");
        sb.append("<OPTION ");
        if (defaultMonth == 10) {
            sb.append("selected ");
        }
        sb.append("value=\"10\">November</OPTION>");
        sb.append("<OPTION ");
        if (defaultMonth == 11) {
            sb.append("selected ");
        }
        sb.append("value=\"11\">December</OPTION>\n");
        return sb.toString();
    }

    /**
     * Makes a &lt;OPTION&gt; object of riders.
     * 
     * @param riders a List of RiderIdAndIdVO objects
     * @param defaultRiders the preselected rider(s)
     * @return the HTML code
     */
    public static String makeRiderSelect(List riders, int[] defaultRiders) {

        StringBuffer sb;
        RiderIdAndNameVO rider;
        Iterator iterator;
        int counter;

        if (riders == null || riders.size() == 0) {
            return "";
        }

        sb = new StringBuffer();
        counter = 0;

        iterator = riders.iterator();
        while (iterator.hasNext()) {

            ++counter;
            rider = (RiderIdAndNameVO) iterator.next();
            sb.append("<OPTION ");
            if (riderInArray(defaultRiders, rider.getId())) {
                sb.append("selected ");
            }
            sb.append(
                "value=\""
                    + rider.getId()
                    + "\">"
                    + rider.getName()
                    + "</OPTION>");

                if (counter == 4) {
                    counter = 0;
                    sb.append("\n");
                }
        }

        return sb.toString();
    }

    private static boolean riderInArray(int[] riders, int rider) {
        int i;

        if (riders == null) {
            return false;
        }

        for (i = 0; i < riders.length; i++) {
            if (riders[i] == rider) {
                return true;
            }
        }

        return false;
    }
}
