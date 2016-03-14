/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: RiderIdAndNameVO.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
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

/**
 * Holds riderid and name. Used for transporting data.
 * @author rvk
 */
public class RiderIdAndNameVO implements Serializable {

    private int id;
    private String name;

    /**
     * Makes a new RiderIdAndNameVO with all fields initialized to the specified values.
     */
    public RiderIdAndNameVO(
        int id,
        String name) {

        this.id = id;
        this.name = name;

    }

    /**
     * @return The rider id
     */
    public int getId() {
        return id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    public boolean equals(Object o) {

        RiderIdAndNameVO theOther;

        if (!(o instanceof RiderIdAndNameVO)) {
            return false;
        }

        theOther = (RiderIdAndNameVO) o;
        if (theOther.getId() != this.getId()) {
            return false;
        }

        if (!theOther.getName().equals(this.getName())) {
            return false;
        }

        return true;
    }

    public String toString() {
        return "[RiderIdAndNameVO: "
            + id
            + ", "
            + name
            + "]";
    }

}
