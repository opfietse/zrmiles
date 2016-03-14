/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: WebUser.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */

package nl.wobble.zrmiles.common;

import java.io.Serializable;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * Holds the preferences of the website users for the
 * duration of the session. Also keeps track of the last time a cookie
 * was sent to the user.
 */
public class WebUser implements Serializable {
    /*
     * instance variables
     */
    private int distanceUnit; // the unit to use for distances
    private int volumeUnit; // the unit to use for volumes
    private int consumptionUnit; // the unit to use for (fuel)consumption
    private int dateFormat; // the format to use for presenting dates

    private boolean cookieSent; // was a cookie sent this session?
    private boolean cookieRead; // was a cookie read this session?

    private boolean loggedIn; // has user provided correct user/password?
    private String userName; // username used to log in
    private int userRole; // the type of user (e.g. guest, user, admin)
    
//    private transient Log log;

    /**
     * Constructs a default WebUser object with 'American' settings.
     * Settings are: miles, distance/volume,american date format
     */
    public WebUser() {
        /*
         * American settings are default:
         */
        this.distanceUnit = Constants.USE_MILES;
        this.volumeUnit = Constants.USE_US_GALLONS;
        this.consumptionUnit = Constants.USE_DISTANCE_PER_VOLUME;
        this.dateFormat = Constants.USE_AMERICAN_DATE;
        this.cookieSent = false;
        this.cookieRead = false;

//        log = null;
    }

    public void setDistanceUnit(int du) {
        /*
         * should be one of the Constants
         */
        if (du == Constants.USE_KILOMETERS || du == Constants.USE_MILES) {
            this.distanceUnit = du;
        }
    }

    public void setDistanceUnit(String du) {
        if (du == null) {
            return;
        }
        int dist = Integer.parseInt(du);
        this.setDistanceUnit(dist);
    }

    public int getDistanceUnit() {
        return this.distanceUnit;
    }

    public void setVolumeUnit(int vu) {
        if (vu == Constants.USE_LITERS
            || vu == Constants.USE_UK_GALLONS
            || vu == Constants.USE_US_GALLONS) {
            this.volumeUnit = vu;
        }
    }

    public void setVolumeUnit(String vu) {
        if (vu == null) {
            return;
        }
        int volume = Integer.parseInt(vu);
        this.setVolumeUnit(volume);
    }

    public int getVolumeUnit() {
        return this.volumeUnit;
    }

    public void setConsumptionUnit(int cu) {
        if (cu == Constants.USE_DISTANCE_PER_VOLUME
            || cu == Constants.USE_VOLUME_PER_DISTANCE) {
            this.consumptionUnit = cu;
        }
    }

    public void setConsumptionUnit(String cu) {
        if (cu == null) {
            return;
        }
        int cons = Integer.parseInt(cu);
        this.setConsumptionUnit(cons);
    }

    public int getConsumptionUnit() {
        return this.consumptionUnit;
    }

    public void setDateFormat(int df) {
        if (df == Constants.USE_EUROPEAN_DATE
            || df == Constants.USE_AMERICAN_DATE) {
            this.dateFormat = df;
        }
    }

    public void setDateFormat(String df) {
//        if (log == null) {
//            log = LogFactory.getLog(this.getClass());
//        }
//        if (log.isInfoEnabled()) {
//            log.info("setDateFormat(" + df + ")");
//        }

        if (df == null) {
            return;
        }
        int datef = Integer.parseInt(df);
        this.setDateFormat(datef);
    }

    public int getDateFormat() {
//        if (log == null) {
//            log = LogFactory.getLog(this.getClass());
//        }
//        if (log.isInfoEnabled()) {
//            log.info("getDateFormat(): " + this.dateFormat);
//        }

        return this.dateFormat;
    }

    public String getDateFormatString() {
        return this.dateFormat == Constants.USE_EUROPEAN_DATE
            ? Constants.SDF_EUROPEAN_DATE
            : Constants.SDF_AMERICAN_DATE;
    }

    public boolean isCookieSent() {
        return this.cookieSent;
    }

    public boolean isCookieRead() {
        return this.cookieRead;
    }

    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    public int getUserRole() {
        return this.userRole;
    }

    public void setCookieSent(boolean cookieSent) {
        this.cookieSent = cookieSent;
    }

    public void setCookieRead(boolean cookieRead) {
        this.cookieRead = cookieRead;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    /**
     * Initializes the fields with values derived from the cookie value.
     * @param cookieString The value stored in the cookie.
     */
    public void setValuesFromCookie(String cookieString) {
        /*
         * for order: see toString()
        */
        if (cookieString != null) {
//            if (log == null) {
//                log = LogFactory.getLog(this.getClass());
//            }
            for (int i = 0; i < cookieString.length(); i++) {
//                if (log.isInfoEnabled()) {
//                    log.info(
//                        "Cookie-pos: " + cookieString.substring(i, i + 1));
//                }
                switch (i) {
                    case 0 :
                        this.distanceUnit =
                            Integer.parseInt(cookieString.substring(i, i + 1));
                        break;
                    case 1 :
                        this.volumeUnit =
                            Integer.parseInt(cookieString.substring(i, i + 1));
                        break;
                    case 2 :
                        this.consumptionUnit =
                            Integer.parseInt(cookieString.substring(i, i + 1));
                        break;
                    case 3 :
                        this.dateFormat =
                            Integer.parseInt(cookieString.substring(i, i + 1));
                        break;
                }
            }
        }
    }

    /**
     * Makes a string representation of the preferences.
     * @return The string representation of the preferences.
     */
    public String toString() {
        return "" + distanceUnit + volumeUnit + consumptionUnit + dateFormat;
    }
    /**
     * @return the username used to log in
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
