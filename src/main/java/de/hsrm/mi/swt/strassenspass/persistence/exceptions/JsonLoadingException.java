package de.hsrm.mi.swt.strassenspass.persistence.exceptions;

public class JsonLoadingException extends Exception{

    private static final long serialVersionUID = 1L;

    public JsonLoadingException() {
        super("JSON-Datei kann nicht geladen werden.");
    }

    public JsonLoadingException(String message){
        super(message);
    }
}
