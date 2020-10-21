package sample;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class HomeScreen extends BorderPane {

    private boolean open = false;
    private boolean augcomopen = false;
    private Process proc;
    private Stage primaryStage;
    private Stage secondStage;

    HomeScreen(Stage primaryStage) {
        super();
        this.primaryStage = primaryStage;

        HBox menuBar = new HBox(button1(), button2(), button3());
        menuBar.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(menuBar, Pos.CENTER);

        menuBar.spacingProperty().bind(this.widthProperty().divide(8));

        this.setCenter(menuBar);
        this.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));

        createSecondStage();
        startMouseListener();

    }

    private void createSecondStage(){
        secondStage = new Stage();
        secondStage.initStyle(StageStyle.TRANSPARENT);

        Pane secondSageRoot = new Pane();
        secondSageRoot.setStyle("-fx-background-color: transparent;");

        Button exit = new Button();
        exit.setPrefWidth(50);
        exit.setPrefHeight(50);
        exit.setOnMouseClicked((event) -> {
            proc.destroy();
            secondStage.hide();
            primaryStage.show();
            primaryStage.toFront();
            augcomopen = false;
        });

        exit.setOnMouseExited((e) -> {
            secondStage.hide();
            open = false;
            augcomopen = true;
        });


        secondSageRoot.getChildren().addAll(exit);

        Scene scene = new Scene(secondSageRoot, Color.TRANSPARENT);
        secondStage.setScene(scene);

        secondStage.toFront();
        secondStage.setFullScreen(true);
        secondStage.setAlwaysOnTop(true);
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
        if (x < 50 && y < 50 && !open && augcomopen) {
            System.out.println("THE COORDINATES ARE " + x + " ; " + y);
            Platform.runLater(() -> {
                primaryStage.hide();
                secondStage.show();
                secondStage.toFront();
                open = true;
            });
        }
    }

    private Button button1() {
        Button button = initButton("src/ressources/gazeplayicon.png");
        button.setOnMouseClicked((e) -> {
            try {
                ProcessBuilder pb = createGazePlayBuilder();
                pb.start();
            } catch (IOException ignored) {
            }
        });
        return button;
    }

    private Button button2() {
        Button button = initButton("src/ressources/angular.png");
        button.setOnMouseClicked((e) -> {
            startAugCom();
        });
        return button;
    }

    private Button button3() {
        Button button = initButton("src/ressources/yt.png");
        button.setOnMouseClicked((e) -> {
            startYoutube();
        });
        return button;
    }

    private void startAugCom() {
        try {
            ProcessBuilder pb = new ProcessBuilder("google-chrome",
                    // "--fullscreen","--app="+"http://www.augcom.net");
                    // "--new-window", "--disable-pinch", "--overscroll-history-navigation=0",
                    "--kiosk",
                    "--window-position=0,0", "--disable-gpu", "--no-sandbox", "--fullscreen", "http://localhost:4200/");
            //"--start-fullscreen" ,"--new-window","--window-position=0,0",  "--disable-gpu", "--no-sandbox", "http://www.augcom.net");
            proc = pb.inheritIO().start();
            augcomopen = true;

        } catch (IOException ignored) {
        }
    }

    private void startYoutube() {
        try {
            ProcessBuilder pb = new ProcessBuilder("google-chrome",
                    // "--fullscreen","--app="+"https://www.youtube.com");
                    //"--new-window", "--disable-pinch", "--overscroll-history-navigation=0",
                    "--kiosk", "--window-position=0,0", "--disable-gpu", "--no-sandbox", "--fullscreen", "https://www.youtube.com");
            // "--start-fullscreen", "--new-window","--window-position=0,0", "--disable-gpu", "--no-sandbox",   "https://www.youtube.com");
            pb.inheritIO().start();
        } catch (IOException ignored) {
        }
    }

    private Button initButton(String ressourcePath) {
        Button newButton = new Button();
        File f = new File(ressourcePath);

        newButton.setPrefWidth(this.getWidth() / 8);
        newButton.setPrefHeight(this.getHeight() / 4);

        newButton.prefWidthProperty().bind(this.widthProperty().divide(8));
        newButton.prefHeightProperty().bind(this.heightProperty().divide(4));

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

    private ProcessBuilder createGazePlayBuilder() {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = ClassPath.get;

        System.out.println(classpath);
        LinkedList<String> commands = new LinkedList<>(Arrays.asList(javaBin, "-cp", classpath, "net.gazeplay.GazePlayLauncher"));


        return new ProcessBuilder(commands);
    }

}
