import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AugComProcess implements AppProcess {

    ProcessBuilder pb;

    @Override
    public void init() {
        pb = new ProcessBuilder("C:/Program Files (x86)/Google/Chrome/Application/chrome.exe",
                // "--fullscreen","--app="+"http://www.augcom.net");
                // "--new-window", "--disable-pinch", "--overscroll-history-navigation=0",
                "--kiosk",
                "--window-position=0,0", "--disable-gpu", "--no-sandbox", "--fullscreen", "http://www.augcom.net/");
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
    public Button createButton(BorderPane borderPane, SecondStage stage) {
        Button button = initButton("src/ressources/images/angular.png", borderPane);
        this.init();
        button.setOnMouseClicked((e) -> {
            stage.proc = this.start();
        });
        return button;
    }

}
