package de.hsrm.mi.swt.strassenspass.business.exceptions;

public class SpeicherException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SpeicherException(String message){
        super(message);
    }
    
    public SpeicherException(){
        super();
    }

}