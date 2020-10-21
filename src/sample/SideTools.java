package sample;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class SideTools extends Pane {

    public SideTools(Stage primaryStage){
        super();
        Rectangle background = new Rectangle(100,900,Color.RED);
        StackPane sp = new StackPane();
        this.prefWidthProperty().bind(background.widthProperty());
        this.prefHeightProperty().bind(background.heightProperty());

        VBox bv = new VBox();
        Button exit = new Button("exit");
        exit.setOnMouseClicked((e)->{
            Platform.exit();
            System.exit(0);
        });
        bv.getChildren().add(exit);
        sp.getChildren().addAll(background, bv);
        this.getChildren().add(sp);
        primaryStage.setX(Screen.getPrimary().getBounds().getWidth()/2 - 100);
        primaryStage.setY(Screen.getPrimary().getBounds().getHeight()/2 - 450);


    }
}
