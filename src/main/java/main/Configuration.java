package main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class Configuration {

    public Point2D currentPoint = null;

    public final static int MOUSE_INTERACTION = 0;
    public final static int GAZE_INTERACTION = 1;
    IntegerProperty selectionMode = new SimpleIntegerProperty(Configuration.GAZE_INTERACTION);
    boolean userIsMoving = false;

    public final Timeline timeline;

    @Setter
    HomeScreen homeScreen;
    @Setter
    OptionsPane optionsPane;
    @Setter
    Scene scene;

    public Configuration(){
        timeline = new Timeline();
        ProgressIndicator indicator = new ProgressIndicator(0);
        timeline.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(indicator.progressProperty(), 0)),
                new KeyFrame(new Duration(2000), new KeyValue(indicator.progressProperty(), 1)));
        timeline.onFinishedProperty().set(actionEvent -> {
            System.out.println("done");
            this.userIsMoving = false;
        });
    }

    public boolean isGazeInteraction(){
        return selectionMode.intValue() == Configuration.GAZE_INTERACTION;
    }

    public boolean waitForUserMove(){
        return !userIsMoving;
    }

    public void setMode(int newMode){
        selectionMode.setValue(newMode);
    }

    public void analyse(double x, double y){
        if(
                (currentPoint!=null && !isArround(x,currentPoint.getX()) && !isArround(y,currentPoint.getY()))
        ){

            System.out.println(" user move " + x + " - " + y);
            System.out.println(" gaze move " + currentPoint.getX() + " - " + currentPoint.getY());
            this.userIsMoving = true;
            launchTimeline();
        }
    }

    public boolean isArround(double coord0, double coord1 ){
        return coord0 <= coord1+2 && coord0 >= coord1-2;
    }

    public void launchTimeline(){
        timeline.playFromStart();
    }
}
