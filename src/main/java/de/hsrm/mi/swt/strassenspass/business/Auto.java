package de.hsrm.mi.swt.strassenspass.business;

import com.google.gson.annotations.Expose;

import de.hsrm.mi.swt.strassenspass.business.components.*;

public class Auto extends Verkehrsteilnehmer{

	public enum Autofarbe{ ROSA, BLAU, GRUEN; }
	private Strassenstueck aktPosition;
	private boolean fahren;
	
	@Expose
	private Autofarbe autofarbe;
	
	public Auto(Autofarbe autofarbe) {
		//Nur für Drag and Drop Vorlagen
		super();
		this.autofarbe = autofarbe;
		this.bewegungsrichtung = Richtung.N;

		switch(autofarbe){
			case ROSA: bewegen(1050, 400); break;
			case BLAU: bewegen(1100, 400); break;
			case GRUEN: bewegen(1150, 400); break;
		}
	}
     
	public Auto(int posX, int posY, Richtung bewegungsrichtung, Autofarbe autofarbe) {
		super(posX, posY, bewegungsrichtung);
		this.autofarbe = autofarbe;
		aktPosition = null;
		geschwindigkeit = 2;
		fahren = false;
	}

	public Auto(Auto a){
		this.autofarbe = a.autofarbe;
		setPosX(a.getPosX());
		setPosY(a.getPosY());
		aktPosition = null;
		geschwindigkeit = 2;
		fahren = false;
	}

	public boolean faehrt(){
		return fahren;
	}
	
	public Strassenstueck getAktPosition(){
		return aktPosition;
	}

	public Autofarbe getFarbe(){
		return autofarbe;
	}
	
    public void setAktPosition(Strassenstueck aktPosition){
		this.aktPosition = aktPosition;
		aktPosition.platziereAuto(this);
	}

	public void stoppen(){
		fahren = false;
	}
	
	public void starten(){
		fahren = true;
	}

	public void fahren(Strassenstueck s) {

		Auto auto = this;
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while(fahren){

					Strassenstueck neuPosition = (Strassenstueck)(aktPosition.getNachbar(bewegungsrichtung));

					if(!neuPosition.equals(aktPosition)) {
						if(aktPosition.sollDrehen()){
							for(Richtung drehRichtung : Richtung.values()){
								if(aktPosition.getNachbarn().get(drehRichtung) != null && aktPosition.getNachbarn().get(drehRichtung).equals(neuPosition)) {
									setBewegungsrichtung(drehRichtung);
									auto.setAktPosition(neuPosition);
								}
							}
						}
						else{
							auto.setAktPosition(neuPosition);
						}
					}
					else {
						auto.setBewegungsrichtung(bewegungsrichtung.getOpposite());
						aktPosition.platziereAuto(auto);
					}

					try{
						Thread.sleep((long)(geschwindigkeit * 1000));
					} catch(InterruptedException e){ 
						e.printStackTrace();
					}
				}
				
			}
		}); 		
		thread.setDaemon(true);
		thread.start();

	}
}
