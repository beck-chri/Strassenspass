package de.hsrm.mi.swt.strassenspass.business.components;

public enum Richtung {

    N(0), O(90), S(180), W(270);

    private double winkel;

    private Richtung(int winkel){
        this.winkel = winkel;
    }

    public double getWinkel(){
        return this.winkel;
    }

    public static Richtung getByWinkel(double winkel){
        //Test: prüfen ob die richtige Richtung für zB 90 Grad zurückgegeben wird
        winkel = (winkel >= 360) ? (winkel-360) : winkel;
        for(Richtung r: Richtung.values()){
            if (r.getWinkel() == winkel) return r; 
        }
        return null;
    }

    public double setPosX(double posX, double breite){
        //Das ist für die Koordinaten neuer Felder, muss eigentlich nicht überprüft werden
        switch(this){
            case N: return posX;
            case O: return posX+breite+0.01;
            case S: return posX;
            case W: return posX-breite-0.01;
        }
        return posX;
    }

    public double setPosY(double posY, double hoehe){
        //Das ist für die Koordinaten neuer Felder, muss eigentlich nicht überprüft werden
        switch(this){
            case N: return posY-hoehe-0.01;
            case O: return posY;
            case S: return posY+hoehe+0.01;
            case W: return posY;
        }
        return posY;
    }

    
    public Richtung getNext() {
        //Test: prüfen ob die nächste Richtung zurückgegeben wird
        return getByWinkel(this.winkel+90);
    }

    public Richtung getOpposite(){
        //Test: prüfen ob die Gegenseite zurückgegeben wird
        return getByWinkel(this.winkel+180);
    }
}
