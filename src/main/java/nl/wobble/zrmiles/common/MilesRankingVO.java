/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: MilesRankingVO.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hold some data for making mileage rankings. Used for transporting data.
 * @author rvk
 */
public class MilesRankingVO implements Serializable {

    private int riderId;
    private int motorcycleId;
    private Date date;
    private int mileage;
    private int correction;
    private boolean inKilometers;

    /**
     * Makes a new MilesRankingVO with all fields initialized to the specified values.
     */
    public MilesRankingVO(
        int riderId,
        int motorcycleId,
        Date date,
        int mileage,
        int correction,
        boolean inKilometers) {
        this.riderId = riderId;
        this.motorcycleId = motorcycleId;
        this.date = date;
        this.mileage = mileage;
        this.correction = correction;
        this.inKilometers = inKilometers;
    }

    /**
     * @return The mileage
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * @return The correction
     */
    public int getCorrection() {
        return correction;
    }

    public boolean equals(Object o) {

        MilesRankingVO theOther;

        if (!(o instanceof MilesRankingVO)) {
            return false;
        }

        theOther = (MilesRankingVO) o;
        if (theOther.getMileage() != this.getMileage()) {
            return false;
        }

        if (!theOther.getDate().equals(this.getDate())) {
            return false;
        }

        if (theOther.getMotorcycleId() != this.getMotorcycleId()) {
            return false;
        }

        if (theOther.getRiderId() != this.getRiderId()) {
            return false;
        }

        if (theOther.isInKilometers() != this.isInKilometers()) {
            return false;
        }

        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return "[MilesRankingVO: "
            + riderId
            + ", "
            + motorcycleId
            + ", "
            + sdf.format(date)
            + ", "
            + mileage
            + ", "
            + correction
            + ", "
            + inKilometers
            + "]";
    }

    /**
     * @return the date
     */
    public final Date getDate() {
        return date;
    }

    /**
     * @return is odometer in kilometers
     */
    public boolean isInKilometers() {
        return inKilometers;
    }

    /**
     * @return is odometer in miles
     */
    public boolean isInMiles() {
        return !inKilometers;
    }

    /**
     * @return the motorcycle databse id
     */
    public int getMotorcycleId() {
        return motorcycleId;
    }

    /**
     * @return the rider database id
     */
    public int getRiderId() {
        return riderId;
    }
}
