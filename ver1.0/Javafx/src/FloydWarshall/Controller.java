package FloydWarshall;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Integer> choice_box_number_of_vertexes = new ChoiceBox<>();



    @FXML
    private Button get_started_button;

    @FXML
    void initialize() {
        choice_box_number_of_vertexes.getItems().addAll( 3, 4, 5, 6, 7);
        choice_box_number_of_vertexes.getSelectionModel().selectFirst();
        get_started_button.setOnAction(event -> {

            get_started_button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();

            //PLEASE ADD SWITCH-CASE BASED ON CHOICE BOX ONCE OTHER .FXMLS ARE READY
            switch(choice_box_number_of_vertexes.getValue()){
                case 3:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/matrix3by3.fxml"));//loads 3*3 matrix

                    try {
                        loader.load();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix4by4.fxml"));//loads 4*4 matrix

                    try {
                        loader.load();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix5by5.fxml"));//loads 5*5 matrix

                    try {
                        loader.load();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix6by6.fxml"));//loads 6*6 matrix

                    try {
                        loader.load();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix7by7.fxml"));//loads 7*7 matrix

                    try {
                        loader.load();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 8:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix8by8.fxml"));//loads 8*8 matrix

                    try {
                        loader.load();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 9:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix9by9.fxml"));//loads 9*9 matrix

                    try {
                        loader.load();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 10:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix10by10.fxml"));//loads 10*10 matrix

                    try {
                        loader.load();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;

            }


            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();


        });

    }
}
