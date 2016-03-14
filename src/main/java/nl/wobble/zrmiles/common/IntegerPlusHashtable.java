/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: IntegerPlusHashtable.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
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
import java.util.Hashtable;

/**
 * Holds some data for making mileage rankings. Used for temporary data.
 * @author rvk
 */
public class IntegerPlusHashtable implements Serializable {

    private Integer theInteger;
    private Hashtable theHashtable;

    /**
     * Makes a new IntegerPlusHashtable with all fields initialized to null.
     */
    public IntegerPlusHashtable() {

        this.theInteger = null;
        this.theHashtable = null;

    }

    /**
     * Makes a new IntegerPlusHashtable with all fields initialized to the specified values.
     */
    public IntegerPlusHashtable(
        Integer theInteger,
        Hashtable theHashtable) {

        this.theInteger = theInteger;
        this.theHashtable = theHashtable;

    }



    public String toString() {
        return "[IntegerPlusHashtable: "
            + theInteger
            + ", "
            + theHashtable
            + "]";
    }

    /**
     * @return the hashtable 
     */
    public Hashtable getTheHashtable() {
        return theHashtable;
    }

    /**
     * @return the integer
     */
    public Integer getTheInteger() {
        return theInteger;
    }

    /**
     * @param theHashtable the hashtable value to set.
     */
    public void setTheHashtable(Hashtable theHashtable) {
        this.theHashtable = theHashtable;
    }

    /**
     * @param theInteger the Integer value to set.
     */
    public void setTheInteger(Integer theInteger) {
        this.theInteger = theInteger;
    }

}
