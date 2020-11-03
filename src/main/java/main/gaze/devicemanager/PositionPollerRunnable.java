package main.gaze.devicemanager;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.SecondStage;
import tobii.Tobii;

@Slf4j
public class PositionPollerRunnable implements Runnable {

    private final TobiiGazeDeviceManager tobiiGazeDeviceManager;
    private final SecondStage stage;

    @Setter
    private transient boolean stopRequested = false;

    public PositionPollerRunnable(final TobiiGazeDeviceManager tobiiGazeDeviceManager, SecondStage stage) {
        this.tobiiGazeDeviceManager = tobiiGazeDeviceManager;
        this.stage = stage;
    }

    @Override
    public void run() {
        while (!stopRequested) {
            if(stage.proc==null) {
                try {
                    poll();
                } catch (final RuntimeException e) {
                    log.warn("Exception while polling position of main.gaze", e);
                }
            }

            // sleep is mandatory to avoid too much calls to gazePosition()
            try {
                Thread.sleep(10);
            } catch (InterruptedException | RuntimeException e) {
                log.warn("Exception while sleeping until next poll", e);
            }
        }
    }

    private void poll() {
        final float[] pointAsFloatArray = Tobii.gazePosition();

        final float xRatio = pointAsFloatArray[0];
        final float yRatio = pointAsFloatArray[1];

        final Rectangle2D screenDimension = Screen.getPrimary().getBounds();//screenDimensionSupplier.get();
        final double positionX = xRatio * screenDimension.getWidth();
        final double positionY = yRatio * screenDimension.getHeight();


        final double offsetX = 0;
        final double offsetY =0;

        final Point2D point = new Point2D(positionX + offsetX, positionY + offsetY);

        Platform.runLater(() -> tobiiGazeDeviceManager.onGazeUpdate(point, "main/gaze"));
    }

}
