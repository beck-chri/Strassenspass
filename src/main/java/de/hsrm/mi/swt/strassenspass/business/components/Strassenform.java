package de.hsrm.mi.swt.strassenspass.business.components;

public enum Strassenform {

    GERADE(Richtung.N, Richtung.S), KURVE(Richtung.N, Richtung.O), ABZWEIGUNG(Richtung.N, Richtung.O, Richtung.S), KREUZUNG(Richtung.N, Richtung.O, Richtung.S, Richtung.W);

    private Richtung [] andockstellen;

    private Strassenform(Richtung ... andockstellen){
        this.andockstellen = andockstellen;
    }

    public Richtung[] getAndockstellen(){
        return this.andockstellen;
    }
}

