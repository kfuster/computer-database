package com.excilys.formation.exception;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 5189890662753717305L;

    /**
     * ServiceException constructor accepting a message.
     * @param pMessage the message explaining the exception
     */
    public ServiceException(String pMessage) {
        super(pMessage);
    }

    /**
     * ServiceException constructor accepting a message and a cause.
     * @param pMessage the message explaining the exception
     * @param pCause the cause of the exception
     */
    public ServiceException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }

    /**
     * ServiceException constructor accepting a cause.
     * @param pCause the cause of the exception
     */
    public ServiceException(Throwable pCause) {
        super(pCause);
    }
}