package com.excilys.formation.exception;

public class ServiceException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 5189890662753717305L;

    public ServiceException (String pMessage) {
        super(pMessage);
    }
    
    public ServiceException (String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
    
    public ServiceException (Throwable pCause) {
        super(pCause);
    }
}
