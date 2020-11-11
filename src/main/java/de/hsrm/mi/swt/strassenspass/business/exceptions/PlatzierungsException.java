package de.hsrm.mi.swt.strassenspass.business.exceptions;

public class PlatzierungsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public PlatzierungsException(String message){
        super(message);
    }
    
    public PlatzierungsException(){
        super();
    }
}