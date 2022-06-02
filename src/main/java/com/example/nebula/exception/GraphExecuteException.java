package com.example.nebula.exception;

/**
 * @author fulin by 2022/3/25
 */
public class GraphExecuteException extends RuntimeException {
    public GraphExecuteException() {
    }

    public GraphExecuteException(String message) {
        super(message);
    }

    public GraphExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphExecuteException(Throwable cause) {
        super(cause);
    }

    public GraphExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
