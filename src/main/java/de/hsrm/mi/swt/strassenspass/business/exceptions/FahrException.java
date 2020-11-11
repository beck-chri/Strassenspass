package de.hsrm.mi.swt.strassenspass.business.exceptions;

public class FahrException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FahrException(String message){
        super(message);
    }
    
    public FahrException(){
        super();
    }
    
}