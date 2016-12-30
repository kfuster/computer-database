package com.excilys.formation.exception;

public class PersistenceException extends Exception {
    private static final long serialVersionUID = 5930162825862224695L;

    /**
     * PersistenceException constructor accepting a message.
     * @param pMessage the message explaining the exception
     */
    public PersistenceException(String pMessage) {
        super(pMessage);
    }

    /**
     * PersistenceException constructor accepting a message and a cause.
     * @param pMessage the message explaining the exception
     * @param pCause the cause of the exception
     */
    public PersistenceException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }

    /**
     * PersistenceException constructor accepting a cause.
     * @param pCause the cause of the exception
     */
    public PersistenceException(Throwable pCause) {
        super(pCause);
    }
}