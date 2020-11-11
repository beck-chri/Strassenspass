package de.hsrm.mi.swt.strassenspass.business;

import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.annotations.Expose;

import de.hsrm.mi.swt.strassenspass.business.components.*;
import de.hsrm.mi.swt.strassenspass.business.exceptions.*;
import javafx.beans.property.SimpleBooleanProperty;

public class Strassenstueck extends Strassenart<Auto> {
	@Expose
    private SimpleBooleanProperty empty;
    @Expose
    private Strassenform strassenform;
    @Expose
    private Richtung [] andockstellen;
    private HashMap<Richtung, Strassenstueck> nachbarn; 
    @Expose
    private int ausrichtung;
    private boolean drehen;

    public Strassenstueck(Strassenform strassenform){
        //Nur für Drag and Drop Vorlagen, muss nicht getestet werden, funktioniert ja in der View :)
        this.strassenform = strassenform;
        this.andockstellen = strassenform.getAndockstellen().clone();

        this.empty = new SimpleBooleanProperty(false);
        this.ausrichtung = 0;
        this.drehen = false;

        switch(strassenform){ 
        case GERADE: bewegen(880, 160); break;
        case KURVE: bewegen(1000, 160); break;
        case ABZWEIGUNG: bewegen(1120, 160); break;
        case KREUZUNG: bewegen(1240, 160); break;
        }
        
        nachbarn = null;
    }
 
    public Strassenstueck(boolean empty, Strassenstueck nachbar, Richtung richtung) {
        //Konstruktor für leere Felder für den nächsten Zug
        //Test: prüfen ob bei Aufruf nach Platzierung für alle Andockstellen des Strassenstücks leere Nachbarn erstellt werden ??
        this.empty = new SimpleBooleanProperty(empty);
        strassenform = null;
        andockstellen = null;
        ausrichtung = 0;
        drehen = false;

        setPosX(richtung.setPosX(nachbar.getPosX(), BREITE));
        setPosY(richtung.setPosY(nachbar.getPosY(), HOEHE));
        
        nachbarn = new HashMap<>();
        nachbarn.put(richtung.getOpposite(), nachbar);
    }

    public Strassenstueck(Strassenstueck s, double x, double y){
        //Konstruktor für das erste Stück, das platziert wird
        empty = new SimpleBooleanProperty(false);
        bewegen(x,y);

        drehen = false;

        strassenform = s.getStrassenform();
        andockstellen = s.getAndockstellen().clone();
        setAusrichtung(s.getAusrichtung());

        verkehsteilnehmer = null;
        
        nachbarn = new HashMap<>();
            for(Richtung richtung : Richtung.values()){
                if(isAndockstelle(richtung)) nachbarn.put(richtung, new Strassenstueck(true, this, richtung));
                else nachbarn.put(richtung, null);
        }
    }

    public void init(Strassenstueck s){
        //Initialisierung eines leeren Feldes, wenn neues Strassenstück darauf gezogen wird
        //Test: Prüfen, ob ein Strassenstück kopiert wird, wenn es in den Bereich zwischen posX und Breite, posY und Hoehe gezogen wird -> Koordinaten und Nachbarn unterscheiden sich aber, daher kein normales equals
        //eigentlich nur testen, ob Strassenform übernommen wird und empty false gesetzt wird
        empty = new SimpleBooleanProperty(false);
        
        strassenform = s.getStrassenform();
        andockstellen = s.getAndockstellen().clone();
        setAusrichtung(s.getAusrichtung());
        
        drehen = false;
        verkehsteilnehmer = null;

        for(Richtung richtung: Richtung.values()){
            if(nachbarn.keySet().contains(richtung)) continue;
            if(isAndockstelle(richtung)) nachbarn.put(richtung, new Strassenstueck(true, this, richtung));
            else nachbarn.put(richtung, null);
        }
    }

    public void clear(){
        //Test: Überprüfen, ob ein Strassenstück beim Löschen aus der Liste in Strassennetz wieder auf empty=true gesetzt wird
        empty.setValue(true);
        verkehsteilnehmer = null;
        andockstellen = null;
        
        Strassenform oldValue = strassenform;
        strassenform = null;

        changes.firePropertyChange("strassenform", oldValue, strassenform);
    }

    public Richtung [] getAndockstellen(){
        return andockstellen;
    }

    public boolean isEmpty(){
        return empty.get();
    }

    public Strassenform getStrassenform() {
        return strassenform;
    }

    public HashMap<Richtung, Strassenstueck> getNachbarn(){
        return nachbarn;
    }

    public int getAusrichtung(){
        return ausrichtung;
    }

    public SimpleBooleanProperty getEmpty(){
        return empty;
    }

    public void setAndockstellen(Richtung[] andockstellen){
        this.andockstellen = andockstellen.clone();
    }

