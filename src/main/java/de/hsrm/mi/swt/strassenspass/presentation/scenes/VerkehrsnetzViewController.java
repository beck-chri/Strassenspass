package de.hsrm.mi.swt.strassenspass.presentation.scenes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hsrm.mi.swt.strassenspass.business.*;
import de.hsrm.mi.swt.strassenspass.business.exceptions.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import de.hsrm.mi.swt.strassenspass.presentation.*;
import de.hsrm.mi.swt.strassenspass.presentation.scenes.components.*;

public class VerkehrsnetzViewController extends ViewController<MainController> {

	private VerkehrsnetzView view;
	private VerkehrsnetzViewController controller;
	private Strassennetz strassennetz;

	private AnchorPane plan;

	private Button startButton;
	private Button speicherButton;
	private Button zurueckButton;
	private FehlerView fehlermeldung;

	private List<AutoView> autovorlagen;
	private List<StrassenView> strassenvorlagen;

	private List<AutoView> platzierteAutos;
	private List<StrassenView> platzierteStrassen;

	private Button akzeptierButton;

	public VerkehrsnetzViewController(final MainController application) {
		super(application);
		strassennetz = application.getStrassennetz();

		view = new VerkehrsnetzView();
		setRootView(view);
		plan = view.plan;

		startButton = view.startButton;
		speicherButton = view.speicherButton;
		zurueckButton = view.zurueckButton;

		fehlermeldung = view.fehlermeldung;
		akzeptierButton = view.akzeptierButton;

		startButton.setDisable(true);
		speicherButton.setDisable(true);

		autovorlagen = new ArrayList<>();
		strassenvorlagen = new ArrayList<>();

		platzierteAutos = new ArrayList<>();
		platzierteStrassen = new ArrayList<>();

		controller = this;
		initVorlagen();

		initialize();
	}

