package de.hsrm.mi.swt.strassenspass.persistence.exceptions;

public class JsonWritingException extends Exception {
	
    private static final long serialVersionUID = 1L;

    public JsonWritingException() {
        super("JSON-Datei kann nicht gespeichert werden.");
    }

    public JsonWritingException(String message){
        super(message);
    }
}
