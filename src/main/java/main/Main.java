package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.gaze.devicemanager.TobiiGazeDeviceManager;

import java.util.List;

public class Main extends Application {

    TobiiGazeDeviceManager tgdm;

    @Override
    public void start(Stage primaryStage) {
        Configuration configuration = new Configuration();
        this.tgdm = new TobiiGazeDeviceManager();
        tgdm.init(configuration);
    }

    public static void main(String[] args) {
        launch(args);
    }

}