package de.hsrm.mi.swt.strassenspass.presentation.scenes;

import javafx.scene.layout.Pane;

public abstract class ViewController<T> {

    protected Pane rootView;
    protected T application;

    public ViewController(){}

    public ViewController(T application){
        this.application = application;
    }

    public Pane getRootView(){
        return rootView;
    }

    public abstract void initialize();

    public void setRootView(Pane pane){
        rootView = pane;
    }

    public void setApplication(T application){
        this.application = application;
    }
    
}