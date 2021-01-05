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
import java.util.LinkedList;

public class Configuration {

    public int numberOfLastPositionsToCheck = 200;
    public LinkedList<Point2D> lastPositions = new LinkedList<>();

    public LinkedList<Point2D> currentPoint = new LinkedList<>();

    public final static int MOUSE_INTERACTION = 0;
    public final static int GAZE_INTERACTION = 1;
    IntegerProperty selectionMode = new SimpleIntegerProperty(Configuration.GAZE_INTERACTION);
    boolean userIsMoving = false;

    public final Timeline timeline;

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
        return !userIsMoving || lasPositionDidntMoved();
    }

    public void analyse(double x, double y){
        if(
                (currentPoint!=null && currentPoint.size() >0
                        && !isArround(x,currentPoint.getLast().getX()) && !isArround(y,currentPoint.getLast().getY()))
        ){
//
//            System.out.println(" user move " + x + " - " + y);
//            System.out.println(" gaze move " + currentPoint.getLast().getX() + " - " + currentPoint.getLast().getY());
            this.userIsMoving = true;
        }
    }

    public boolean isArround(double coord0, double coord1 ){
        return coord0 <= coord1+2 && coord0 >= coord1-2;
    }

    public void launchTimeline(){
        while(lastPositions.size()>=numberOfLastPositionsToCheck) {
            lastPositions.pop();
        }
        lastPositions.add(new Point2D(MouseInfo.getPointerInfo().getLocation().getX(), MouseInfo.getPointerInfo().getLocation().getY()));
       // timeline.playFromStart();
    }

    public boolean lasPositionDidntMoved(){
        if (lastPositions.size() == numberOfLastPositionsToCheck){
            Point2D pos = lastPositions.get(0);
                for (int i = 0; i < numberOfLastPositionsToCheck; i++){
                    if(!lastPositions.get(i).equals(pos)){
                        return false;
                    }
                }
                lastPositions.clear();
                this.userIsMoving = false;
            return  true;
        }
        return false;
    }
}