	@Override
	public void initialize() {

		initDrag();
		
		strassennetz.getFahrbarProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) startButton.setDisable(false);
				else startButton.setDisable(true);
			}
		});

		strassennetz.getEmptyProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					speicherButton.setDisable(false);
					view.messageContainer.setVisible(false);
				} else{
					speicherButton.setDisable(true);
					view.messageContainer.setVisible(true);
				}
			}
		});

		startButton.addEventHandler(ActionEvent.ACTION, e -> {
			if (!strassennetz.faehrt()) {
				Platform.runLater(() -> {
					try{
						strassennetz.starten();
					} catch(FahrException f){
						fehlermeldung = new FehlerView(f.getMessage(), akzeptierButton);
						view.getChildren().add(fehlermeldung);
						akzeptierButton.addEventHandler(ActionEvent.ACTION, a -> {
							view.getChildren().remove(fehlermeldung);
						});
					}
				});
				startButton.textProperty().set("STOP");
				startButton.setId("button7");

			} else {
				strassennetz.stoppen();
				startButton.textProperty().set("START");
				startButton.setId("button5");
			}
		});

		zurueckButton.addEventHandler(ActionEvent.ACTION, e -> {
			application.switchView(ViewInfo.MENU_VIEW);
		});

		speicherButton.addEventHandler(ActionEvent.ACTION, e -> {
			application.switchView(ViewInfo.SPEICHER_VIEW);
		});

		strassennetz.getEmptyProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				initDrag();
			}
		});

		strassennetz.getStrassenstuecke().addListener(new ListChangeListener<Strassenstueck>() {
			@Override
			public void onChanged(Change<? extends Strassenstueck> c) {
				while (c.next()) {
					if (c.wasRemoved()) {
						for (Strassenstueck s : c.getRemoved()) {
							StrassenView sview = null;
							Iterator<StrassenView> it = platzierteStrassen.iterator();
							while(it.hasNext()){
								sview = it.next();
								if(sview.getStrasse().equals(s)){
									plan.getChildren().remove(sview);
									it.remove();								
								}
							}
						}
					}
					if (c.wasAdded()) {
						for (Strassenstueck s : c.getAddedSubList()) {
							StrassenView sview = new StrassenView(s, view, controller);
							platzierteStrassen.add(sview);
							plan.getChildren().add(sview);
							sview.initTools();
							platziereAutos();
							
							if(s.getVerkehrsteilnehmer() != null){
								AutoView aview = new AutoView(s.getVerkehrsteilnehmer());
								plan.getChildren().add(aview);
								platzierteAutos.add(aview);
							s.changes.addPropertyChangeListener(new PropertyChangeListener(){
								@Override
								public void propertyChange(PropertyChangeEvent e) {
									if(e.getPropertyName().equals("verkehrsteilnehmer")){
										if(e.getNewValue() == null){
											AutoView aview = null;
											Iterator<AutoView> it = platzierteAutos.iterator();
											while(it.hasNext()){
												aview = it.next();
												if(aview.getAuto().equals(e.getOldValue())){
													plan.getChildren().remove(aview);
													it.remove();
												}
											} 
										}
									}
								}
							});
						}
					}
				}
			}
		}
	});

	}

	private void initVorlagen() {
		for (Auto a : Strassennetz.getAutofarben()) {
			AutoView auto = new AutoView(a);
			autovorlagen.add(auto);
			view.getChildren().add(auto);
		}
		for (Strassenstueck s : Strassennetz.getStrassenformen()){
			StrassenView sview = new StrassenView(s, view, controller);
			strassenvorlagen.add(sview);
			view.getChildren().add(sview);
		} 	
	}

	private void initDrag(){

		for(StrassenView s : strassenvorlagen){
			
			s.initDrehenVorlagen();
			s.setOnMousePressed(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					event.setDragDetect(true);
					s.setMouseTransparent(true);
				}
			});

			s.setOnMouseReleased(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					s.setMouseTransparent(false);
				}
			});

			s.setOnMouseDragged(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					event.setDragDetect(false);
				}

			});
			
			s.setOnDragDetected(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					s.startFullDrag();
					if(!strassennetz.isEmpty()) initFelder(s);
					initDrop(s);
				}
			});
		}

		for(AutoView a : autovorlagen){
			
			a.setOnMousePressed(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					event.setDragDetect(true);
					a.setMouseTransparent(true);
				}
			});

			a.setOnMouseReleased(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					a.setMouseTransparent(false);
				}
			});

			a.setOnMouseDragged(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					event.setDragDetect(false);
				}

			});

			a.setOnDragDetected(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					a.startFullDrag();
					initDrop(a);
				}
			});
		}
	}

	public void replace(StrassenView s){
		plan.setOnMouseDragReleased(new EventHandler<MouseDragEvent>(){
			@Override
			public void handle(MouseDragEvent event) {
				try{
					clearFelder();
					strassennetz.platziere(s.getStrasse(), event.getX(), event.getY());
					s.removeDragable();
					plan.getChildren().remove(s);
					strassennetz.getStrassenstuecke().remove(s.getStrasse());
					strassennetz.getStrassenstueckeListe().remove(s.getStrasse());
					s.getStrasse().clear();
				} catch(PlatzierungsException e){
					fehlermeldung = new FehlerView(e.getMessage(), akzeptierButton);
					view.getChildren().add(fehlermeldung);
					akzeptierButton.addEventHandler(ActionEvent.ACTION, a -> {
						view.getChildren().remove(fehlermeldung);
					});
				}
			}
		});

	}

	public void initDrop(StrassenView s){
		plan.setOnMouseDragReleased(new EventHandler<MouseDragEvent>(){
			@Override
			public void handle(MouseDragEvent event) {
				try{
					clearFelder();
					strassennetz.platziere(s.getStrasse(), event.getX(), event.getY());
				} catch(PlatzierungsException e){
					fehlermeldung = new FehlerView(e.getMessage(), akzeptierButton);
						view.getChildren().add(fehlermeldung);
						akzeptierButton.addEventHandler(ActionEvent.ACTION, a -> {
							view.getChildren().remove(fehlermeldung);
					});
				}
			}
		});
	}

	public void initDrop(AutoView a){
		plan.setOnMouseDragReleased(new EventHandler<MouseDragEvent>(){
			@Override
			public void handle(MouseDragEvent event) {
				try{
					Strassenstueck target = strassennetz.getStrassenstueck(event.getX(), event.getY());
					System.out.println("click");
					Auto auto = new Auto(a.getAuto());
					target.autoHinzufuegen(auto);
					AutoView aview = new AutoView(auto);
					plan.getChildren().add(aview);
					platzierteAutos.add(aview);
				} catch(PlatzierungsException e){
					fehlermeldung = new FehlerView(e.getMessage(), akzeptierButton);
					view.getChildren().add(fehlermeldung);
					akzeptierButton.addEventHandler(ActionEvent.ACTION, a -> {
						view.getChildren().remove(fehlermeldung);
					});
				}
			}
		});
	}

	public void initFelder(StrassenView sview){
		for(Strassenstueck feld : strassennetz.getOptionen(sview.getStrasse())){
			plan.getChildren().add(new FeldView(feld));
		}
	}

	public void clearFelder(){
		List<FeldView> clean = new ArrayList<>();
		for(Node n : plan.getChildren()){
			if(n instanceof FeldView) clean.add((FeldView)n);
		}
		for(FeldView f : clean){
			plan.getChildren().remove(f);
		}
	}

	private void platziereAutos(){
		for(AutoView aview : platzierteAutos){
			plan.getChildren().remove(aview);
			plan.getChildren().add(aview);
		}
	}

	public void initFehlerView(FehlerView fview){
		fehlermeldung = fview;
		view.getChildren().add(fehlermeldung);
	}
	

	public Button getAdmitButton(){
		return akzeptierButton;
	}

	public VerkehrsnetzView getView() {
		return view;
	}

	public Strassennetz getStrassennetz(){
		return strassennetz;
	}

	public AnchorPane getPlan(){
		return plan;
	}

}
