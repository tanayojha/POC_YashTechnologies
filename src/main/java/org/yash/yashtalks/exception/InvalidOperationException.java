package org.yash.yashtalks.exception;

/**
 * @author tanay.ojha
 */

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException() {
    }

    public InvalidOperationException(String message) {
        super(message);
    }
}
