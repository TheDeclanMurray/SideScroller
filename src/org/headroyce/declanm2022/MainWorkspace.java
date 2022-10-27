package org.headroyce.declanm2022;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;

public class MainWorkspace extends BorderPane {
    private DrawingWorkspace dw;

    public MainWorkspace(){
        dw = new DrawingWorkspace();

        this.setCenter(dw);
    }



}
