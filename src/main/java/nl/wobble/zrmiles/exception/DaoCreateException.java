/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: DaoCreateException.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.exception;

/**
 * @author rvk
 */
public class DaoCreateException extends ZrMilesException {

    public DaoCreateException(String message) {
        super(message);
    }

    public DaoCreateException(String message, Exception rootCause) {
        super(message, rootCause);
    }
}
