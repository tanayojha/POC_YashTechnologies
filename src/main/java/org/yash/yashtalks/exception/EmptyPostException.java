package org.yash.yashtalks.exception;

public class EmptyPostException extends RuntimeException {

    /**
     */
    private static final long serialVersionUID = 1L;

    public EmptyPostException(String message) {
        super(message);
    }

    public EmptyPostException(String message, Throwable cause) {
        super(message, cause);
    }

}
