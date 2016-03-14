/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: MilesEntry.java,v 1.1 2007/06/01 15:24:05 rvk Exp $
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import nl.wobble.zrmiles.exception.InvalidDataException;

/**
 * Combination of motorcycle - date - odometer reading
 * @author rvk
 */
public class MilesEntry implements Serializable {

    /**
     * Id of the database record.
     */
    private Integer id;
    
    /**
     * Id of the motorcycle.
     */
    private Integer motorcycleId;

    private Date date;

    private int odometerReading;

    private int correction;

    private String userComment;

    private String motorcycle; // no database field
    
    private String owner; // no database field

    /**
     * Constructs a MilesEntry with all fields initialized to null.
     */
    public MilesEntry() {
        id = null;
        motorcycleId = null;
        date = null;
        odometerReading = 0;
        correction = 0;
    }

    /**
     * Constructs a MilesEntry with all database fields initialized to the specified values.
     */
    public MilesEntry(
        Integer id,
        Integer motorcycleId,
        Date date,
        int odometerReading,
        int correction,
        String userComment) {

        this.id = id;
        this.motorcycleId = motorcycleId;
        this.date = date;
        this.odometerReading = odometerReading;
        this.correction = correction;
        this.userComment = userComment;
    }

    /**
     * Constructs a MilesEntry with all fields initialized to the specified values.
     */
    public MilesEntry(
        Integer id,
        Integer motorcycleId,
        Date date,
        int odometerReading,
        int correction,
        String userComment,
        String motorcycle,
        String owner) {

        this.id = id;
        this.motorcycleId = motorcycleId;
        this.date = date;
        this.odometerReading = odometerReading;
        this.correction = correction;
        this.userComment = userComment;
        this.motorcycle = motorcycle;
        this.owner = owner;
    }

    /**
     * @return The correction to apply to this entry (and therefore the total)
     */
    public int getCorrection() {
        return correction;
    }

    /**
     * @return The date of the odometer reading.
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return The id of the record in the database.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return The database id of the motorcycle.
     */
    public Integer getMotorcycleId() {
        return motorcycleId;
    }

    /**
     * @return The odometer reading.
     */
    public int getOdometerReading() {
        return odometerReading;
    }

    /**
     * @param i
     */
    public void setCorrection(int i) {
        correction = i;
    }

    /**
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @param integer
     */
    public void setId(Integer integer) {
        id = integer;
    }

    /**
     * @param integer
     */
    public void setMotorcycleId(Integer integer) {
        motorcycleId = integer;
    }

    /**
     * @param i
     */
    public void setOdometerReading(int i) {
        odometerReading = i;
    }

    /**
     * @return
     */
    public String getUserComment() {
        return userComment;
    }

    /**
     * @param userComment
     */
    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    /**
     * @return make and model
     */
    public String getMotorcycle() {
        return motorcycle;
    }

    /**
     * @return first and lastname
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param string
     */
    public void setMotorcycle(String string) {
        motorcycle = string;
    }

    /**
     * @param string
     */
    public void setOwner(String string) {
        owner = string;
    }

    public String toDbString(String delim) {
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.SDF_DB_DATE);
                
        sb.append(this.id);
        sb.append(delim);
        sb.append(this.motorcycleId);
        sb.append(delim);
        sb.append(sdf.format(this.date));
        sb.append(delim);
        sb.append(this.odometerReading);
        sb.append(delim);
        sb.append(this.correction);
        sb.append(delim);
        if (this.userComment != null) {
            sb.append(this.userComment);
        }

        return sb.toString();
    }

    /**
     * Creates a MilesEntry object from a string. The string should contain all fields separated by
     * delimiters. Comment field can be empty. E.g. "1|1|2003-12-31|86765|0|comment"
     * @param inString
     * @param delim
     * @return
     * @throws InvalidDataException In case of an error in the input.
     */
    public static MilesEntry stringToMilesEntry(String inString, String delim) throws InvalidDataException {
   
        Integer i;
        Integer m;
        Date d;
        int o;
        int c;
        String u;
        String nextToken;
        
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

        // motorcycle id
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                throw new InvalidDataException("Motorcycle id cannot be null");
            } else {
                m = new Integer(nextToken);
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

        // date
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                throw new InvalidDataException("Date cannot be null");
            } else {
                try {
                d = new SimpleDateFormat(Constants.SDF_DB_DATE).parse(nextToken);
                } catch(ParseException pe) {
                    throw new InvalidDataException(pe.getMessage(), pe);
                }
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

        // odometer reading
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                throw new InvalidDataException("Odometer reading cannot be null");
            } else {
                o = Integer.parseInt(nextToken);
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

        // correction
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                throw new InvalidDataException("Correction cannot be null");
            } else {
                c = Integer.parseInt(nextToken);
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

        // user comment
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                u = null;
            } else {
                u = nextToken;
            }
        } else {
            // last field is empty (and not mandatory), hence no nextToken
            u = null;
        }

        return new MilesEntry(i, m, d, o, c, u);
    }

}
