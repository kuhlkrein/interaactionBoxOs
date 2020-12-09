package main.process;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import main.ProgressButton;
import main.SecondStage;
import main.gaze.devicemanager.AbstractGazeDeviceManager;

import java.io.File;
import java.io.IOException;

public class AugComProcess implements AppProcess {

    ProcessBuilder pb;

    @Override
    public void init() {
        pb = new ProcessBuilder("C:/Program Files (x86)/Google/Chrome/Application/chrome.exe",
                // "--fullscreen","--app="+"http://www.augcom.net");
                // "--new-window", "--disable-pinch", "--overscroll-history-navigation=0",
                "--kiosk",
                "--window-position=0,0", "--disable-gpu", "--no-sandbox", "--fullscreen", "http://localhost:4200/");
        //"--start-fullscreen" ,"--new-window","--window-position=0,0",  "--disable-gpu", "--no-sandbox", "http://www.augcom.net");
    }

    @Override
    public Process start() {
        try {
            return pb.inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProgressButton createButton(BorderPane borderPane, SecondStage stage, AbstractGazeDeviceManager tgdm) {
        ProgressButton pb = new ProgressButton();
        File f = new File(ClassLoader.getSystemResource("images/angular.png").getPath());
        ImageView logo = new ImageView(new Image("file:" + f.getAbsolutePath()));
        pb.getButton().setRadius(100);
        logo.setFitWidth(pb.getButton().getRadius() * 0.7);
        logo.setFitHeight(pb.getButton().getRadius() * 0.7);
        logo.fitWidthProperty().bind(pb.getButton().radiusProperty().multiply(0.7));
        logo.fitHeightProperty().bind(pb.getButton().radiusProperty().multiply(0.7));
        logo.setPreserveRatio(true);
        pb.setImage(logo);
        pb.assignIndicator((e) -> {
            stage.proc = this.start();
        }, 500);
        // Button button = initButton("src/resources/images/angular.png", borderPane);
        this.init();
        pb.active();
        return pb;
    }

}
