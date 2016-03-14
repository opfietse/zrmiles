/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: StringUtil.java,v 1.1 2007/06/01 15:24:05 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.common;


/**
 * Holds some utilities to do manipulations with Strings.
 * @author rvk
 */
public class StringUtil {

    private static StringBuffer yyyyMMSb = new StringBuffer(6);
    /**
     * Converts yyyyMM to MM-yyyy
     * 
     * @param yyyyMM the string to convert
     * @return the converted string if everything is OK, null in case of a null tring or an
     * empty string or the original (if length not equal 6)
     */
    public static String makeMMyyyyFromyyyyMM(String yyyyMM) {

        if (yyyyMM == null) {
            return null;
        }

        if (yyyyMM.trim().length() == 0) {
            return null;
        }

        if (yyyyMM.trim().length() != 6) {
            return yyyyMM;
        }

        yyyyMMSb.delete(0, 5);

        yyyyMMSb.replace(0, 1, yyyyMM.substring(4, 6));
        yyyyMMSb.replace(2, 2, "-");
        yyyyMMSb.replace(3, 6, yyyyMM.substring(0, 4));

        return yyyyMMSb.toString();
    }

}
