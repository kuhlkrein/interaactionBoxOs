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
import main.process.AugComProcess;
import main.process.GazePlayProcess;
import main.process.YoutubeProcess;

import java.awt.geom.Point2D;
import java.io.File;

public class SecondStage extends Stage {
    public Process proc;

    Button[] buttons = new Button[6];
    final ImageView backgroundBlured;

    public SecondStage(Stage primaryStage) {
        super();
        this.initStyle(StageStyle.TRANSPARENT);

        Pane secondSageRoot = new Pane();
        secondSageRoot.setStyle("-fx-background-color: transparent;");

        Button exit = new Button();
        exit.setPrefWidth(50);
        exit.setPrefHeight(50);
        exit.setOnMouseClicked((event) -> {
            if (proc == null) {
                primaryStage.show();
                primaryStage.toFront();
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
        backgroundBlured = new ImageView(new Image("file:" + f.getAbsolutePath()));

        backgroundBlured.setOpacity(0.9);

        backgroundBlured.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundBlured.fitHeightProperty().bind(primaryStage.heightProperty());

        secondSageRoot.getChildren().addAll(backgroundBlured, exit);
        setbackgroundListener(backgroundBlured);
        createCircularButtons(exit, secondSageRoot, primaryStage);

        Scene scene = new Scene(secondSageRoot, Color.TRANSPARENT);
        this.setScene(scene);
    }

    public void setOpacityBackground(double d) {
        backgroundBlured.setOpacity(d);
    }

    public void setbackgroundListener(ImageView backgroundBlured) {
        backgroundBlured.setOnMouseMoved(event -> {

            for (Button button : buttons) {
                if (button != null) {
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
            }
        });
    }

    public void createCircularButtons(Button exit, Pane secondSageRoot, Stage primaryStage) {
        int numberOfButtons = 5;
        for (int i = 0; i < numberOfButtons; i++) {
            buttons[i] = new Button("" + i);
            setHandler(i, primaryStage);
            buttons[i].setPrefWidth(50);
            buttons[i].setPrefHeight(50);
            buttons[i].setShape(new Circle(50));
            buttons[i].layoutXProperty().bind(exit.layoutXProperty().add(200 * Math.cos(Math.toRadians(i * 360d / numberOfButtons))));
            buttons[i].layoutYProperty().bind(exit.layoutYProperty().add(200 * Math.sin(Math.toRadians(i * 360d / numberOfButtons))));
            secondSageRoot.getChildren().add(buttons[i]);

            int index = i;
            buttons[i].setOnMouseEntered(event -> {
                buttons[index].requestFocus();
            });
        }
    }

    public void setHandler(int i, Stage primaryStage) {
        EventHandler<MouseEvent> eventhandler = null;
        File f;
        ImageView logo;
        switch (i) {
            case 0:
                buttons[i].setText("exit");
                eventhandler = e -> {
                    if (proc != null) {
                        proc.destroy();
                    }
                    Platform.exit();
                    System.exit(0);
                };
                break;
            case 1:
                f = new File("src/ressources/images/angular.png");
                logo = new ImageView(new Image("file:" + f.getAbsolutePath()));
                logo.setFitWidth(25);
                logo.setFitHeight(25);
                buttons[i].setText("");
                buttons[i].setGraphic(logo);
                eventhandler = e -> {
                    if (proc != null) {
                        proc.destroy();
                    }
                    AugComProcess augComProcess = new AugComProcess();
                    augComProcess.init();
                    proc = augComProcess.start();
                };
                break;
            case 2:
                f = new File("src/ressources/images/yt.png");
                logo = new ImageView(new Image("file:" + f.getAbsolutePath()));
                logo.setFitWidth(25);
                logo.setFitHeight(25);
                buttons[i].setText("");
                buttons[i].setGraphic(logo);
                eventhandler = e -> {
                    if (proc != null) {
                        proc.destroy();
                    }
                    YoutubeProcess youtubeProcess = new YoutubeProcess();
                    youtubeProcess.init();
                    proc = youtubeProcess.start();
                };
                break;
            case 3:
                f = new File("src/ressources/images/gazeplayicon.png");
                logo = new ImageView(new Image("file:" + f.getAbsolutePath()));
                logo.setFitWidth(25);
                logo.setFitHeight(25);
                buttons[i].setText("");
                buttons[i].setGraphic(logo);
                eventhandler = e -> {
                    if (proc != null) {
                        proc.destroy();
                    }
                    GazePlayProcess gazePlayProcess = new GazePlayProcess();
                    gazePlayProcess.init();
                    proc = gazePlayProcess.start();
                };
                break;
            case 4:
                buttons[i].setText("menu");
                eventhandler = (event) -> {
                    if (proc != null) {
                        proc.destroy();
                    }
                    this.hide();
                    primaryStage.show();
                    primaryStage.toFront();
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
