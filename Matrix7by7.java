package FloydWarshall;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Matrix7by7 {

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

    @FXML
    private TextField textfield21;

    @FXML
    private TextField textfield22;

    @FXML
    private TextField textfield23;

    @FXML
    private TextField textfield24;

    @FXML
    private TextField textfield25;

    @FXML
    private TextField textfield26;

    @FXML
    private TextField textfield27;

    @FXML
    private TextField textfield28;

    @FXML
    private TextField textfield29;

    @FXML
    private TextField textfield30;

    @FXML
    private TextField textfield31;

    @FXML
    private TextField textfield32;

    @FXML
    private TextField textfield33;

    @FXML
    private TextField textfield34;

    @FXML
    private TextField textfield35;

    @FXML
    private TextField textfield36;

    @FXML
    private TextField textfield37;

    @FXML
    private TextField textfield38;

    @FXML
    private TextField textfield39;

    @FXML
    private TextField textfield40;

    @FXML
    private TextField textfield41;

    @FXML
    private TextField textfield42;

    final static int MATRIX_CODE = 7;
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

    @FXML
    void initialize() {

        build_graph_button.setOnAction(event -> {

            build_matrix(textfield1, 0, 1);
            build_matrix(textfield2, 0, 2);
            build_matrix(textfield3, 0, 3);
            build_matrix(textfield4, 0, 4);
            build_matrix(textfield5, 0, 5);
            build_matrix(textfield6, 0, 6);
            build_matrix(textfield7, 1, 0);
            build_matrix(textfield8, 1, 2);
            build_matrix(textfield9, 1, 3);
            build_matrix(textfield10, 1, 4);
            build_matrix(textfield11, 1, 5);
            build_matrix(textfield12, 1, 6);
            build_matrix(textfield13, 2, 0);
            build_matrix(textfield14, 2, 1);
            build_matrix(textfield15, 2, 3);
            build_matrix(textfield16, 2, 4);
            build_matrix(textfield17, 2, 5);
            build_matrix(textfield18, 2, 6);
            build_matrix(textfield19, 3, 0);
            build_matrix(textfield20, 3, 1);
            build_matrix(textfield21, 3, 2);
            build_matrix(textfield22, 3, 4);
            build_matrix(textfield23, 3, 5);
            build_matrix(textfield24, 3, 6);
            build_matrix(textfield25, 4, 0);
            build_matrix(textfield26, 4, 1);
            build_matrix(textfield27, 4, 2);
            build_matrix(textfield28, 4, 3);
            build_matrix(textfield29, 4, 5);
            build_matrix(textfield30, 4, 6);
            build_matrix(textfield31, 5, 0);
            build_matrix(textfield32, 5, 1);
            build_matrix(textfield33, 5, 2);
            build_matrix(textfield34, 5, 3);
            build_matrix(textfield35, 5, 4);
            build_matrix(textfield36, 5, 6);
            build_matrix(textfield37, 6, 0);
            build_matrix(textfield38, 6, 1);
            build_matrix(textfield39, 6, 2);
            build_matrix(textfield40, 6, 3);
            build_matrix(textfield41, 6, 4);
            build_matrix(textfield42, 6, 5);


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

                Runtime r = Runtime.getRuntime();
                Process p = null;

                try {
                    p = r.exec("java -jar buildgraph.jar");//change for Linux!
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FloydWarshall/frames/Visualisation5.fxml"));//loads visualisation

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();


            }
            input_is_not_valid = false;
        });
    }
    int [][] adjacency_matrix = new int [MATRIX_CODE][MATRIX_CODE];
}
