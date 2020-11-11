package de.hsrm.mi.swt.strassenspass.presentation.scenes;

import de.hsrm.mi.swt.strassenspass.business.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import de.hsrm.mi.swt.strassenspass.presentation.scenes.components.FehlerView;
import de.hsrm.mi.swt.strassenspass.presentation.scenes.components.StrassenView;

public class VerkehrsnetzView extends AnchorPane {
	
	//Layout
	AnchorPane plan;
	StrassenView feld;

	//Button
	Button startButton, speicherButton, zurueckButton;
	
	//Label
	Label ueb,s,p,a,ss;
	HBox ueberschrift;
	Label strassenstueck;
	Label auto;

	FehlerView fehlermeldung;
	Button akzeptierButton;
	 
	StackPane messageContainer;
	Label startText;
	
	public VerkehrsnetzView() {

		plan = new AnchorPane();
		plan.setPrefHeight(Strassennetz.getHoehe());
		plan.setPrefWidth(Strassennetz.getBreite());
		plan.minWidth(USE_PREF_SIZE);
		plan.minHeight(USE_PREF_SIZE);
		plan.setLayoutX(Strassennetz.getPosX());
		plan.setLayoutY(Strassennetz.getPosY());
		plan.setId("plan");

		this.getChildren().add(plan);

		startText = new Label();
		startText.setText("Um zu beginnen, musst \ndu ein Stra\u00DFenst\u00FCck in \nden Plan ziehen");
		startText.setId("kleinertext");

		messageContainer = new StackPane();
		messageContainer.getChildren().add(startText);
		StackPane.setAlignment(startText, Pos.CENTER);
		messageContainer.setPrefHeight(40);
		messageContainer.setPrefWidth(80);
		messageContainer.setId("kleineBlaueBox");	
		messageContainer.setVisible(true);

		messageContainer.setLayoutX(255);
		messageContainer.setLayoutY(250);
		
		startButton = new Button("START");
		speicherButton = new Button("Stra\u00DFennetz speichern");
		zurueckButton = new Button("zur\u00fcck");
		zurueckButton.setId("button4");
		zurueckButton.setLayoutX(20);
		zurueckButton.setLayoutY(20);

		strassenstueck = new Label("Stra\u00DFenst\u00FCcke");
		auto = new Label("Autos");
		
		startButton.setLayoutX(1200);
		startButton.setLayoutY(520);
		speicherButton.setLayoutX(950);
		speicherButton.setLayoutY(540);
		startButton.setId("button5");
		speicherButton.setId("button6");
		strassenstueck.setLayoutX(1050);
		strassenstueck.setLayoutY(100);
		strassenstueck.setId("text");
		auto.setLayoutX(1080);
		auto.setLayoutY(300);
		auto.setId("text");
		this.setId("root");
		
		ueb = new Label("STRA\u00DFEN");
		s = new Label("S");
		p = new Label("P");
		a = new Label("A");
		ss = new Label("\u00DF");
		ueberschrift = new HBox();
		ueberschrift.getChildren().addAll(ueb,s,p,a,ss);
		ueberschrift.setAlignment(Pos.CENTER);
		
		ueb.setId("ueberschrift2");
		s.setId("S2");
		p.setId("P2");
		a.setId("A2");
		ss.setId("SS2");

		ueberschrift.setLayoutX(550);
		ueberschrift.setLayoutY(20);

		fehlermeldung = null;
		akzeptierButton = new Button();
		akzeptierButton.getStyleClass().add("mid-icon-button");
		akzeptierButton.setId("akzeptieren-button");

		this.getChildren().addAll(ueberschrift, strassenstueck, auto, startButton, speicherButton, zurueckButton);
		this.getChildren().add(messageContainer);
	
	}
}
