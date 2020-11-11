package de.hsrm.mi.swt.strassenspass.presentation.scenes.components;

import de.hsrm.mi.swt.strassenspass.business.*;
import de.hsrm.mi.swt.strassenspass.business.exceptions.EntfernenException;
import de.hsrm.mi.swt.strassenspass.business.exceptions.FunktionalitaetException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import de.hsrm.mi.swt.strassenspass.presentation.scenes.*;

public class StrassenView extends StackPane {

    Strassenstueck strasse;
    VerkehrsnetzView rootView;
    VerkehrsnetzViewController controller;

    ImageView image;
    VBox toolBox;
    Button drehen, bewegen, entfernen, bigDrehen;
    boolean clicked;

    public StrassenView(Strassenstueck strasse, VerkehrsnetzView view, VerkehrsnetzViewController controller) {
        this.strasse = strasse;
        this.rootView = view;
        this.controller = controller;
        this.clicked = false;

        image = new ImageView();
        image.setFitHeight(Strassenstueck.getHoehe());
        image.setFitWidth(Strassenstueck.getBreite());
        image.setImage(new Image(getClass().getResource("/images/"+strasse.getStrassenform().name().toLowerCase()+".png").toString()));
        image.setRotate(strasse.getAusrichtung());

        setId("normal");
        setLayoutX(strasse.getPosX());
        setLayoutY(strasse.getPosY());
        setPrefHeight(Strassenstueck.getHoehe());
        setPrefWidth(Strassenstueck.getBreite());

        toolBox = new VBox();

        drehen = new Button();
        drehen.getStyleClass().add("icon-button");
        drehen.setId("drehen-button");

        bewegen = new Button();
        bewegen.getStyleClass().add("icon-button");
        bewegen.setId("bewegen-button");

        entfernen = new Button();
        entfernen.getStyleClass().add("icon-button");
        entfernen.setId("entfernen-button");

        toolBox.setSpacing(5);
        toolBox.setMaxHeight(Strassenstueck.getHoehe());
        toolBox.setMaxWidth(50);
        toolBox.setAlignment(Pos.TOP_CENTER);
        toolBox.getChildren().addAll(drehen, bewegen, entfernen);
        toolBox.setLayoutX(strasse.getPosX() + Strassenstueck.getBreite() + toolBox.getWidth());
        toolBox.setLayoutY(strasse.getPosY() + Strassenstueck.getHoehe());
        toolBox.setPadding(new Insets(8, 10, 8, 10));
        toolBox.setId("root");
        toolBox.setVisible(false);

        getChildren().add(image);
        initialize();
    }

    public StrassenView() {
    }

    public Strassenstueck getStrasse() {
        return strasse;
    }

    public void setStrasse(Strassenstueck s) {
        strasse = s;
    }

    public void initDrehenVorlagen(){
        bigDrehen = new Button();
        bigDrehen.setId("big-drehen-button");
        getChildren().add(bigDrehen);
        setAlignment(bigDrehen, Pos.CENTER);

        bigDrehen.addEventHandler(ActionEvent.ACTION, e -> {
            strasse.drehen();
        });
    }

    public void initTools() {

        rootView.getChildren().add(toolBox);

        drehen.addEventHandler(ActionEvent.ACTION, e -> {
            strasse.drehen();
            toolBox.setVisible(false);
        });

        bewegen.addEventHandler(ActionEvent.ACTION, e -> {
            initDragable();
            toolBox.setVisible(false);
        });

        entfernen.addEventHandler(ActionEvent.ACTION, e -> {
            try {
                controller.getStrassennetz().strassenstueckEntfernen(strasse);
                toolBox.setVisible(false);
            } catch (EntfernenException entf) {
                FehlerView fehler = new FehlerView(entf.getMessage(), controller.getAdmitButton());
                controller.initFehlerView(fehler);
				controller.getAdmitButton().addEventHandler(ActionEvent.ACTION, a -> {
                    rootView.getChildren().remove(fehler);
                    strasse.setVerkehrsteilnehmer(null);
                });
            } catch(FunktionalitaetException f){
                FehlerView fehler = new FehlerView(f.getMessage(), controller.getAdmitButton());
                controller.initFehlerView(fehler);
				controller.getAdmitButton().addEventHandler(ActionEvent.ACTION, a -> {
                    rootView.getChildren().remove(fehler);
                    strasse.clear();
                    controller.getStrassennetz().getStrassenstuecke().remove(strasse);
                    controller.getStrassennetz().getStrassenstueckeListe().remove(strasse);
                    controller.getStrassennetz().setFahrbarProperty(false);
                });
            }
        });
    }

    private void initialize() {
        if(getChildren().contains(bigDrehen)) getChildren().remove(bigDrehen);
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clicked=!clicked;
                toolBox.setVisible(clicked);
            }
        });

        strasse.changes.addPropertyChangeListener("ausrichtung", e -> {
            image.setRotate(strasse.getAusrichtung());
        });
    }

    public void initDragable(){

        setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                setMouseTransparent(true);
                event.setDragDetect(true);
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                setMouseTransparent(false);
                controller.clearFelder();
            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                event.setDragDetect(false);
            }
        });
        StrassenView sview = this;
        setOnDragDetected(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                startFullDrag();
                controller.initFelder(sview);
                controller.replace(sview);
            }
        });
    }

    public void removeDragable(){
        setOnMousePressed(null);
        setOnMouseReleased(null);
        setOnMouseDragged(null);
        setOnDragDetected(null);
    }
}