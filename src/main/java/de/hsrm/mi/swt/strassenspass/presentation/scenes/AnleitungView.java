package de.hsrm.mi.swt.strassenspass.presentation.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class AnleitungView extends AnchorPane{

    Label anleitung, anleitung1;
    Button zurueckButton;
    
    Label s, p, a, ss;
    HBox ueberschrift;
    
    Label eins;
    Label ende;
    
    public AnleitungView() {
        
    	this.setId("root");
        anleitung = new Label("STRA\u00DFEN");
        s = new Label("S");
        p = new Label("P");
        a = new Label("A");
        ss = new Label("\u00DF");
        ueberschrift = new HBox();
        ueberschrift.getChildren().addAll(anleitung,s,p,a,ss);
        
        eins = new Label("Du bist neu hier? Hier erf\u00e4hrst du alles, was du wissen musst! \n\nUm das Spiel zu starten, dr\u00fccke auf Stra\u00DFennetz erstellen."
                + "\nWenn du ein gespeichertes Stra\u00DFennetz \u00f6ffnen willst, dr\u00fccke Stra\u00DFennetz laden.\n\n"
                + "Funktionen:\n1. Um ein Stra\u00DFenst\u00fcck/Auto in den Plan zu ziehen, dr\u00fccke auf das gew\u00fcnschte St\u00fcck und ziehen es in den Plan.\n"
                + "2. Mit Doppelklick kannst du das Stra\u00DFenst\u00fcck/Auto drehen.\n"
                + "3. Du musst mindestens drei Stra\u00DFenst\u00fccke hinzugef\u00fcgt haben, um ein Auto zu platzieren.\n"
                + "4. Um ein Stra\u00DFenst\u00fcck/Auto zu l\u00f6schen und zu verschieben, dr\u00fccke auf das platzierte St\u00fcck.\n"
                + "5. Damit die Autos fahren, musst du auf Start dr\u00fccken. Daf\u00fcr muss mindestens ein Auto platziert worden sein.\n"
                + "6. Stoppen kannst du indem du auf Stop dr\u00fcckst.\n"
                + "7. Du kannst dein Stra\u00DFennetz speichern indem du auf Stra\u00DFennetz speichern dr\u00fcckst.");
        ende = new Label("Wir w\u00fcnschen dir viel Spa\u00DF beim Spielen!");
        
        anleitung.setId("ueberschrift2");
        s.setId("S2");
        p.setId("P2");
        a.setId("A2");
        ss.setId("SS2");
        
        anleitung.setMaxWidth(Double.MAX_VALUE);
        ueberschrift.setAlignment(Pos.CENTER);
        zurueckButton = new Button("zur\u00fcck");
        zurueckButton.setId("button4");
        zurueckButton.setLayoutX(100);
        zurueckButton.setLayoutY(580);
    
        anleitung1 = new Label("Anleitung");

        ueberschrift.setLayoutX(550);
        ueberschrift.setLayoutY(50);
        anleitung1.setLayoutX(150);
        anleitung1.setLayoutY(120);
        anleitung1.setId("text");
        eins.setLayoutX(150);
        eins.setLayoutY(170);
        eins.setId("info");
        ende.setLayoutX(150);
        ende.setLayoutY(470);
        ende.setId("endetext");
    
        getChildren().addAll(ueberschrift, anleitung1, zurueckButton, eins, ende);
    }
}
