package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.application.exception;

/**
 * Created by jbuenosv@google.com
 */
public class VideoAnalyzerServiceException extends RuntimeException {

    public VideoAnalyzerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoAnalyzerServiceException(String message) {
        super(message);
    }

    public VideoAnalyzerServiceException(Throwable cause) {
        super(cause);
    }

}