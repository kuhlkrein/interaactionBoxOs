package main;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.process.AugComProcess;
import main.process.GazePlayProcess;
import main.process.YoutubeProcess;

import java.awt.*;

public class HomeScreen extends BorderPane {

    private Stage primaryStage;
    public SecondStage secondStage;

    HomeScreen(Stage primaryStage) {
        super();
        this.primaryStage = primaryStage;

        HBox menuBar = createMenuBar();

        this.setCenter(menuBar);
        this.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));

        createSecondStage();
        startMouseListener();

    }

    private HBox createMenuBar(){
        YoutubeProcess youtubeProcess = new YoutubeProcess();
        AugComProcess augComProcess = new AugComProcess();
        GazePlayProcess gazePlayProcess= new GazePlayProcess();

        HBox menuBar = new HBox(
                youtubeProcess.createButton(this),
                augComProcess.createButton(this),
                gazePlayProcess.createButton(this)
        );

        menuBar.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(menuBar, Pos.CENTER);

        menuBar.spacingProperty().bind(this.widthProperty().divide(8));
        return menuBar;
    }

    private void createSecondStage(){
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

    synchronized private void checkMouse(){
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();
        System.out.println(" X is " + x +" and Y is " + y);
        if (x > 500 && x < Screen.getPrimary().getBounds().getWidth()-500 && y <= 10) {
            System.out.println("ENTERED");
            Platform.runLater(() -> {
                primaryStage.hide();
                secondStage.setFullScreen(true);
                secondStage.show();
                secondStage.toFront();
            });
        }
    }

}
