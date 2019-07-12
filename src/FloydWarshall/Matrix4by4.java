package FloydWarshall;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
            Controller.alert("Invalid input","Only integer > 0 input is valid");
            return false;}


        return true;
    }

    private void build_matrix(TextField textfield, int a, int b){
        if(!isNumeric(textfield)) adjacency_matrix [a][b] = INF;
        else if(Integer.parseInt(textfield.getText())>=0){adjacency_matrix [a][b] = Integer.parseInt(textfield.getText());}
        else {
            input_is_not_valid = true;
            Controller.alert("Invalid input","Only integer > 0 input is valid");

        }
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

        TextField [] tfArr = {adj_matrix2_textfield1,adj_matrix2_textfield2,
                adj_matrix2_textfield3,adj_matrix2_textfield4,adj_matrix2_textfield5,adj_matrix2_textfield6,
                adj_matrix2_textfield7, adj_matrix2_textfield8,adj_matrix2_textfield9,adj_matrix2_textfield10,
                adj_matrix2_textfield11,adj_matrix2_textfield12};

        for (int g = 0;g<12;g++){
            tfArr[g].addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        }

        adj_matrix2_textfield1.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        adj_matrix2_textfield2.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        adj_matrix2_textfield3.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        adj_matrix2_textfield4.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        adj_matrix2_textfield5.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        adj_matrix2_textfield6.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        adj_matrix2_textfield7.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        adj_matrix2_textfield8.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        adj_matrix2_textfield9.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        adj_matrix2_textfield10.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        adj_matrix2_textfield11.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        adj_matrix2_textfield12.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));

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


            if(!input_is_not_valid){


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

                try {
                    Controller.callgraph();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.showAndWait();


            }
            input_is_not_valid = false;
        });
    }
        int [][] adjacency_matrix = new int [4][4];
}
