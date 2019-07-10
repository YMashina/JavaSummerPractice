package FloydWarshall;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
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

    //@FXML
   // private TextField filename_textfield;

    @FXML
    private Button choosefile_button;

    @FXML
    private Button filename_submitbutton;

    @FXML
    private AnchorPane faq_pane;

    @FXML
    private Button main_sheet_button;

    @FXML
    private Label questionmark;

    @FXML
    private Label filenamelabel ;

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();

    }

    @FXML
    void choose_file_function(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt file with your adjacency matrix for the graph","*.txt"));
        File f = fc.showOpenDialog(null);
        if (f != null){
            filenamelabel.setText("Your file: "+ f.getAbsolutePath());
        }
        filename = f.getName();
        input = f;
    }
    File input;

    String filename;

    @FXML
    void filename_submit_button(ActionEvent event) throws  Exception{
        filenamelabel.setText("Your file: "+ input.getAbsolutePath());

       // System.out
        //System.out.println(input.getAbsolutePath());
        //String filename = filename_textfield.getText();
        //System.out.printf("'%s'",filename);
        //if(filename.length()<1) {alert("Empty filename","Surely your file does have a name!"); return;}

        File file = input;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            alert("File not found","Maybe you forgot to put it next to my app? Good luck to your next attempt!");
            return;
            //e.printStackTrace();
        }

        /*String st = new String();
        String stb = new String();
        while (true) {
            try {
                if ((st +=  br.readLine()) != null) {
                    //st.append(string);
                    break;
                }
            } catch (IOException e) {
                alert("Error while reading from file","Oops! Something weird's up with your file");
                return;
               // e.printStackTrace();
            }
        }*/

        //public static void main(String[] args) throws FileNotFoundException {

            String st = new Scanner(input)
                    .useDelimiter("\\A").next();

            //System.out.printf("{%s}\n\n",st);
       // System.out.println(st);
       // }

        //System.out.printf("'%s'",st);

        if(st=="") {alert("Empty file!","What am I supposed to do with it?"); return;}




        //System.out.printf("'%c'\n",stringToCharArray[0]);
      //  System.out.printf("'%c'\n",stringToCharArray[4]);
      //  System.out.printf("'%c'\n",stringToCharArray[3]);

        //for (int m = 0; m< stringToCharArray.length;m++){
       //     System.out.printf("m = %d[%c]\n",m, stringToCharArray[m]);
      //  }
        st = st.replace(String.format("%n"), "");
        st = st.replace("\n", "");
        char[] stringToCharArray = st.toCharArray();

        System.out.printf("'%s'\n",st);

        for (int y = 0; y< stringToCharArray.length;y++){
            //System.out.printf("'%d %c'\n",y,stringToCharArray[y]);
            if(!Character.isDigit(stringToCharArray[y]) && stringToCharArray[y]!=' '&& String.valueOf(stringToCharArray[y]).matches("\n") && stringToCharArray[y]!=','&&stringToCharArray[y]!='{'&&stringToCharArray[y]!='}'){
                alert("Invalid input", String.format("Symbol #%d = '%c' (not counting newlines) in your input makes your input incorrect", y+1, stringToCharArray[y]));
                return;
            }
        }

        st = st.replace(",", "");
        st = st.replace(String.format("%n"), "");
        st = st.replace("{", "");
        st = st.replace("}", "");
        st = st.replace("\n", "");
        st = st.replace(".", "");
        st = st.replace("/", "");
        st = st.replace("|", "");

        System.out.println(st);

        String matrix = st;
        System.out.printf("'%s'",st);
        String [] arrayOfStrings = matrix.split(" ");//turn input line from file into an array of basically "integers",
        //but they are in a form of strings. We'll parse them into an array of integers right away

        //for(int y = 0; y< arrayOfStrings.length-1;y++){
        //    System.out.printf("'%d'",arrayOfStrings[y]);
        //}
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



        callgraph();

        int [][] dist = new int [V][V];

        for (int i = 0;i<V;i++)
            for (int q = 0;q<V;q++){
                dist [i][q] = adj_matrix [i][q];
               // System.out.println(dist[i][q]);
            }



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

        File dir = new File("result");
        dir.mkdir();

        try (FileWriter writer = new FileWriter(System.getProperty("user.dir")+"/result/result.txt", false)) {

            writer.append("Here you go!"+ String.format("%n")+String.format("%n")+
                    "FLOYD-WARSHALL ALGORITHM"+String.format("%n")+"Found you the shortest paths!"+String.format("%n")+"Your matrix:"+String.format("%n"));

            for (int i = 0; i < V; i++){
                writer.append("{ ");
                for (int q = 0; q < V; q++) {
                    if (adj_matrix[i][q]>=INF)
                        writer.append('∞');
                    else
                        writer.append(Integer.toString(adj_matrix[i][q]));
                    if (q!=V-1) writer.append(",");
                    writer.append(" ");
                }
                writer.append("}"+String.format("%n"));
            }

            writer.append(String.format("%n")+"SHORTEST PATHS MATRIX:"+String.format("%n"));

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
            process = runtime.exec("notepad "+System.getProperty("user.dir")+"/result/result.txt");
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


    public static void callgraph(){

        Runtime r = Runtime.getRuntime();
        Process p = null;

        try {
            p = r.exec("java -jar buildgraph.jar");//change for Linux!
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

        Runtime run = Runtime.getRuntime();
        Process pr = null;
        try {
            pr = run.exec("java -jar dynamicgraph_1sttriangle.jar");
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

        Runtime run2 = Runtime.getRuntime();
        Process pr2 = null;
        try {
            pr2 = run2.exec("java -jar dynamicgraph_2ndtriangle.jar");
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

    }

    @FXML
    void initialize() {
        Tooltip tp = new Tooltip(String.format("Your input should begin with a number of vertexes.\nSeparate it and matrix elements with a space.\nYou can use: {},.|/"));
        Tooltip.install(questionmark, tp);

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
