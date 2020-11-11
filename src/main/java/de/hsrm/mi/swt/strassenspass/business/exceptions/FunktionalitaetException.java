package de.hsrm.mi.swt.strassenspass.business.exceptions;

public class FunktionalitaetException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FunktionalitaetException(String message){
        super(message);
    }
    
    public FunktionalitaetException(){
        super();
    }
    
}