package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private static Stage stg;

    @Override
    public void start(Stage stage) throws Exception{
        stg = stage;
//        stage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage.setTitle("Bike Shop Manager");
        stage.setScene(new Scene(root));        
//        stage.setOnCloseRequest(e -> Platform.exit());        
        stage.show();
    }

    public void changeScene(String fxmlFile) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxmlFile));
        stg.getScene().setRoot(pane);
    }
    public void changeScene(Parent root) throws IOException {
//        Parent pane = FXMLLoader.load(getClass().getResource(fxmlFile));
        stg.getScene().setRoot(root);
    }
    public void changeTitle(String s) {
    	stg.setTitle(s);
    }


    public static void main(String[] args) {
        launch(args); // calls the start method
    }
}