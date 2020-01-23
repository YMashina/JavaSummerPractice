package FloydWarshall;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class Matrix3by3
{

    final static int MATRIX_CODE = 3;
    final static int INF = 100000;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField adj_matrix1_textfield1 = new TextField();

    @FXML
    private TextField adj_matrix1_textfield2= new TextField();

    @FXML
    private TextField adj_matrix1_textfield4= new TextField();

    @FXML
    private TextField adj_matrix1_textfield3= new TextField();

    @FXML
    private TextField adj_matrix1_textfield5= new TextField();

    @FXML
    private TextField adj_matrix1_textfield6= new TextField();

    @FXML
    private Button build_graph_button;

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
            String str = adj_matrix1_textfield1.getText();

            build_matrix(adj_matrix1_textfield1, 0, 1);
            build_matrix(adj_matrix1_textfield2, 0, 2);
            build_matrix(adj_matrix1_textfield3, 1, 0);
            build_matrix(adj_matrix1_textfield4, 1, 2);
            build_matrix(adj_matrix1_textfield5, 2, 0);
            build_matrix(adj_matrix1_textfield6, 2, 1);


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


                FileInputStream instream = null;
                FileOutputStream outstream = null;

                try{
                    File infile =new File("adjacency_matrix.txt");
                    File outfile =new File("dynamic_adjacency_matrix.txt");

                    instream = new FileInputStream(infile);
                    outstream = new FileOutputStream(outfile);

                    byte[] buffer = new byte[1024];

                    int length;
                    /*copying the contents from input stream to
                     * output stream using read and write methods
                     */
                    while ((length = instream.read(buffer)) > 0){
                        outstream.write(buffer, 0, length);
                    }

                    //Closing the input/output file streams
                    instream.close();
                    outstream.close();

                    //System.out.println("File copied successfully!!");

                }catch(IOException ioe){
                    ioe.printStackTrace();
                }


                Controller.callgraph();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FloydWarshall/frames/Visualisation.fxml"));//loads visualisation

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

    int [][] adjacency_matrix = new int [3][3];
}
