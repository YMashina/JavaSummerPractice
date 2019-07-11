package FloydWarshall;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Matrix5by5 {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button build_graph_button;

    @FXML
    private TextField textfield1;

    @FXML
    private TextField textfield2;

    @FXML
    private TextField textfield3;

    @FXML
    private TextField textfield4;

    @FXML
    private TextField textfield5;

    @FXML
    private TextField textfield6;

    @FXML
    private TextField textfield7;

    @FXML
    private TextField textfield8;

    @FXML
    private TextField textfield9;

    @FXML
    private TextField textfield10;

    @FXML
    private TextField textfield11;

    @FXML
    private TextField textfield12;

    @FXML
    private TextField textfield13;

    @FXML
    private TextField textfield14;

    @FXML
    private TextField textfield15;

    @FXML
    private TextField textfield16;

    @FXML
    private TextField textfield17;

    @FXML
    private TextField textfield18;

    @FXML
    private TextField textfield19;

    @FXML
    private TextField textfield20;

    final static int MATRIX_CODE = 5;
    final static int INF = 100000;

    private boolean isNumeric(TextField textfield){

        if(textfield.getText().length()<1)
            return false;

        try{Integer.valueOf(textfield.getText());}catch(Exception e){
            input_is_not_valid = true;
            return false;}


        return true;
    }

    private void build_matrix(TextField textfield, int a, int b){
        if(!isNumeric(textfield)) adjacency_matrix [a][b] = INF;
        else adjacency_matrix [a][b] = Integer.parseInt(textfield.getText());
    }

    private boolean input_is_not_valid = false;

    public EventHandler<KeyEvent> maxLength(final Integer i) {
        return new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {

                TextField tx = (TextField) arg0.getSource();
                if (tx.getText().length() >= i) {
                    arg0.consume();
                }

            }

        };

    }

    @FXML
    void initialize() {

        TextField [] tfArr = {textfield1,textfield2,
                textfield3,textfield4,textfield5,textfield6,textfield7,
                textfield8,textfield9,textfield10,textfield11,textfield12,
                textfield13,textfield14,textfield15,textfield16,textfield17,
                textfield18,textfield19,textfield20};

        for (int g = 0;g<20;g++){
            tfArr[g].addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        }

        build_graph_button.setOnAction(event -> {

            build_matrix(textfield1, 0, 1);
            build_matrix(textfield2, 0, 2);
            build_matrix(textfield3, 0, 3);
            build_matrix(textfield4, 0, 4);
            build_matrix(textfield5, 1, 0);
            build_matrix(textfield6, 1, 2);
            build_matrix(textfield7, 1, 3);
            build_matrix(textfield8, 1, 4);
            build_matrix(textfield9, 2, 0);
            build_matrix(textfield10, 2, 1);
            build_matrix(textfield11, 2, 3);
            build_matrix(textfield12, 2, 4);
            build_matrix(textfield13, 3, 0);
            build_matrix(textfield14, 3, 1);
            build_matrix(textfield15, 3, 2);
            build_matrix(textfield16, 3, 4);
            build_matrix(textfield17, 4, 0);
            build_matrix(textfield18, 4, 1);
            build_matrix(textfield19, 4, 2);
            build_matrix(textfield20, 4, 3);


            if(input_is_not_valid){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Only numeric input is valid!");
                errorAlert.setContentText("Why would anyone input such a weird edge weight anyways??");
                errorAlert.showAndWait();
            }

            else {


                try (FileWriter writer = new FileWriter("adjacency_matrix.txt", false)) {

                    writer.append(Integer.toString(MATRIX_CODE));
                    writer.append(' ');
                    for (int i = 0; i < MATRIX_CODE; i++)
                        for (int q = 0; q < MATRIX_CODE; q++) {
                            writer.append(Integer.toString(adjacency_matrix[i][q]));
                            writer.append(' ');
                        }

                    writer.flush();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }

                Controller.callgraph();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FloydWarshall/frames/Visualisation3.fxml"));//loads visualisation

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.showAndWait();


            }
            input_is_not_valid = false;
        });
    }
    int [][] adjacency_matrix = new int [MATRIX_CODE][MATRIX_CODE];
}
