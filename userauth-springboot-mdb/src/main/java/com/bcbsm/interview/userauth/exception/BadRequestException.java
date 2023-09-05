package com.bcbsm.interview.userauth.exception;

/**
 * @author Moorthi Ramasamy
 */
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 817995918525157656L;
    public BadRequestException(String message) {
        super(message);
    }
}
