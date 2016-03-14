/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: Rider.java,v 1.1 2007/06/01 15:24:05 rvk Exp $
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
import java.util.StringTokenizer;

import nl.wobble.zrmiles.exception.InvalidDataException;

/**
 * The motorcycle rider.
 * @author rvk
 */
public class Rider implements Serializable {

    /**
     * Id of the database record.
     */
    private Integer id;

    /**
     * The rider's first name.
     */
    private String firstName;

    /**
     * The Rider's last name.
     */
    private String lastName;

    /**
     * The rider's email address
     */
    private String emailAddress;

    /**
     * The rider's location (e.g. city, country)
     */
    private String streetAddress;

    /**
     * The rider's username to use the site
     */
    private String userName;

    /**
     * The rider's password to logon to the site
     */
    private String userPassword;

    /**
     * The users role (guest, user, admin)
     */
    private int userRole;

    /**
     * Constructs a Rider with all fields initialized to null.
     */
    public Rider() {
        id = null;
        firstName = null;
        lastName = null;
        emailAddress = null;
        streetAddress = null;
        userName = null;
        userPassword = null;
        userRole = 0;
    }

    /**
     * Constructs a Rider with all fields initialized with the specified values.
     * @param id The id of the corresponding database record
     * @param firstName The rider's first name.
     * @param lastName The rider's last name.
     * @param emailAddress The rider's email address
     * @param streetAddress The rider's street address (e.g city, country).
     */
    public Rider(
        Integer id,
        String firstName,
        String lastName,
        String emailAddress,
        String streetAddress,
        String userName,
        String userPassword,
        int userRole) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.streetAddress = streetAddress;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    /**
     * Gets the rider's street address.
     * @return The rider's straat address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Gets the rider's street address.
     * @return The rider's straat address
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the record id
     * @return The record id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the rider's last name.
     * @return The rider's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the rider's street address.
     * @return The rider's street address.
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the rider's email address.
     * @param emailAddress The rider's email address.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Sets the rider's first name.
     * @param firstName The rider's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the record id.
     * @param id The record id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Sets the rider's last name.
     * @param lastName The rider's last name
     */
    public void setLastName(String string) {
        lastName = string;
    }

    /**
     * Sets the rider's street address.
     * @param streetAddress The rider's street address.
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * @return The rider's username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the rider's password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @return the rider's role
     */
    public int getUserRole() {
        return userRole;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @param userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @param userRole
     */
    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String toDbString(String delim) {
        StringBuffer sb = new StringBuffer();

        sb.append(id);
        sb.append(delim);
        sb.append(firstName);
        sb.append(delim);
        sb.append(lastName);
        sb.append(delim);
        if (emailAddress != null) {
            sb.append(emailAddress);
        }
        sb.append(delim);
        if (streetAddress != null) {
            sb.append(streetAddress);
        }

        return sb.toString();
    }

    /**
     * Creates a Rider object from a string. The string should contain all fields separated by
     * delimiters. E.g. "1|Mark|Reuvekamp|mark@reuvekamp.com|Raalte, NL"
     * @param inString
     * @param delim
     * @return
     * @throws InvalidDataException In case of an error in the input.
     */
    public static Rider stringToRider(String inString, String delim) throws InvalidDataException {
        Integer i;
        String f;
        String l;
        String e;
        String s;
        String n;
        String p;
        int r;
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

        // first name
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                throw new InvalidDataException("First name cannot be null");
            } else {
                f = nextToken;
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

        // last name
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                throw new InvalidDataException("Last name cannot be null");
            } else {
                l = nextToken;
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
        // email address, can be null
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                e = null;
                readDelim = false;
            } else {
                e = nextToken;
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

        //street address, can be null
        if (st.hasMoreTokens()) {
            nextToken = st.nextToken();
            if (nextToken.equals(delim)) {
                s = null;
            } else {
                s = nextToken;
            }
        } else {
            // last field is empty (and not mandatory), hence no nextToken
            s = null;
        }

        n = null; // TODO
        p = null; // TODO
        r = 0; // TODO
        return new Rider(i, f, l, e, s, n, p, r);
    }
}
