package main;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.geom.Point2D;
import java.io.File;

public class SecondStage extends Stage {
    public Process proc;

    Button[] buttons = new Button[6];

    public SecondStage(Stage primaryStage) {
        super();
        this.initStyle(StageStyle.TRANSPARENT);

        Pane secondSageRoot = new Pane();
        secondSageRoot.setStyle("-fx-background-color: transparent;");

        Button exit = new Button();
        exit.setPrefWidth(50);
        exit.setPrefHeight(50);
        exit.setOnMouseClicked((event) -> {
            primaryStage.show();
            primaryStage.toFront();
            if (proc != null) {
                proc.destroy();
            }
            this.hide();
        });

        exit.setShape(new Circle(50));

        exit.layoutXProperty().bind(primaryStage.widthProperty().divide(2).subtract(25));
        exit.layoutYProperty().bind(primaryStage.heightProperty().divide(2).subtract(25));

        File cross = new File("src/ressources/images/cross.png");
        ImageView logo = new ImageView(new Image("file:" + cross.getAbsolutePath()));
        logo.setFitWidth(20);
        logo.setFitHeight(20);
        logo.setPreserveRatio(true);
        exit.setGraphic(logo);

        File f = new File("src/ressources/images/blured.jpg");
        ImageView backgroundBlured = new ImageView(new Image("file:" + f.getAbsolutePath()));

        backgroundBlured.setOpacity(0.9);

        backgroundBlured.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundBlured.fitHeightProperty().bind(primaryStage.heightProperty());

        secondSageRoot.getChildren().addAll(backgroundBlured, exit);
        setbackgroundListener(backgroundBlured);
        createCircularButtons(exit, secondSageRoot);

        Scene scene = new Scene(secondSageRoot, Color.TRANSPARENT);
        this.setScene(scene);
    }

    public void setbackgroundListener(ImageView backgroundBlured) {
        backgroundBlured.setOnMouseMoved(event -> {

            for (Button button : buttons) {
                double buttonOrigin = Point2D.distance(
                        Screen.getPrimary().getBounds().getWidth() / 2,
                        Screen.getPrimary().getBounds().getHeight() / 2,
                        button.getLayoutX(),
                        button.getLayoutY());
                double mouseOrigin = Point2D.distance(
                        Screen.getPrimary().getBounds().getWidth() / 2,
                        Screen.getPrimary().getBounds().getHeight() / 2,
                        event.getX(),
                        event.getY());
                double mouseButton = Point2D.distance(
                        button.getLayoutX(),
                        button.getLayoutY(),
                        event.getX(),
                        event.getY());

                if (mouseButton < buttonOrigin) {
                    button.setPrefWidth(50 + 25 * (mouseOrigin / buttonOrigin));
                    button.setPrefHeight(50 + 25 * (mouseOrigin / buttonOrigin));
                } else {
                    button.setPrefWidth(50);
                    button.setPrefHeight(50);
                }

            }
        });
    }

    public void createCircularButtons(Button exit, Pane secondSageRoot) {
        for (int i = 0; i < 6; i++) {
            buttons[i] = new Button("" + i);
            setHandler(i);
            buttons[i].setPrefWidth(50);
            buttons[i].setPrefHeight(50);
            buttons[i].layoutXProperty().bind(exit.layoutXProperty().add(200 * Math.cos(Math.toRadians(i * 360d / 6d))));
            buttons[i].layoutYProperty().bind(exit.layoutYProperty().add(200 * Math.sin(Math.toRadians(i * 360d / 6d))));
            secondSageRoot.getChildren().add(buttons[i]);

            int index = i;
            buttons[i].setOnMouseEntered(event -> {
                buttons[index].requestFocus();
            });
        }
    }

    public void setHandler(int i) {
        EventHandler<MouseEvent> eventhandler = null;
        switch (i) {
            case 0:
                buttons[i].setText("exit");
                eventhandler = e -> {
                    Platform.exit();
                    System.exit(0);
                };
                break;
            default:
                break;
        }

        if (eventhandler != null) {
            buttons[i].setOnMouseClicked(eventhandler);
        }
    }

}
