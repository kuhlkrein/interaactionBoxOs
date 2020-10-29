package main;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class SideTools extends Pane {

    public SideTools(Stage primaryStage){
        super();

        VBox bv = new VBox();
        Button exit = new Button("exit");
        Button exit2 = new Button("exit");
        Button exit3 = new Button("exit");
        exit.setOnMouseClicked((e)->{
            Platform.exit();
            System.exit(0);
        });
        bv.getChildren().addAll(exit,exit2,exit3);

        Rectangle backgroundBlured = new Rectangle(
                Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight()
        );

        backgroundBlured.setFill(Color.RED);
        backgroundBlured.setOpacity(0.5);

        backgroundBlured.widthProperty().bind(primaryStage.widthProperty());
        backgroundBlured.heightProperty().bind(primaryStage.heightProperty());

        this.getChildren().addAll(backgroundBlured,bv);

        this.prefWidthProperty().bind(primaryStage.widthProperty());
        this.prefHeightProperty().bind(primaryStage.heightProperty());

        primaryStage.setX(0);
        primaryStage.setY(0);


    }
}
