package de.hsrm.mi.swt.strassenspass.business;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import de.hsrm.mi.swt.strassenspass.business.Auto.Autofarbe;
import de.hsrm.mi.swt.strassenspass.business.components.*;
import de.hsrm.mi.swt.strassenspass.business.exceptions.*;
import de.hsrm.mi.swt.strassenspass.persistence.JsonHandler;
import de.hsrm.mi.swt.strassenspass.persistence.exceptions.JsonLoadingException;
import de.hsrm.mi.swt.strassenspass.persistence.exceptions.JsonWritingException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Strassennetz {

	private SimpleBooleanProperty fahrbar;
	private SimpleBooleanProperty empty;
	private JsonHandler jsonhandler;
	private ObservableList<Strassenstueck> strassenstuecke;
	
	@Expose
	private ArrayList<Strassenstueck> strassenstueckeListe;

	private static ArrayList<Strassenstueck> strassenformen;
	private static ArrayList<Auto> autofarben;

	private boolean faehrt;
	
	@Expose
	private String name;
	@Expose
	private static double hoehe, breite;
	@Expose
	private static double posX, posY;
	

	public Strassennetz() {
		strassenstuecke = FXCollections.observableArrayList();
		strassenstueckeListe = new ArrayList<Strassenstueck>();
		jsonhandler = new JsonHandler();

		fahrbar = new SimpleBooleanProperty(false);
		empty = new SimpleBooleanProperty(true);

		faehrt = false;
		name = null;

		hoehe = 550;
		breite = 780;
		posX = 15;
		posY = 100;

		strassenformen = new ArrayList<>();
		for (Strassenform f : Strassenform.values()) {
			strassenformen.add(new Strassenstueck(f));
		}

		autofarben = new ArrayList<>();
		for (Autofarbe a : Autofarbe.values()) {
			autofarben.add(new Auto(a));
		}
	}

	public static double getPosX() {
		return posX;
	}

	public static double getPosY() {
		return posY;
	}

	public static double getHoehe() {
		return hoehe;
	}

	public static double getBreite() {
		return breite;
	}

	public static ArrayList<Strassenstueck> getStrassenformen() {
		return strassenformen;
	}

	public static ArrayList<Auto> getAutofarben() {
		return autofarben;
	}

	public SimpleBooleanProperty getFahrbarProperty() {
		return fahrbar;
	}

	public boolean isFahrbar() {
		return fahrbar.get();
	}

	public boolean isEmpty() {
		return strassenstuecke.isEmpty();
	}

	public SimpleBooleanProperty getEmptyProperty() {
		return empty;
	}

	public void setEmptyProperty(boolean value) {
		empty.set(value);
	}

	public boolean faehrt() {
		return faehrt;
	}

	public ObservableList<Strassenstueck> getStrassenstuecke() {
		return strassenstuecke;
	}

	public String getName() {
		return name;
	}

	public void setFahrbarProperty(boolean fahrbar) {
		this.fahrbar.set(fahrbar);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void platziere(Strassenstueck vorlage, double x, double y) throws PlatzierungsException {
		// Test: prüfen ob bei ersten Stück im Plan die Koordinaten genauso übernommen
		// werden
		// Test: prüfen ob bei weiteren Stücken die Koordinaten angepasst werden
		// Test: prüfen ob Platzierung auch verweigert werden kann, wenn das Stück an
		// einer anderen Stelle als den angrenzenden Feldern platziert wird, also ob die
		// Exception geworfen wird

		// Das erste Stück kann frei platziert werden

		if (isEmpty()) {
			Strassenstueck first = new Strassenstueck(vorlage, x, y);
			strassenstueckHinzufuegen(first);
			return;
		}

		// alle weiteren Stücke können nur auf leeren, kompatiblen Feldern platziert
		// werden
		for (Strassenstueck feld : getOptionen(vorlage)) {
			if (feld.enthaelt(x, y)) {
				feld.init(vorlage);
				verknuepfen(feld);
				strassenstueckHinzufuegen(feld);
				return;
			}
		}
		throw new PlatzierungsException("Konnte kein Feld finden");
	}

	public void verknuepfen(Strassenstueck neu) {
        Strassenstueck nachbar = null;
        for(Richtung richtung : Richtung.values()){
            nachbar = getStrassenstueck(richtung.setPosX(neu.getPosX(), Strassenstueck.getBreite()), richtung.setPosY(neu.getPosY(), Strassenstueck.getBreite()));
            if(nachbar != null && !nachbar.isEmpty()) {
                neu.getNachbarn().replace(richtung, nachbar); 
                nachbar.getNachbarn().replace(richtung.getOpposite(), neu);
            } nachbar = null;
        }
    }

    public List<Strassenstueck> getOptionen(Strassenstueck neu){
    //Die Methode sucht für die Platzierung eines neuen Stücks alle möglichen passende Felder, die sollen später angezeigt werden während des Drag and Drop Vorgangs
    //Bin noch am Überlegen, was man da gut Testen kann
        List<Strassenstueck> optionen = new ArrayList<>();
        for(Strassenstueck gespeichert : strassenstuecke){
            for(Richtung richtung : Richtung.values()){
                Strassenstueck nachbar = gespeichert.getNachbarn().get(richtung);
                if(nachbar == null) continue;
                if(nachbar.isEmpty() && gespeichert.isKompatibel(neu, richtung)) optionen.add(nachbar);
            }
        } return optionen;
    }

    public void strassenstueckHinzufuegen(Strassenstueck neu) {
		//Test: prüfen ob bei mehr als 3 hinzugefügten Stücken fahrbar auf true gesetzt wird
		strassenstuecke.add(neu);
        strassenstueckeListe.add(neu);
        if(strassenstuecke.size() >=1) setEmptyProperty(false);
        if(strassenstuecke.size() >= 3) setFahrbarProperty(true); 
    }

    public void strassenstueckEntfernen(Strassenstueck s) throws FunktionalitaetException, EntfernenException {
        //Test: prüfen ob EntfernenExcepton geworfen werden, wenn sich ein Auto auf dem Stück befindet
        //Test: prüfen ob FunktionalitaetException geworfen wird, wenn durch Löschen weniger als 3 Stücke vorhanden wären
        //Test: falls kein Error geworfen wird, prüfen ob das Feld auf leer gesetzt wird und aus der Liste entfernt wird

        if(s.getVerkehrsteilnehmer() != null) throw new EntfernenException("Auf diesem Stück befindet sich ein Auto. Dein Auto wird nun entfernt.");
        if(strassenstuecke.size() == 3) throw new FunktionalitaetException("Wenn du dieses Stück entfernst, können vorerst keine Autos mehr fahren.");
    
        s.clear();
        strassenstuecke.remove(s);
        strassenstueckeListe.remove(s);
        if(strassenstuecke.size() > 3) setFahrbarProperty(false);
        if(isEmpty()) setEmptyProperty(true);
    }

    public Strassenstueck getStrassenstueck(double posX, double posY){
        //Test: prüfen ob sich ein platziertes Strassenstück durch die Koordinaten wieder finden lässt
        for(Strassenstueck s : strassenstuecke){
            if(s.enthaelt(posX, posY)) return s;
        }
        return null;
    }

    public void starten() throws FahrException {
        //Test: prüfen ob Fahrexception geworfen wird, wenn fahrbar false ist
        //wie man das Fahren prüfen kann weiß ich noch nicht, bin auch nicht 100% sicher, dass es grade funktioniert
        //gucke da nochmal drüber
        if(!isFahrbar()) throw new FahrException();
        else {
            for(Strassenstueck s : strassenstuecke){
                if(s.getVerkehrsteilnehmer() != null){
                    s.getVerkehrsteilnehmer().starten();
                    s.getVerkehrsteilnehmer().fahren(s);
                }
            } faehrt = true;
        }
    }

    public void stoppen(){
        //das müsste aber schon funktionieren
        //Test: ob bei Stoppen der Wert in Auto auf false gesetzt wird und die AktPos wieder auf die Ausgangsposition gesetzt wird
        for(Strassenstueck s : strassenstuecke){
            if(s.getVerkehrsteilnehmer() != null){
				s.getVerkehrsteilnehmer().stoppen();
				s.getVerkehrsteilnehmer().setAktPosition(s);
            }
        } faehrt = false;
    }

    public void speichern() throws SpeicherException {
        //Test: prüfen ob ohne Platzierung SpeicherException geworfen wird
		if(isEmpty()) throw new SpeicherException("Du musst mindestens ein Stück platzieren, um dein Verkehrsnetz speichern zu können.");
		try{
			jsonhandler.strassennetzSpeichern(this);
		} catch(JsonWritingException e){
			throw new SpeicherException("Hoppla, da ist wohl etwas schief gelaufen. Buitte versuche es erneut");
		}
	}
	
	public void laden(String name){
		try{
			Strassennetz strassennetz = jsonhandler.getStrassennetz(name);
			this.name = strassennetz.name;
			this.strassenstuecke.clear();
			for(Strassenstueck s : strassennetz.getStrassenstueckeListe()){
				try{
					platziere(s, s.getPosX(), s.getPosY());
				} catch(PlatzierungsException e){
					strassenstueckHinzufuegen(s);
					verknuepfen(s);
				}
			}
		} catch(JsonLoadingException e){
			throw new SpeicherException("Dieser Spielstand scheint fehlerhaft zu sein und kann nicht geladen werden");
		}
	}

	public ArrayList<Strassennetz> getSavings(){
		return jsonhandler.getGespeicherteStrassennetze();
	}

	public ArrayList<Strassenstueck> getStrassenstueckeListe() {
		return strassenstueckeListe;
	}

	@Override
	public String toString() {
		return "Strassennetz [fahrbar=" + fahrbar + ", empty=" + empty + ", strassenstuecke=" + strassenstuecke
				+ ", strassenstueckeListe=" + strassenstueckeListe + ", faehrt=" + faehrt + ", name=" + name + "]";
	}

	
    

}
