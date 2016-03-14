/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: Motorcycle.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.common;

import java.util.StringTokenizer;

import nl.wobble.zrmiles.exception.InvalidDataException;

/**
 * The motorcycle (used to rack up the miles)
 * 
 * @author rvk
 */
public class Motorcycle {

    /**
     * Id of the database record.
     */
    private Integer id;
    
    /**
     * Id of the rider of this motorcycle.
     */
    private Integer riderId;

    /**
     * The make of the motorcycle.
     */
    private String make;

    /**
     * The model of the motorcyle.
     */
    private String model;

    /**
     * The year the motorcycle was built/first out on the street.
     */
    private int year;

    /**
     * The year the motorcycle was built/first out on the street.
     */
    private int distanceUnit;

    /**
     * The name of the owner.
     */
    private String owner; // no database field
    
    /**
     * A field to store mileage
     */
    private Integer miles;

    /**
     * Constructs a Motorcycle with all fields initialized to null except odometer in miles.
     */
    public Motorcycle() {
        id = null;
        riderId = null;
        make = null;
        model = null;
        year = 0;
        distanceUnit = Constants.BIKE_USES_MILES;
        owner = null;
        miles = null;
    }

    /**
     * Constructs a Motorcycle with all fields initialized to the specified values, except
     * the miles field.
     */
    public Motorcycle(
        Integer id,
        Integer riderId,
        String make,
        String model,
        int year,
        int distanceUnit,
        String owner) {

        this.id = id;
        this.riderId = riderId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.distanceUnit = distanceUnit;
        this.owner = owner;
        
        miles = null;
    }

    /**
     * @return The id of the record in the database.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return The make of the bike.
     */
    public String getMake() {
        return make;
    }

    /**
     * @return The model of the bike.
     */
    public String getModel() {
        return model;
    }

    /**
     * @return The owner's name.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @return The database id of the rider.
     */
    public Integer getRiderId() {
        return riderId;
    }

    /**
     * @return The year the bike was built.
     */
    public int getYear() {
        return year;
    }

    /**
     * @param integer
     */
    public void setId(Integer integer) {
        id = integer;
    }

    /**
     * @param string
     */
    public void setMake(String string) {
        make = string;
    }

    /**
     * @param string
     */
    public void setModel(String string) {
        model = string;
    }

    /**
     * @param string
     */
    public void setOwner(String string) {
        owner = string;
    }

    /**
     * @param integer
     */
    public void setRiderId(Integer integer) {
        riderId = integer;
    }

    /**
     * @param year The year the motorcycle was built.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return The distance unit the odometer reads in (miles/kilometers).
     */
    public int getDistanceUnit() {
        return distanceUnit;
    }

    /**
     * @param i
     */
    public void setDistanceUnit(int i) {
        distanceUnit = i;
    }

    public String toDbString(String delim) {
        StringBuffer sb = new StringBuffer();

        sb.append(id);
        sb.append(delim);
        sb.append(riderId);
        sb.append(delim);
        sb.append(make);
        sb.append(delim);
        sb.append(model);
        sb.append(delim);
        sb.append(year);
        sb.append(delim);
        sb.append(distanceUnit);

        return sb.toString();
    }

    /**
     * Creates a Motorcycle object from a string. The string should contain all fields separated by
     * delimiters. E.g. "1|1|Kawasaki|ZR-7|1999|1"
     * @param inString
     * @param delim
     * @return a newly contructed Motorcycle object.
     * @throws InvalidDataException In case of an error in the input.
     */
    public static Motorcycle stringToMotorcycle(String inString, String delim) throws InvalidDataException {
        Integer i;
        Integer r;
        String m;
        String mo;
        int y;
        int o;
        String nextToken;
        boolean readDelim;
        
        if (inString == null) {
            throw new InvalidDataException("Input cannot be null");
        }

        if (delim == null) {
            throw new InvalidDataException("Delimiter cannot be null");
        }

        StringTokenizer st = new StringTokenizer(inString, delim, true);

        // Id
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                throw new InvalidDataException("Id cannot be null");
            } else {
                i = new Integer(nextToken);
            }
        } else {
            throw new InvalidDataException("Input string has too few data");
        }

        // read delimiter
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
        } else {
            throw new InvalidDataException("Input string has too few data");
        }

        // rider id
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                throw new InvalidDataException("Rider id cannot be null");
            } else {
                r = new Integer(nextToken);
            }
        } else {
            throw new InvalidDataException("Input string has too few data");
        }

        // read delimiter
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
        } else {
            throw new InvalidDataException("Input string has too few data");
        }

        // make
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                throw new InvalidDataException("Make cannot be null");
            } else {
                m = nextToken;
            }
        } else {
            throw new InvalidDataException("Input string has too few data");
        }

        // read delimiter
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
        } else {
            throw new InvalidDataException("Input string has too few data");
        }

        // model
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                throw new InvalidDataException("Model cannot be null");
            } else {
                mo = nextToken;
            }
        } else {
            throw new InvalidDataException("Input string has too few data");
        }

        // read delimiter
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
        } else {
            throw new InvalidDataException("Input string has too few data");
        }

        readDelim = true;
        // year
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                y = 0;
                readDelim = false;
            } else {
                y = Integer.parseInt(nextToken);
            }
        } else {
            throw new InvalidDataException("Input string has too few data");
        }

        if (readDelim) {
            // read delimiter
            if (st.hasMoreTokens()) {
                nextToken = st.nextToken();
            } else {
                throw new InvalidDataException("Input string has too few data");
            }
        }

        // odometer in
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                throw new InvalidDataException("Odometer in cannot be null");
            } else {
                o = Integer.parseInt(nextToken);
                if (o != Constants.BIKE_USES_KILOMETERS && o != Constants.BIKE_USES_MILES) {
                    throw new InvalidDataException("Odometer in has invalid value");
                }
            }
        } else {
            throw new InvalidDataException("Input string has too few data");
        }

        return new Motorcycle(i, r, m, mo, y, o, null);
    }

    /**
     * @return the miles
     */
    public Integer getMiles() {
        return miles;
    }

    /**
     * @param miles the miles
     */
    public void setMiles(Integer miles) {
        this.miles = miles;
    }

}
