package FloydWarshall;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

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
        TextField [] tfArr = {adj_matrix1_textfield1,adj_matrix1_textfield2,
                adj_matrix1_textfield3,adj_matrix1_textfield4,adj_matrix1_textfield5,adj_matrix1_textfield6};

        for (int g = 0;g<6;g++){
            tfArr[g].addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
        }

        build_graph_button.setOnAction(event -> {
            //adj_matrix1_textfield1.setMaxWidth(5);

            //FORBID EVERYTHING ASIDE INTEGERS INSIDE TEXTFIELDS
            // force the field to be numeric only
//addTextLimiter(adj_matrix1_textfield1,5);
  //          addTextLimiter(adj_matrix1_textfield2,5);

            adj_matrix1_textfield1.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));

            //adjacency_matrix [0][0] = 0;
            String str = adj_matrix1_textfield1.getText();

            build_matrix(adj_matrix1_textfield1, 0, 1);
            build_matrix(adj_matrix1_textfield2, 0, 2);
            build_matrix(adj_matrix1_textfield3, 1, 0);
            build_matrix(adj_matrix1_textfield4, 1, 2);
            build_matrix(adj_matrix1_textfield5, 2, 0);
            build_matrix(adj_matrix1_textfield6, 2, 1);


            if(!input_is_not_valid) {


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


                /*FileInputStream instream = null;
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
*/

                try {
                    Controller.callgraph();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FloydWarshall/frames/Visualisation.fxml"));//loads visualisation

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

    int [][] adjacency_matrix = new int [3][3];
}
