package FloydWarshall;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Integer> choice_box_number_of_vertexes = new ChoiceBox<>();

    @FXML
    private AnchorPane main_pane = new AnchorPane();

    @FXML
    private Button get_started_button;

    @FXML
    private Button floydwhat_button;

    @FXML
    private Button fileinput_button;

    @FXML
    private Button exit_button;

    @FXML
    private Button faq_button;

    @FXML
    private AnchorPane explanation_pane;

    @FXML
    private AnchorPane fileinput_pane;

    @FXML
    private TextField filename_textfield;

    @FXML
    private Button filename_submitbutton;

    @FXML
    private AnchorPane faq_pane;

    @FXML
    private Button main_sheet_button;

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();

    }

    @FXML
    void filename_submit_button(ActionEvent event) throws  Exception{
        String filename = filename_textfield.getText();
        //System.out.printf("'%s'",filename);
        if(filename.length()<1) {alert("Empty filename","Surely your file does have a name!"); return;}

        File file = new File(filename+".txt");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            alert("File not found","Maybe you forgot to put it next to my app? Good luck to your next attempt!");
            return;
            //e.printStackTrace();
        }

        String st;
        while (true) {
            try {
                if ((st = br.readLine()) != null) break;
            } catch (IOException e) {
                alert("Error while reading from file","Oops! Something weird's up with your file");
                return;
               // e.printStackTrace();
            }
        }
        //System.out.println(st);

        if(st=="") {alert("Empty file!","What am I supposed to do with it?"); return;}


        char[] stringToCharArray = st.toCharArray();
        for (int y = 0; y< stringToCharArray.length;y++){
            if(!Character.isDigit(stringToCharArray[y]) && stringToCharArray[y]!=' '&& stringToCharArray[y]!='\n'&&stringToCharArray[y]!=','&&stringToCharArray[y]!='{'&&stringToCharArray[y]!='}'){
                alert("Invalid input", String.format("Symbol #%d in your input makes your input incorrect", y+1));
                return;
            }
        }

        st = st.replace(",", "");
        st = st.replace("\n", "");
        st = st.replace("{", "");
        st = st.replace("}", "");



        String matrix = st;
        String [] arrayOfStrings = matrix.split(" ");//turn input line from file into an array of basically "integers",
        //but they are in a form of strings. We'll parse them into an array of integers right away
        int V = Integer.parseInt(arrayOfStrings[0]);
        if(V*V!=arrayOfStrings.length-1){
            alert("Invalid input","Invalid adjacency matrix representation: declared number of vertexes doesn't match its matrix");
            return;
        }


        int input_array[] = new int [arrayOfStrings.length-1];

        for (int i = 0; i< arrayOfStrings.length-1; i++){
            input_array[i]= Integer.parseInt(arrayOfStrings[i+1]);
        }
        int adj_matrix[][] = new int [V][V];

        int input_array_iterator = 0;
        for(int q = 0; q<V; q++)
            for(int j= 0;j<V;j++){
                adj_matrix[q][j] = input_array[input_array_iterator];
                input_array_iterator++;
            }

        for (int y = 0; y < V;y++)
            if(adj_matrix[y][y]!=0) {
                alert("Invalid input","Cycles have been found in your matrix!");
                return;
            }



        try (FileWriter writer = new FileWriter("adjacency_matrix.txt", false)) {

            writer.append(st);

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }


        Runtime r = Runtime.getRuntime();
        Process p = null;

        try {
            p = r.exec("java -jar buildgraph.jar");
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

        int [][] dist = new int [V][V];

        for (int i = 0;i<V;i++)
            for (int q = 0;q<V;q++)
                dist [i][q] = adj_matrix [i][q];



        for (int k = 0; k < V; k++)
        {
            // Pick all vertices as source one by one
            for (int i = 0; i < V; i++)
            {
                // Pick all vertices as destination for the
                // above picked source
                for (int j = 0; j < V; j++)
                {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }

        try (FileWriter writer = new FileWriter("result.txt", false)) {

            writer.append("Here you go!"+ String.format("%n")+String.format("%n")+
                    "FLOYD-WARSHALL ALGORITHM"+String.format("%n")+"Found you the shortest paths!"+String.format("%n"));
            for (int i = 0; i < V; i++){
                writer.append("{ ");
                for (int q = 0; q < V; q++) {
                    writer.append(Integer.toString(dist[i][q]));
                    if (q!=V-1) writer.append(",");
                    writer.append(" ");
                }
                writer.append("}"+String.format("%n"));
            }

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

        Runtime runtime = Runtime.getRuntime();
        Process process = null;

        try {
            process = runtime.exec("notepad result.txt");
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }


        FXMLLoader loader = new FXMLLoader();

        switch (V){
            case 3:
                //System.out.println("Entered case 3");
                loader.setLocation(getClass().getResource("/FloydWarshall/frames/Visualisation.fxml"));//loads visualisation

                break;
            case 4:
                loader.setLocation(getClass().getResource("/FloydWarshall/frames/Visualisation2.fxml"));//loads visualisation

                break;
            case 5:
                loader.setLocation(getClass().getResource("/FloydWarshall/frames/Visualisation3.fxml"));//loads visualisation

                break;
            case 6:
                loader.setLocation(getClass().getResource("/FloydWarshall/frames/Visualisation4.fxml"));//loads visualisation

                break;
            case 7:
                loader.setLocation(getClass().getResource("/FloydWarshall/frames/Visualisation5.fxml"));//loads visualisation

                break;
            case 8:

                break;
        }
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



    private boolean isNumeric(String str){
        if(str.length()<1)
            return false;
        try{Integer.valueOf(str);}catch(Exception e){
            return false;}
        return true;
    }

    private final int INF = 100000;

    private void build_matrix(String str, int [][] adjacency_matrix, int a, int b){
        if(!isNumeric(str)) adjacency_matrix [a][b] = INF;
        else adjacency_matrix [a][b] = Integer.parseInt(str);
    }

    private void alert(String alert_message1, String alert_message2){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(alert_message1);
        errorAlert.setContentText(alert_message2);
        errorAlert.showAndWait();
    }



    @FXML
    void explanation(ActionEvent event) {

        fadeOut(main_pane);
        main_pane.setDisable(true);

        fadeOut(fileinput_pane);
        fileinput_pane.setDisable(true);

        fadeOut(faq_pane);
        faq_pane.setDisable(true);



        fadeIn(explanation_pane);
        explanation_pane.setDisable(false);

    }

    @FXML
    void faq(ActionEvent event) {

        fadeOut(main_pane);
        main_pane.setDisable(true);



        fadeOut(fileinput_pane);
        fileinput_pane.setDisable(true);

        fadeOut(explanation_pane);
        explanation_pane.setDisable(true);

        fadeIn(faq_pane);
        faq_pane.setDisable(false);



    }

    @FXML
    void fileinput(ActionEvent event) {


        fadeOut(main_pane);
        main_pane.setDisable(true);

        fadeOut(explanation_pane);
        explanation_pane.setDisable(true);

        fadeOut(faq_pane);
        faq_pane.setDisable(true);


        fadeIn(fileinput_pane);
        fileinput_pane.setDisable(false);



    }

    @FXML
    void main_sheet(ActionEvent event) {

        fadeIn(main_pane);
        main_pane.setDisable(false);

        fadeOut(fileinput_pane);
        fileinput_pane.setDisable(true);

        fadeOut(explanation_pane);
        explanation_pane.setDisable(true);

        fadeOut(faq_pane);
        faq_pane.setDisable(true);

    }

    private void fadeIn(AnchorPane pane){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(0.5));
        fadeTransition.setNode(pane);
        fadeTransition.setFromValue(0.8);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void fadeOut(AnchorPane pane){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(0.5));
        fadeTransition.setNode(pane);
        fadeTransition.setFromValue(0.01);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

    @FXML
    void initialize() {
        main_pane.setDisable(false);
        fadeIn(main_pane);
        choice_box_number_of_vertexes.getItems().addAll( 3, 4, 5, 6, 7);
        choice_box_number_of_vertexes.getSelectionModel().selectFirst();
        get_started_button.setOnAction(event -> {

            get_started_button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();

            //PLEASE ADD SWITCH-CASE BASED ON CHOICE BOX ONCE OTHER .FXMLS ARE READY
            switch(choice_box_number_of_vertexes.getValue()){
                case 3:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/matrix3by3.fxml"));//loads 3*3 matrix


                    break;
                case 4:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix4by4.fxml"));//loads 4*4 matrix


                    break;
                case 5:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix5by5.fxml"));//loads 5*5 matrix


                    break;
                case 6:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix6by6.fxml"));//loads 6*6 matrix


                    break;
                case 7:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix7by7.fxml"));//loads 7*7 matrix


                    break;
                case 8:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix8by8.fxml"));//loads 8*8 matrix


                case 9:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix9by9.fxml"));//loads 9*9 matrix


                case 10:
                    loader.setLocation(getClass().getResource("/FloydWarshall/frames/Matrix10by10.fxml"));//loads 10*10 matrix


                    break;
                default:
                    break;

            }


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
}
