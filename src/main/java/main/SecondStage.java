package main;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.gaze.devicemanager.AbstractGazeDeviceManager;
import main.gaze.devicemanager.GazeEvent;
import main.gaze.devicemanager.TobiiGazeDeviceManager;
import main.process.AugComProcess;
import main.process.GazePlayProcess;
import main.process.YoutubeProcess;

import java.io.File;
import java.util.LinkedList;

public class SecondStage extends Stage {
    public Process proc;
    public AbstractGazeDeviceManager tgdm;


    public SecondStage(Stage primaryStage, AbstractGazeDeviceManager tgdm) {
        super();
        this.initStyle(StageStyle.TRANSPARENT);
        this.tgdm = tgdm;
        MenuPane secondSageRoot = new MenuPane(primaryStage);
        secondSageRoot.setCloseMenuHandler((event) -> {
            if (proc == null) {
                primaryStage.show();
                primaryStage.toFront();
            }
            this.hide();
        });
        secondSageRoot.buttons=setButtons(primaryStage);
        secondSageRoot.createCircularButtons();

        Scene scene = new Scene(secondSageRoot, Color.TRANSPARENT);
        this.setScene(scene);
    }

    public LinkedList<ProgressButton> setButtons(Stage primaryStage) {
        EventHandler<Event> eventhandler = null;
        File f;
        ImageView logo;
        LinkedList<ProgressButton> buttons = new LinkedList<ProgressButton>();
        for (int i = 0; i < 5; i++) {
            buttons.add(new ProgressButton());
            switch (i) {
                case 0:
                    //buttons.get(i).setText("exit");
                    eventhandler = e -> {
                        if (proc != null) {
                            proc.destroy();
                            proc = null;
                        }
                        Platform.exit();
                        System.exit(0);
                    };
                    break;
                case 1:
                    f = new File("src/ressources/images/angular.png");
                    logo = new ImageView(new Image("file:" + f.getAbsolutePath()));
                    logo.setFitWidth(100);
                    logo.setFitHeight(100);
                   // buttons.get(i).setText("");
                    buttons.get(i).setImage(logo);
                    eventhandler = e -> {
                        if (proc != null) {
                            proc.destroy();
                            proc = null;
                        }
                        AugComProcess augComProcess = new AugComProcess();
                        augComProcess.init();
                        proc = augComProcess.start();
                    };
                    break;
                case 2:
                    f = new File("src/ressources/images/yt.png");
                    logo = new ImageView(new Image("file:" + f.getAbsolutePath()));
                    logo.setFitWidth(100);
                    logo.setFitHeight(100);
                    //buttons.get(i).setText("");
                    buttons.get(i).setImage(logo);
                    eventhandler = e -> {
                        if (proc != null) {
                            proc.destroy();
                            proc = null;
                        }
                        YoutubeProcess youtubeProcess = new YoutubeProcess();
                        youtubeProcess.init();
                        proc = youtubeProcess.start();
                    };
                    break;
                case 3:
                    f = new File("src/ressources/images/gazeplayicon.png");
                    logo = new ImageView(new Image("file:" + f.getAbsolutePath()));
                    logo.setFitWidth(100);
                    logo.setFitHeight(100);
                   // buttons.get(i).setText("");
                    buttons.get(i).setImage(logo);
                    eventhandler = e -> {
                        if (proc != null) {
                            proc.destroy();
                            proc = null;
                        }
                        GazePlayProcess gazePlayProcess = new GazePlayProcess();
                        gazePlayProcess.init();
                        proc = gazePlayProcess.start();
                    };
                    break;
                case 4:
                   // buttons.get(i).setText("menu");
                    eventhandler = (event) -> {
                        if (proc != null) {
                            proc.destroy();
                            proc = null;
                        }
                        primaryStage.show();
                        primaryStage.toFront();
                        this.hide();
                    };
                    break;
                default:
                    break;
            }
            if (eventhandler != null) {
                buttons.get(i).setOnMouseClicked(eventhandler);
                buttons.get(i).assignIndicator(eventhandler,500);
                buttons.get(i).active();
                tgdm.addEventFilter(buttons.get(i));
            }
        }
        return buttons;
    }

}
