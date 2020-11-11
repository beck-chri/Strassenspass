package de.hsrm.mi.swt.strassenspass.presentation.scenes.components;

import de.hsrm.mi.swt.strassenspass.business.*;
import de.hsrm.mi.swt.strassenspass.business.components.Richtung;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class AutoView extends BorderPane {

    Auto auto;
    ImageView image;

    double posX, posY;
    double hoehe, breite;
    Richtung richtung;

    public AutoView(Auto auto) {
        this.auto = auto;
        posX = auto.getPosX();
        posY = auto.getPosY();

        hoehe = Strassenstueck.getHoehe() / 2;
        breite = Strassenstueck.getBreite() / 2;

        image = new ImageView();
        image.setFitHeight(Auto.getHoehe());
        image.setFitWidth(Auto.getBreite());
        image.setImage(new Image(getClass().getResource("/images/" + auto.getFarbe().name().toLowerCase() + ".png").toString()));
        getChildren().add(image);

        setMinHeight(hoehe);
        setMinWidth(breite);
        setMaxHeight(hoehe);
        setMaxWidth(breite);

        setRotate(auto.getBewegungsrichtung().getWinkel());
        setStyle("-fx-background-color: transparent; -fx-padding: 0 0 5 3;");
        setAlignment(image, Pos.BASELINE_LEFT);

        setLayoutX(posX);
        setLayoutY(posY);

        init();
    }

    public void init(){

        auto.changes.addPropertyChangeListener("bewegungsrichtung", e -> {
            if(!auto.faehrt()) setRotate(auto.getBewegungsrichtung().getWinkel());
        });

        auto.changes.addPropertyChangeListener("posX", e-> {
            if(auto.faehrt()) startAnimation();
            if(!auto.faehrt()) posX = auto.getPosX();
        });

        auto.changes.addPropertyChangeListener("posY", e-> {
            if(auto.faehrt()) startAnimation();
            if(!auto.faehrt()){
                posY = auto.getPosY();
                setLayoutX(posX);
                setLayoutY(posY);
            }
        });
    }

    public void startAnimation(){
       
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(auto.getGeschwindigkeit()));
        transition.setNode(this);

        transition.setByX(auto.getPosX()-posX);
        transition.setByY(auto.getPosY()-posY);
        transition.play();

        posX = auto.getPosX();
        posY = auto.getPosY();

        setRotate(auto.getBewegungsrichtung().getWinkel());
    }

    public Auto getAuto(){
        return this.auto;
    }

}