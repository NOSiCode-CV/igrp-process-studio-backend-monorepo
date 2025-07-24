package cv.igrp.framework.process.management.integration.core.exception;


public class ProcessDefinitionException extends RuntimeException {

    public ProcessDefinitionException(String message) {
        super(message);
    }

    public ProcessDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessDefinitionException(Throwable cause) {
        super(cause);
    }

}
