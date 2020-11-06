package main.gaze.devicemanager;

import javafx.scene.Node;

public interface GazeDeviceManager {

    void destroy();

    void addGazeMotionListener(GazeMotionListener listener);

    void removeGazeMotionListener(GazeMotionListener listener);

    void addEventFilter(Node gs);

    void addEventHandler(Node gs);

    void removeEventFilter(Node gs);

    void removeEventHandler(Node gs);

    void clear();

    void setInReplayMode(boolean b);
}
