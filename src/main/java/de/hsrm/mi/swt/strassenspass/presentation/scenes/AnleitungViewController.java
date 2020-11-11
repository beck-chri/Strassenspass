package de.hsrm.mi.swt.strassenspass.presentation.scenes;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import de.hsrm.mi.swt.strassenspass.presentation.*;

public class AnleitungViewController extends ViewController<MainController>{

	AnleitungView view;

	Button zurueckButton;
	
	public AnleitungViewController(MainController application) {
		super(application);

		view = new AnleitungView();
		setRootView(view);

		zurueckButton = view.zurueckButton;
	
		initialize();
		
	}
	
	public void initialize() {
		zurueckButton.addEventHandler(ActionEvent.ACTION, e -> {
			application.switchView(ViewInfo.MENU_VIEW);
		});
	
	}
}