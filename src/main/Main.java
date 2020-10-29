package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(new HomeScreen(primaryStage));

        primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());
        primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight());

        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void makesUnclosable() {
//        primaryStage.setFullScreen(true);
//        primaryStage.setResizable(false);
//        primaryStage.setFullScreenExitHint("");
//        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    public static void main(String[] args) {
        launch(args);
    }

}