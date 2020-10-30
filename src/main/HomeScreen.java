package main;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.process.AugComProcess;
import main.process.GazePlayProcess;
import main.process.YoutubeProcess;

import java.awt.*;
import java.io.File;

public class HomeScreen extends BorderPane {

    private Stage primaryStage;
    public SecondStage secondStage;

    HomeScreen(Stage primaryStage) {
        super();
        this.primaryStage = primaryStage;

        File f = new File("src/ressources/images/blured.jpg");
        ImageView backgroundBlured = new ImageView(new Image("file:" + f.getAbsolutePath()));

        backgroundBlured.setOpacity(0.9);

        backgroundBlured.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundBlured.fitHeightProperty().bind(primaryStage.heightProperty());

        this.getChildren().add(backgroundBlured);

        createSecondStage();
        HBox menuBar = createMenuBar();

        this.setCenter(menuBar);

        startMouseListener();

    }

    private HBox createMenuBar() {
        YoutubeProcess youtubeProcess = new YoutubeProcess();
        AugComProcess augComProcess = new AugComProcess();
        GazePlayProcess gazePlayProcess = new GazePlayProcess();

        HBox menuBar = new HBox(
                youtubeProcess.createButton(this, secondStage),
                augComProcess.createButton(this, secondStage),
                gazePlayProcess.createButton(this, secondStage)
        );

        menuBar.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(menuBar, Pos.CENTER);

        menuBar.spacingProperty().bind(this.widthProperty().divide(8));
        return menuBar;
    }

    private void createSecondStage() {
        secondStage = new SecondStage(primaryStage);
    }

    private void startMouseListener() {
        Thread t = new Thread(() -> {
            while (true) {
                checkMouse();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    synchronized private void checkMouse() {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();
        System.out.println(" X is " + x + " and Y is " + y);
        if (x > 500 && x < Screen.getPrimary().getBounds().getWidth() - 500 && y <= 10) {
            Platform.runLater(() -> {
                primaryStage.hide();
                StageUtils.displayUnclosable(secondStage);
            });
        }
    }

}
