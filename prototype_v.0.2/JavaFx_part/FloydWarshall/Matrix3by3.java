package FloydWarshall;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private TextField adj_matrix1_textfield1= new TextField();

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

    @FXML
    void initialize() {
        build_graph_button.setOnAction(event -> {

            //FORBID EVERYTHING ASIDE INTEGERS INSIDE TEXTFIELDS
            // force the field to be numeric only


            adjacency_matrix [0][0] = 0;
            String str = adj_matrix1_textfield1.getText();
            if(adj_matrix1_textfield1.getText().length()<1||!Character.isDigit(adj_matrix1_textfield1.getText().charAt(0))) adjacency_matrix [0][1] = INF;
            else
            adjacency_matrix [0][1] = Integer.parseInt(adj_matrix1_textfield1.getText());
            if(adj_matrix1_textfield2.getText().length()<1||!Character.isDigit(adj_matrix1_textfield2.getText().charAt(0))) adjacency_matrix [0][2] = INF;
            else
            adjacency_matrix [0][2] = Integer.parseInt(adj_matrix1_textfield2.getText());
            if(adj_matrix1_textfield3.getText().length()<1||!Character.isDigit(adj_matrix1_textfield3.getText().charAt(0))) adjacency_matrix [1][0] = INF;
            else
            adjacency_matrix [1][0] = Integer.parseInt(adj_matrix1_textfield3.getText());
            adjacency_matrix [1][1] = 0;
            if(adj_matrix1_textfield4.getText().length()<1||!Character.isDigit(adj_matrix1_textfield4.getText().charAt(0))) adjacency_matrix [1][2] = INF;
            else
            adjacency_matrix [1][2] = Integer.parseInt(adj_matrix1_textfield4.getText());
            if(adj_matrix1_textfield5.getText().length()<1||!Character.isDigit(adj_matrix1_textfield5.getText().charAt(0))) adjacency_matrix [2][0] = INF;
            else
            adjacency_matrix [2][0] = Integer.parseInt(adj_matrix1_textfield5.getText());
            if(adj_matrix1_textfield6.getText().length()<1||!Character.isDigit(adj_matrix1_textfield6.getText().charAt(0))) adjacency_matrix [2][1] = INF;
            else
            adjacency_matrix [2][1] = Integer.parseInt(adj_matrix1_textfield6.getText());
            adjacency_matrix [2][2] = 0;

                    try(FileWriter writer = new FileWriter("adjacency_matrix.txt", false))
                    {

                        writer.append(Integer.toString(MATRIX_CODE));
                        writer.append(' ');
                        for(int i = 0;i<3;i++)
                            for(int q = 0; q<3;q++){
                                writer.append(Integer.toString(adjacency_matrix[i][q]));
                                writer.append(' ');
                            }

                        writer.flush();
                    }
                    catch(IOException ex){

                        System.out.println(ex.getMessage());
                    }

            Runtime r = Runtime.getRuntime();
            Process p = null;

            try{p = r.exec("C:/Program Files/Java/jdk-12.0.1/bin/java.exe -jar buildgraph.jar"); }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FloydWarshall/frames/Visualisation.fxml"));//loads visualisation

            try {
                loader.load();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });



    }

    int [][] adjacency_matrix = new int [3][3];
}
