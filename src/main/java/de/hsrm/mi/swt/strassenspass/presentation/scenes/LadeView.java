package de.hsrm.mi.swt.strassenspass.presentation.scenes;


import de.hsrm.mi.swt.strassenspass.business.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class LadeView extends AnchorPane {
	
	Label logo;
    Label s, p, a, ss;
    HBox ueberschrift;
    TableView<Strassennetz> tabelle;
    TableColumn<Strassennetz, String> name;
    Button zurueckButton;
    Button ladeButton;
    
    public LadeView(){
    	
		logo = new Label("STRA\u00DFEN");
		s = new Label("S");
		p = new Label("P");
		a = new Label("A");
		ss = new Label("\u00DF");
		ueberschrift = new HBox();
		ladeButton = new Button("Stra\u00DFennetz laden");
		ladeButton.setLayoutX(820);
		ladeButton.setLayoutY(510);
		ladeButton.setId("button6");
		zurueckButton = new Button("zur\u00fcck");
		zurueckButton.setId("button4");
		zurueckButton.setLayoutX(100);
		zurueckButton.setLayoutY(580);
		logo.setId("ueberschrift2");
		s.setId("S2");
		p.setId("P2");
		a.setId("A2");
		ss.setId("SS2");
		ueberschrift.getChildren().addAll(logo, s, p, a, ss);
		ueberschrift.setLayoutX(550);
		ueberschrift.setLayoutY(50);
		
		tabelle = new TableView<Strassennetz>();
		setId("root");
		name = new TableColumn<Strassennetz, String>("W\u00e4hle dein Stra\u00DFennetz mit Doppelklick aus");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		name.setPrefWidth(698.0);
		tabelle.getColumns().add(name);
		tabelle.setLayoutX(350);
		tabelle.setLayoutY(150);
		tabelle.setPrefHeight(450.0);
		tabelle.setPrefWidth(700.0);

		getChildren().addAll(tabelle, ueberschrift, zurueckButton);

	}

	
}