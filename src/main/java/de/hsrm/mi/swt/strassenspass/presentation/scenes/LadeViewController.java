package de.hsrm.mi.swt.strassenspass.presentation.scenes;

import java.util.ArrayList;

import de.hsrm.mi.swt.strassenspass.business.Strassennetz;
import de.hsrm.mi.swt.strassenspass.business.exceptions.SpeicherException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import de.hsrm.mi.swt.strassenspass.presentation.MainController;
import de.hsrm.mi.swt.strassenspass.presentation.scenes.components.FehlerView;

public class LadeViewController extends ViewController<MainController> {
	
	
	private LadeView view;
	private Strassennetz strassennetz;
	
	private Button zurueckButton;
    private TableColumn <Strassennetz, String> zeile;
	private TableView <Strassennetz> tabelle;
	private ArrayList<Strassennetz> namen;
	
	private FehlerView fehlermeldung;
	private Button akzeptierButton;

    public LadeViewController(MainController application){
		super(application);
		strassennetz = application.getStrassennetz();

        view = new LadeView();
        setRootView(view);
        
    	zurueckButton = view.zurueckButton;
    	tabelle = view.tabelle;
    	zeile = view.name;
		fuelleTabelle();
		
		akzeptierButton = new Button();
		akzeptierButton.getStyleClass().add("mid-icon-button");
		akzeptierButton.setId("akzeptierenButton");
    	
		initialize();
	}
	
	public void initialize() {
		zurueckButton.addEventHandler(ActionEvent.ACTION, e -> {
			application.switchView(ViewInfo.MENU_VIEW);
		});
	
		tabelle.setRowFactory( tv -> {
		    TableRow<Strassennetz> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
					try{
						strassennetz.laden(row.getItem().getName());
						application.switchView(ViewInfo.VERKEHRSNETZ_VIEW);
					}
					catch(SpeicherException e){
						initFehlerView(new FehlerView(e.getMessage(), akzeptierButton));
						akzeptierButton.addEventHandler(ActionEvent.ACTION, s -> {
							view.getChildren().remove(fehlermeldung);
						});
					}
		        }
		    });
		   return row ;
		});
}

	public void fuelleTabelle() {
		namen = strassennetz.getSavings();
		ObservableList<Strassennetz> inhalt = FXCollections.observableArrayList();
		for(int i = 0; i<namen.size(); i++) {
			inhalt.add(namen.get(i));
		}
		zeile.setCellValueFactory(new PropertyValueFactory<Strassennetz, String>("name"));
		tabelle.setItems(inhalt);
	}

	public void initFehlerView(FehlerView fview){
		fehlermeldung = fview;
		view.getChildren().add(fehlermeldung);
	}

    public LadeView getView(){
        return view;
    }
    
}