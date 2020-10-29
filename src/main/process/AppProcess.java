package main.process;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;

public interface AppProcess {

    Process start();
    void init() ;
    Button createButton(BorderPane borderPane);

    default Button initButton(String ressourcePath, BorderPane borderPane) {
        Button newButton = new Button();
        File f = new File(ressourcePath);

        newButton.setPrefWidth(borderPane.getWidth() / 8);
        newButton.setPrefHeight(borderPane.getHeight() / 4);

        newButton.prefWidthProperty().bind(borderPane.widthProperty().divide(8));
        newButton.prefHeightProperty().bind(borderPane.heightProperty().divide(4));

        ImageView logo = new ImageView(new Image("file:" + f.getAbsolutePath()));

        logo.setFitWidth(newButton.getWidth() * 0.7);
        logo.setFitHeight(newButton.getHeight() * 0.7);

        logo.fitWidthProperty().bind(newButton.widthProperty().multiply(0.7));
        logo.fitHeightProperty().bind(newButton.heightProperty().multiply(0.7));
        logo.setPreserveRatio(true);

        newButton.setGraphic(logo);

        newButton.setStyle("-fx-background-radius: 5%; ");

        Reflection reflection = new Reflection();
        reflection.setBottomOpacity(0);
        reflection.setTopOffset(-30);
        reflection.setTopOpacity(0.8);
        reflection.setFraction(0.6);


        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(-3.0);

        reflection.setInput(dropShadow);

        newButton.setEffect(reflection);
        return newButton;
    }

}
