/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: NameMileageVO.java,v 1.1 2007/06/01 15:24:05 rvk Exp $
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
import java.util.List;

/**
 * Hold data (name and mileage) for a ranking list entry.
 * @author rvk
 */
public class NameMileageVO implements Serializable {

    private String name;
    private int riderId;
    private int mileage;
//    private int firstOdometer;
//    private int lastOdometer;
    private List mileages; // separate mileages for this riders bikes
    
    /**
     * Makes a new NameMileageVO with all fields initialized to the specified values.
     */
    public NameMileageVO(String name, int riderId, int mileage/*, int firstOdometer, int lastOdometer*/, List mileages) {
        this.name = name;
        this.riderId = riderId;
        this.mileage = mileage;
//        this.firstOdometer = firstOdometer;
//        this.lastOdometer = lastOdometer;
        this.mileages = mileages;
    }

    /**
     * @return The mileage
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * @return The rider's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The rider's id
     */
    public int getRiderId() {
        return riderId;
    }

    /**
     * Sets the mileage.
     * @param mileage The total mileage.
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /**
     * Sets the name.
     * @param name The owner's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the riderId of the owner.
     * @param name The owner's rider id.
     */
    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

//    /**
//     * @return The first odometer reading of the year.
//     */
//    public int getFirstOdometer() {
//        return firstOdometer;
//    }
//
//    /**
//     * @return The last odometer reading of the year.
//     */
//    public int getLastOdometer() {
//        return lastOdometer;
//    }
//
//    /**
//     * @param The first odometer reading of the year.
//     */
//    public void setFirstOdometer(int firstOdometer) {
//        this.firstOdometer = firstOdometer;
//    }
//
//    /**
//     * @param The last odometer reading of the year.
//     */
//    public void setLastOdometer(int lastOdometer) {
//        this.lastOdometer = lastOdometer;
//    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {

        NameMileageVO theOther;

        if (!(o instanceof NameMileageVO)) {
            return false;
        }

        theOther = (NameMileageVO) o;
        if (theOther.getMileage() != this.getMileage()) {
            return false;
        }

        if (! theOther.getName().equals(this.getName())) {
            return false;
        }

        return true;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return (name + mileage).hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[NameMileage: " + name + ", " + mileage + "]";
    }
    /**
     * @return the list of mileages
     */
    public List getMileages() {
        return mileages;
    }

    /**
     * @param mileages the mileages
     */
    public void setMileages(List mileages) {
        this.mileages = mileages;
    }

}
