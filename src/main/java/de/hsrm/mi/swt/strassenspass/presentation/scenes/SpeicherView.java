package de.hsrm.mi.swt.strassenspass.presentation.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class SpeicherView extends AnchorPane {
    
    Label logo;
    Label s, p, a, ss;
    HBox ueberschrift;
    StackPane blaueBox;
    Label speichern;
    Label name;
    Button speicherButton;
    TextField textfield;
    
    public SpeicherView() {
        
        logo = new Label("STRA\u00DFEN");
        s = new Label("S");
        p = new Label("P");
        a = new Label("A");
        ss = new Label("\u00DF");
        speichern = new Label("\nStra\u00DFennetz speichern");
        name = new Label("\n\n\n\n\n\n         Bitte gebe hier deinen Namen ein:");
        ueberschrift = new HBox();
        blaueBox = new StackPane();
        textfield = new TextField();
        ueberschrift.getChildren().addAll(logo,s,p,a,ss);
        speicherButton = new Button("Jetzt speichern");
        speicherButton.setId("button6");
        this.setId("root");
        
        
        blaueBox.setId("blaueBox");
        blaueBox.getChildren().addAll(speichern,name);
        StackPane.setAlignment(speichern, Pos.TOP_CENTER);
        StackPane.setAlignment(name, Pos.TOP_LEFT);
        logo.setId("ueberschrift2");
        s.setId("S2");
        p.setId("P2");
        a.setId("A2");
        ss.setId("SS2");
        speichern.setId("text");
        name.setId("info");
        
        speicherButton.setLayoutX(800);
        speicherButton.setLayoutY(450);
        blaueBox.setLayoutX(300);
        blaueBox.setLayoutY(170);
        ueberschrift.setLayoutX(550);
        ueberschrift.setLayoutY(50);
        textfield.setLayoutX(590);
        textfield.setLayoutY(290);
        textfield.setId("textfeld");
        
        this.getChildren().addAll(ueberschrift, blaueBox,speicherButton, textfield);
        
    }
}
