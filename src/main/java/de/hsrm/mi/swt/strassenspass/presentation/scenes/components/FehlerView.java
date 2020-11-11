package de.hsrm.mi.swt.strassenspass.presentation.scenes.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class FehlerView extends StackPane {

    public FehlerView (String nachricht, Button button){

        Label text = new Label();
        text.setMaxWidth(250);
        text.setWrapText(true);
        text.setText(nachricht);
        text.setId("kleinertext");
        
        setId("roteBox");
        getChildren().addAll(text, button);
        setAlignment(text, Pos.TOP_CENTER);
        setAlignment(button, Pos.BOTTOM_CENTER);
        setPadding(new Insets(40, 10, 40, 10));

        setLayoutX(550);
        setLayoutY(220);
    }
    
}