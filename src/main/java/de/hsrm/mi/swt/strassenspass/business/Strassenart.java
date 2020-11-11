package de.hsrm.mi.swt.strassenspass.business;

import java.beans.PropertyChangeSupport;

import com.google.gson.annotations.Expose;

import de.hsrm.mi.swt.strassenspass.business.components.*;

public abstract class Strassenart<T extends Verkehrsteilnehmer> implements Editierbar{

    protected static final double HOEHE = 110.0;
    protected static final double BREITE = HOEHE;
    
    @Expose
    protected double posX, posY;

    @Expose
    protected T verkehsteilnehmer;

    public PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public Strassenart(double posX, double posY){
        bewegen(posX, posY);
    }

    public Strassenart() {}

    public static double getHoehe(){
        return HOEHE;
    }

    public static double getBreite(){
        return BREITE;
    }

    public double getPosX(){
        return posX;
    }

    public double getPosY(){
        return posY;
    }

    public T getVerkehrsteilnehmer(){
        return this.verkehsteilnehmer;
    }

    public void setPosX(double posX){
        double oldValue = posX;
        this.posX = posX;

        changes.firePropertyChange("posX", oldValue, this.posX);
    }

    public void setPosY(double posY){
        double oldValue = posY;
        this.posY = posY;

        changes.firePropertyChange("posY", oldValue, this.posY);
    }

    public void setVerkehrsteilnehmer(T verkehrsteilnehmer){
        T oldValue = this.verkehsteilnehmer;
        this.verkehsteilnehmer = verkehrsteilnehmer;

        changes.firePropertyChange("verkehrsteilnehmer", oldValue, this.verkehsteilnehmer);
    }

    @Override
    public void bewegen(double posX, double posY){
        setPosX(posX);
        setPosY(posY);
    }

    public abstract void drehen();

    public abstract boolean isKompatibel(Strassenart<T> s, Richtung r);

}