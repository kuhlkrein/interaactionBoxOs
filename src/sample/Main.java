package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

       // primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(new HomeScreen(primaryStage));

        primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());
        primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight());

        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void makesUnclosable(){
//        primaryStage.setFullScreen(true);
//        primaryStage.setResizable(false);
//        primaryStage.setFullScreenExitHint("");
//        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Button createB2(Stage primaryStage){
        Button b2 = new Button("exit");
        b2.setOnMouseClicked((e)->{
            ProcessBuilder pb = new ProcessBuilder("google-chrome","--chrome","--disable-gpu","--fullscreen","--kiosk","http://www.augcom.net");
            try {
                pb.inheritIO().start();
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(()->{
                                    Stage secondaryStage = new Stage();
                                    secondaryStage.initModality(Modality.APPLICATION_MODAL);
                                    secondaryStage.initStyle(StageStyle.TRANSPARENT);
                                    secondaryStage.setScene(new Scene(new SideTools(primaryStage),Color.TRANSPARENT));
                                    secondaryStage.toFront();
                                    secondaryStage.setAlwaysOnTop(true);
                                    secondaryStage.show();
                                });
                            }
                        },
                        3000
                );
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return b2;
    }
}
