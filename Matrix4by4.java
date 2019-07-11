package FloydWarshall;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Matrix4by4 {

    final static int MATRIX_CODE = 4;
    final static int INF = 100000;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField adj_matrix2_textfield1 = new TextField();

    @FXML
    private TextField adj_matrix2_textfield2 = new TextField();

    @FXML
    private TextField adj_matrix2_textfield5 = new TextField();

    @FXML
    private TextField adj_matrix2_textfield4 = new TextField();

    @FXML
    private TextField adj_matrix2_textfield7 = new TextField();

    @FXML
    private TextField adj_matrix2_textfield8 = new TextField();

    @FXML
    private Button build_graph_button;

    @FXML
    private TextField adj_matrix2_textfield12 = new TextField();

    @FXML
    private TextField adj_matrix2_textfield10 = new TextField();

    @FXML
    private TextField adj_matrix2_textfield11 = new TextField();

    @FXML
    private TextField adj_matrix2_textfield9 = new TextField();

    @FXML
    private TextField adj_matrix2_textfield3 = new TextField();

    @FXML
    private TextField adj_matrix2_textfield6 = new TextField();

    @FXML
    void initialize(ActionEvent event) {

    }

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

            //FORBID EVERYTHING ASIDE INTEGERS INSIDE TEXTFIELDS
            // force the field to be numeric only


            //adjacency_matrix [0][0] = 0;
            String str = adj_matrix2_textfield1.getText();

            build_matrix(adj_matrix2_textfield1, 0, 1);
            build_matrix(adj_matrix2_textfield2, 0, 2);
            build_matrix(adj_matrix2_textfield3, 0, 3);
            build_matrix(adj_matrix2_textfield4, 1, 0);
            build_matrix(adj_matrix2_textfield5, 1, 2);
            build_matrix(adj_matrix2_textfield6, 1, 3);
            build_matrix(adj_matrix2_textfield7, 2, 0);
            build_matrix(adj_matrix2_textfield8, 2, 1);
            build_matrix(adj_matrix2_textfield9, 2, 3);
            build_matrix(adj_matrix2_textfield10, 3, 0);
            build_matrix(adj_matrix2_textfield11, 3, 1);
            build_matrix(adj_matrix2_textfield12, 3, 2);


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
                loader.setLocation(getClass().getResource("/FloydWarshall/frames/Visualisation2.fxml"));//loads visualisation

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
        int [][] adjacency_matrix = new int [4][4];
}
