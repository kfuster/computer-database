package com.excilys.formation.exception;

public class PersistenceException extends Exception{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5930162825862224695L;

    public PersistenceException (String pMessage) {
        super(pMessage);
    }
    
    public PersistenceException (String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
    
    public PersistenceException (Throwable pCause) {
        super(pCause);
    }
}
