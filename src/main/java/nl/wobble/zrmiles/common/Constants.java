/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: Constants.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
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
 * Holds all constants that are used in %quot;zrmiles&quot;.
 * @author rvk
 */
public class Constants {

    //
    // Contents of database field in ZR_MOTORCYCLES
    //
    public static final int BIKE_USES_MILES = 0;
    public static final int BIKE_USES_KILOMETERS = 1;

    //
    // Units:
    //
    public static final float MILE = 1.609f;
    public static final float UK_GALLON = 4.546092f;
    public static final float US_GALLON = 3.784297f;

    //
    // constants that define userchoices:
    //
    public static final int USE_KILOMETERS = 1;
    public static final int USE_MILES = 2;

    public static final int USE_LITERS = 1;
    public static final int USE_UK_GALLONS = 2;
    public static final int USE_US_GALLONS = 4;

    public static final int USE_DISTANCE_PER_VOLUME = 1;
    public static final int USE_VOLUME_PER_DISTANCE = 2;

    public static final int USE_EUROPEAN_DATE = 1;
    public static final int USE_AMERICAN_DATE = 2;

    //
    // to use in lists:
    //
    public static final String KILOMETERS = "Kilometers";
    public static final String MILES = "Miles";

    public static final String LITERS = "Liters";
    public static final String UK_GALLONS = "UK Gallons";
    public static final String US_GALLONS = "US Gallons";

    public static final String DISTANCE_PER_VOLUME = "distance/volume";
    public static final String VOLUME_PER_DISTANCE = "volume/distance";

    public static final String EUROPEAN_DATE = "dd-mm-yyyy";
    public static final String AMERICAN_DATE = "mm-dd-yyyy";

    // SDF = SimpleDateFormat
    public static final String SDF_EUROPEAN_DATE = "dd-MM-yyyy";
    public static final String SDF_AMERICAN_DATE = "MM-dd-yyyy";

    // SDF database format for dumping/loading data
    public static final String SDF_DB_DATE = "yyyy-MM-dd";

    // Cookie name
    public static final String PREF_COOKIE_NAME = "ZRMILESPREF";

    // User roles
    public static final int GUEST_ROLE = 1;
    public static final int USER_ROLE = 2;
    public static final int ADMIN_ROLE = 4;

}
