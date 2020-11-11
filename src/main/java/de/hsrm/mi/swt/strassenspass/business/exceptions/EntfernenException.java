package de.hsrm.mi.swt.strassenspass.business.exceptions;

public class EntfernenException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EntfernenException(String message){
        super(message);
    }
    
    public EntfernenException(){
        super();
    }
}