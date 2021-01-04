package main;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class OptionsPane extends BorderPane {

    final Configuration configuration;

    public OptionsPane(Configuration configuration){
        super();
        this.configuration = configuration;
        this.configuration.setOptionsPane(this);

        Label title = new Label("Options");
        this.setTop(title);


        GridPane settings = new GridPane();

        {
            Label useEyeTracker = new Label("Utiliser l'eye Tracker");
            CheckBox useEyeTrackerCheckBox = new CheckBox();
            useEyeTrackerCheckBox.selectedProperty().addListener((obj,newVal,oldVal)->{
                if(newVal){
                    useEyeTracker.setTextFill(Color.GREEN);
                    configuration.setMode(Configuration.GAZE_INTERACTION);
                } else {
                    useEyeTracker.setTextFill(Color.RED);
                    configuration.setMode(Configuration.MOUSE_INTERACTION);
                }
            });

            settings.add(useEyeTracker,0,0);
            settings.add(useEyeTrackerCheckBox,1,0);
        }

        this.setCenter(settings);

        Button back = new Button("Retour");
        back.setOnMouseClicked((e)->{
            configuration.scene.setRoot(configuration.homeScreen);
        });

        this.setLeft(back);
    }
}
