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
        choice_box_number_of_vertexes.getItems().addAll( 3, 4, 5, 6, 7, 8, 9, 10);
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
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
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
