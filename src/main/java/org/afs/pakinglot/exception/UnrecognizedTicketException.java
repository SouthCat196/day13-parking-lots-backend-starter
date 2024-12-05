package org.afs.pakinglot.exception;

public class UnrecognizedTicketException extends RuntimeException {
    public UnrecognizedTicketException() {
        super("Unrecognized parking ticket.");
    }
}
