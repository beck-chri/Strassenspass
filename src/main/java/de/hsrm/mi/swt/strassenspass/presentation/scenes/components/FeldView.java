package de.hsrm.mi.swt.strassenspass.presentation.scenes.components;

import de.hsrm.mi.swt.strassenspass.business.Strassenstueck;
import javafx.scene.layout.StackPane;

public class FeldView extends StackPane {
    
    public FeldView(Strassenstueck strasse) {

        setPrefHeight(Strassenstueck.getHoehe());
        setPrefWidth(Strassenstueck.getBreite());
        setLayoutX(strasse.getPosX());
        setLayoutY(strasse.getPosY());
        setId("leer");
    }
    
}