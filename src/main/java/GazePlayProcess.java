import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class GazePlayProcess implements AppProcess {
    ProcessBuilder pb;

    @Override
    public Process start() {
        try {
            return pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void init() {
        pb = createGazePlayBuilder();
    }

    private ProcessBuilder createGazePlayBuilder() {
        String javaBin =  "C:\\Program Files (x86)\\GazePlay\\lib\\jre\\bin\\java.exe";
        String classpath = "C:\\Program Files (x86)\\GazePlay\\lib\\*";

        LinkedList<String> commands = new LinkedList<>(Arrays.asList(javaBin, "-cp", classpath, "net.gazeplay.GazePlayLauncher"));

        System.out.println(javaBin + " " + "-cp" + " " + classpath + " " + "net.gazeplay.GazePlayLauncher");

        return new ProcessBuilder(commands);
    }

    @Override
    public Button createButton(BorderPane borderPane, SecondStage stage) {
        Button button = initButton("src/ressources/images/gazeplayicon.png", borderPane);
        this.init();
        button.setOnMouseClicked((e) -> {
            stage.proc = this.start();
        });
        return button;
    }

}
