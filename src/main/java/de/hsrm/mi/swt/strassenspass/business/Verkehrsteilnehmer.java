package de.hsrm.mi.swt.strassenspass.business;

import java.beans.PropertyChangeSupport;

import com.google.gson.annotations.Expose;

import de.hsrm.mi.swt.strassenspass.business.components.*;

public class Verkehrsteilnehmer implements Editierbar {
    
    @Expose
    protected double posX, posY;
    
    protected static final double HOEHE = 40;
    protected static final double BREITE = 20;
    protected static double geschwindigkeit;
    
    @Expose
    protected Richtung bewegungsrichtung;

    public PropertyChangeSupport changes = new PropertyChangeSupport(this);
    
    public Verkehrsteilnehmer(int posX, int posY, Richtung bewegungsrichtung) {
    	setPosX(posX);
    	setPosY(posY);
        setBewegungsrichtung(bewegungsrichtung); 	
    }

    public Verkehrsteilnehmer(){
        this.posX = 0;
        this.posY = 0;
        setBewegungsrichtung(Richtung.N);
    }
    
    public double getPosX(){
        return posX;
    }

    public double getPosY(){
        return posY;
    }
    
    public static double getHoehe(){
        return HOEHE;
    }

    public static double getBreite(){
        return BREITE;
    }
    
    public double getGeschwindigkeit(){
    	return geschwindigkeit;
    }

    public Richtung getBewegungsrichtung(){
        return bewegungsrichtung;
    }
    
    public void setPosX(double posX){
        double oldValue = this.posX;
        this.posX = posX;

        changes.firePropertyChange("posX", oldValue, this.posX);
    }

    public void setPosY(double posY){
        double oldValue = this.posY;
        this.posY = posY;

        changes.firePropertyChange("posY", oldValue, this.posY);
    }
    
    public void setBewegungsrichtung(Richtung bewegungsrichtung){
        Richtung oldValue = this.bewegungsrichtung;
        this.bewegungsrichtung = bewegungsrichtung;

        changes.firePropertyChange("bewegungsrichtung", oldValue, this.bewegungsrichtung);

    }
    
	@Override
	public void bewegen(double posX, double posY) {
        setPosX(posX);
        setPosY(posY);
	}
	
	@Override
	public void drehen() {
		setBewegungsrichtung(bewegungsrichtung.getNext());
	}  

}
