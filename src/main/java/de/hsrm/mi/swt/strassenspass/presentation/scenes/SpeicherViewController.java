package de.hsrm.mi.swt.strassenspass.presentation.scenes;

import de.hsrm.mi.swt.strassenspass.business.*;
import de.hsrm.mi.swt.strassenspass.business.exceptions.SpeicherException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import de.hsrm.mi.swt.strassenspass.presentation.MainController;
import de.hsrm.mi.swt.strassenspass.presentation.scenes.components.FehlerView;

public class SpeicherViewController extends ViewController<MainController> {

    private SpeicherView view;
    private Strassennetz strassennetz;

    private TextField textfield;
    private Button speicherButton;

    private FehlerView fehlermeldung;
    private Button akzeptierButton;
    
    public SpeicherViewController(MainController application) {
        super(application);
        strassennetz = application.getStrassennetz();
        
        view = new SpeicherView();
        setRootView(view);
        
        textfield = view.textfield;
        speicherButton = view.speicherButton;

        akzeptierButton = new Button();
        akzeptierButton.getStyleClass().add("mid-icon-button");
        akzeptierButton.setId("akzeptieren-button");
        
        initialize();
    }

    @Override
    public void initialize() {
    	
    	speicherButton.addEventHandler(ActionEvent.ACTION, e -> {
            if(strassennetz.getName() == null){
                String name = "MeinStrassennetz";
                if(!textfield.getText().equals("")) name = textfield.getText();
                strassennetz.setName(name);
            } else textfield.setText(strassennetz.getName());
                   
            try {
				strassennetz.speichern();
				application.switchView(ViewInfo.MENU_VIEW);
			} catch (SpeicherException s) {
                initFehlerView(new FehlerView(s.getMessage(), akzeptierButton));
                akzeptierButton.addEventHandler(ActionEvent.ACTION, b -> {
                    view.getChildren().remove(fehlermeldung);
                });
			}
		});
        
    }

    public void initFehlerView(FehlerView fview){
		fehlermeldung = fview;
		view.getChildren().add(fehlermeldung);
	}


	public SpeicherView getView() {
		return view;
    }

}