    public void setAusrichtung(int ausrichtung){
        int oldValue = getAusrichtung();
        
        ausrichtung = (ausrichtung >= 360) ? ausrichtung-360 : ausrichtung;
        if(ausrichtung%90 == 0) this.ausrichtung = ausrichtung;
        
        changes.firePropertyChange("ausrichtung", oldValue, getAusrichtung());
    }

    public void setNachbarn(HashMap<Richtung, Strassenstueck> nachbarn){
        if(isEmpty()) return;
        this.nachbarn = nachbarn;
    }

    public boolean isAndockstelle(Richtung richtung){
        for(Richtung r : getAndockstellen()){
            if(r.equals(richtung)) return true;
        }
        return false;
    }

    public boolean sollDrehen(){
        return drehen;
    }

    public Strassenstueck getNachbar(Richtung richtung) {
        drehen = false;
        if(isAndockstelle(richtung) && !nachbarn.get(richtung).isEmpty()) return nachbarn.get(richtung);
        else{
            for(Richtung neueRichtung : andockstellen){
                if(!nachbarn.get(neueRichtung).isEmpty() && !neueRichtung.equals(richtung.getOpposite())){ 
                    drehen = true;
                    return nachbarn.get(neueRichtung);
                }
            } return this;
        }
    }

    public void autoHinzufuegen(Auto neu) throws PlatzierungsException{
        if(verkehsteilnehmer != null) throw new PlatzierungsException("Hoppla! Hier befindet sich bereits ein Auto. Wähle bitte ein anderes Straßenstück aus.");
        else {
            platziereAuto(neu);
            setVerkehrsteilnehmer(neu);
            neu.setAktPosition(this);
        }
    }

    public void platziereAuto(Auto auto) {
        //Test: prüfen, ob PlatzierungsException geworfen wird, wenn Strassenstück schon ein Auto hat
        //wüsste jetzt aber nicht, was hier nicht funktionieren könnte
        double [] koordinaten = getMittelpunkt();
        
        if(!isAndockstelle(auto.getBewegungsrichtung().getOpposite())){
            for(Richtung richtung : Richtung.values()){
                if(isAndockstelle(richtung)) auto.setBewegungsrichtung(richtung.getOpposite());
            }
        }

        switch(auto.getBewegungsrichtung()){
            case N: break;
            case O: koordinaten[0] = getPosX(); break;
            case S: koordinaten[0] = getPosX(); koordinaten[1] = getPosY(); break;
            case W: koordinaten[1] = getPosY(); break;
        }
                
        auto.setPosX(koordinaten[0]);
        auto.setPosY(koordinaten[1]);
    }

    public boolean enthaelt(double x, double y){
        if(x >= getPosX() && x <= (getPosX()+BREITE)  &&  y >= getPosY() && y <= (getPosY()+HOEHE)) return true;
        else return false;
    }

    public double[] getMittelpunkt(){
        double[] mid = {getPosX() + BREITE/2, getPosY() + HOEHE/2};
        return mid;
    }


    @Override
    public boolean isKompatibel(Strassenart<Auto> s, Richtung richtung) {
        //Mitgegebene Richtung ist die Andockstelle des momentanten Stücks, s ist das neue Strassenstück das dort angesetzt werden soll     
        Strassenstueck strasse = (Strassenstueck)s;
        if(strasse.isAndockstelle(richtung.getOpposite()) && isAndockstelle(richtung)) {
            return true;
        }
        return false;
    }

    @Override
    public void drehen(){
        setAusrichtung(getAusrichtung()+90);
        
        Richtung[] neu = new Richtung[andockstellen.length];
        for(int i = 0; i < andockstellen.length; i++){
            neu[i] = andockstellen[i].getNext();
        } setAndockstellen(neu);

        if(verkehsteilnehmer!= null) platziereAuto(verkehsteilnehmer);
        if(nachbarn == null) return;

        HashMap <Richtung, Strassenstueck> neueNachbarn = new HashMap<>();
        for (Richtung richtung : Richtung.values()){
            if(nachbarn.get(richtung) != null && !nachbarn.get(richtung).isEmpty()){
                neueNachbarn.put(richtung, nachbarn.get(richtung));
            } else {
                if(isAndockstelle(richtung)) neueNachbarn.put(richtung, new Strassenstueck(true, this, richtung));
                else neueNachbarn.put(richtung, null);
            }
        } setNachbarn(neueNachbarn);
    }

	@Override
	public String toString() {
		return "Strassenstueck [empty=" + empty + ", verkehrsteilnehmer=" + verkehsteilnehmer + ", strassenform=" + strassenform + ", andockstellen="
				+ Arrays.toString(andockstellen) + ", nachbarn=" + nachbarn + ", ausrichtung=" + ausrichtung
				+ ", drehen=" + drehen + "]";
	}
    

}
