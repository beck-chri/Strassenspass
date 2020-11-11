package de.hsrm.mi.swt.strassenspass.presentation.scenes;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import de.hsrm.mi.swt.strassenspass.presentation.MainController;

public class MenuViewController extends ViewController<MainController> {

    private MenuView view;
    private Button anleitungButton, spielButton, ladeButton;

    public MenuViewController(MainController application){
        super(application);

        view = new MenuView();
        setRootView(view);

        anleitungButton = view.anleitungButton;
        spielButton = view.spielButton;
        ladeButton = view.ladeButton;

        initialize();
    }

    @Override
    public void initialize() {
        anleitungButton.addEventHandler(ActionEvent.ACTION, e -> {
            application.switchView(ViewInfo.ANLEITUNG_VIEW);
        });
        
        spielButton.addEventHandler(ActionEvent.ACTION, e -> {
            application.switchView(ViewInfo.VERKEHRSNETZ_VIEW);
        });

        ladeButton.addEventHandler(ActionEvent.ACTION, e -> {
            application.switchView(ViewInfo.LADE_VIEW);
        });

    }

}

