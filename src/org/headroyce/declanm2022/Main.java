package org.headroyce.declanm2022;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MainWorkspace root = new MainWorkspace();
        root.getStylesheets().add(DrawingWorkspace.class.getResource("style.css").toExternalForm());

        Scene scene = new Scene(root, 1000, 600);

        stage.setScene(scene);
        stage.show();
        stage.requestFocus();
        stage.toFront();

    }

    public static void main(String[] args) {
	    launch(args);
    }
}
