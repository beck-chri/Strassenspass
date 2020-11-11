package de.hsrm.mi.swt.strassenspass.presentation.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/*
 * View fuer die Menu-Seite
 */
public class MenuView extends BorderPane {

	Label caption;
	VBox buttons;
	Button spielButton, ladeButton;
	Button anleitungButton;
	Label s, p, a, ss;
	HBox ueberschrift;

	public MenuView() {
		
		this.setId("root");
		caption = new Label("STRA\u00DFEN");
		s = new Label("S");
		p = new Label("P");
		a = new Label("A");
		ss = new Label("\u00DF");
		ueberschrift = new HBox();
		ueberschrift.getChildren().addAll(caption,s,p,a,ss);

		buttons = new VBox();
		spielButton = new Button("Stra\u00DFennetz erstellen");
		ladeButton = new Button("Plan laden");
		anleitungButton = new Button("Anleitung");
		
		ladeButton.setId("button1");
		spielButton.setId("button2");
		anleitungButton.setId("button3");
		buttons.setId("menu-view");
		buttons.setAlignment(Pos.BASELINE_CENTER);
		buttons.getChildren().addAll(spielButton, ladeButton,anleitungButton);
		setCenter(buttons);
		setTop(ueberschrift);

		caption.setId("ueberschrift");
		s.setId("S");
		p.setId("P");
		a.setId("A");
		ss.setId("SS");

		caption.setMaxWidth(Double.MAX_VALUE);
		ueberschrift.setAlignment(Pos.CENTER);

	}

}