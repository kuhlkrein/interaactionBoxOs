package main.process;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import main.ClassPath;

import java.io.File;
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
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = ClassPath.get;

        System.out.println(classpath);
        LinkedList<String> commands = new LinkedList<>(Arrays.asList(javaBin, "-cp", classpath, "net.gazeplay.GazePlayLauncher"));


        return new ProcessBuilder(commands);
    }

    @Override
    public Button createButton(BorderPane borderPane) {
        Button button = initButton("src/ressources/images/gazeplayicon.png", borderPane);
        this.init();
        button.setOnMouseClicked((e) -> {
            this.start();
        });
        return button;
    }

}
