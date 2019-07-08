package FloydWarshall;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("frames/Menu.fxml"));
        primaryStage.setTitle("Main menu");
        primaryStage.setScene(new Scene(root, 600, 425));
        primaryStage.show();
    }


    public static void main(String[] args) {
        File file = new File("adjacency_matrix.txt");
        file.delete();

        launch(args);


    }
}
