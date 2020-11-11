package de.hsrm.mi.swt.strassenspass.presentation;

import java.util.HashMap;

import de.hsrm.mi.swt.strassenspass.business.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.hsrm.mi.swt.strassenspass.presentation.scenes.*;

public class MainController extends Application {
    
    private Scene scene;
    private Pane currentScene;
    private HashMap<ViewInfo, Pane> scenes;
    private Strassennetz strassennetz;
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage){
        try{
            strassennetz = new Strassennetz();

            ViewController<MainController> controller;
            scenes = new HashMap<>();

            controller = new MenuViewController(this);
            scenes.put(ViewInfo.MENU_VIEW, controller.getRootView());

            controller = new VerkehrsnetzViewController(this);
            scenes.put(ViewInfo.VERKEHRSNETZ_VIEW, controller.getRootView());

            controller = new LadeViewController(this);
            scenes.put(ViewInfo.LADE_VIEW, controller.getRootView());

            controller = new AnleitungViewController(this);
            scenes.put(ViewInfo.ANLEITUNG_VIEW, controller.getRootView());

            controller = new SpeicherViewController(this);
            scenes.put(ViewInfo.SPEICHER_VIEW, controller.getRootView());

            currentScene = scenes.get(ViewInfo.MENU_VIEW);
            scene = new Scene(currentScene, 1400, 700);
            scene.getStylesheets().add(getClass().getResource("/stylesheets/application.css").toExternalForm());
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("Willkommen");
            this.primaryStage.setScene(scene);
            this.primaryStage.show();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public Strassennetz getStrassennetz(){
        return strassennetz;
    }

	
	public void switchView(ViewInfo viewName) {
        Pane nextScene;

        if(scenes.containsKey(viewName)){
            nextScene = scenes.get(viewName);
            scene.setRoot(nextScene);
            currentScene = nextScene;
        }
    }
    
    public static void main (String[] args) {
        launch(args);
    }
    
}
