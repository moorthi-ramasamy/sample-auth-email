package com.bcbsm.interview.userauth.exception;
/**
 * @author Moorthi Ramasamy
 */
public class ApplicationRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 188872005895190894L;

    public ApplicationRuntimeException(String message) {
        super(message);
    }
}
