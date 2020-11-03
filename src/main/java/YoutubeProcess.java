import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class YoutubeProcess implements AppProcess {

    ProcessBuilder pb;

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
    public void init() {
        pb = new ProcessBuilder("C:/Program Files (x86)/Google/Chrome/Application/chrome.exe",
                // "--fullscreen","--app="+"https://www.youtube.com");
                //"--new-window", "--disable-pinch", "--overscroll-history-navigation=0",
                "--kiosk", "--window-position=0,0", "--disable-gpu", "--no-sandbox", "--fullscreen", "https://www.youtube.com");
        // "--start-fullscreen", "--new-window","--window-position=0,0", "--disable-gpu", "--no-sandbox",   "https://www.youtube.com");
    }

    @Override
    public Button createButton(BorderPane borderPane, SecondStage stage) {
        Button button = initButton("src/ressources/images/yt.png", borderPane);
        this.init();
        button.setOnMouseClicked((e) -> {
            stage.proc = this.start();
        });
        button.setOnMouseEntered(e -> {
            button.requestFocus();
        });
        return button;
    }

}
