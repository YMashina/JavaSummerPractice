package FloydWarshall;

import java.io.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class Visualisation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label if_statement_label;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label if_statement_result_label;

    @FXML
    private Label AA_connection_label = new Label();

    @FXML
    private Label AB_connection_label = new Label();

    @FXML
    private Label AC_connection_label = new Label();

    @FXML
    private Label BA_connection_label = new Label();

    @FXML
    private Label BB_connection_label = new Label();

    @FXML
    private Label BC_connection_label = new Label();

    @FXML
    private Label CA_connection_label = new Label();

    @FXML
    private Label CB_connection_label = new Label();

    @FXML
    private Label CC_connection_label = new Label();

    @FXML
    private Button next_step_button;

    @FXML
    private Button immediate_result_button;

    @FXML
    private Label k_label;

    @FXML
    private Label i_label;

    @FXML
    private Label j_label;

    @FXML
    private Button previous_step_button;

    @FXML
    private Button start_over_button;


    @FXML
    void initialize() {

        final int MATRIX_CODE = 3;

        Label [][] array_of_labels = {
                {AA_connection_label,AB_connection_label, AC_connection_label},
                {BA_connection_label,BB_connection_label,BC_connection_label},
                {CA_connection_label,CB_connection_label,CC_connection_label}
        };

        previous_step_button.setVisible(false);
        start_over_button.setVisible(false);


        Tooltip tp = new Tooltip(String.format("always checking the following:\ndist[i][k] + dist[k][j] < dist[i][j]"));
        Tooltip.install(if_statement_label, tp);

        String matrix = read_from_file();
        String [] arrayOfStrings = matrix.split(" ");//turn input line from file into an array of basically "integers",
        //but they are in a form of strings. We'll parse them into an array of integers right away
        int V = Integer.parseInt(arrayOfStrings[0]);
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



        int dist[][] = new int[V][V]; //new adjacency matrix
        //int i, j, k;

        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++)
                dist[i][j] = adj_matrix[i][j];


        immediate_result_button.setOnAction(event -> {
            previous_step_button.setVisible(true);
            start_over_button.setVisible(true);
            ImmediateFloydWarshallAlgo(MATRIX_CODE,dist, array_of_labels,V);
            immediate_result_button.setVisible(false);
            next_step_button.setVisible(false);
        });

        next_step_button.setOnAction(event -> {
            immediate_result_button.setVisible(true);
            previous_step_button.setVisible(true);
            start_over_button.setVisible(true);

            boolean increase_j = true;
            if (k<V) {
                //System.out.printf("%d %d %d\n",k,i,j);
                FloydWarshallAlgo(MATRIX_CODE, dist, array_of_labels, i, j, k);

                if (i == V - 1&& j == V-1) {
                    k++;
                    j = 0;
                    i = 0;
                    increase_j = false;
                }
                if (j == V - 1) {//
                    i++;
                    j = 0;//
                    increase_j = false;
                }
                if (increase_j) j++;

            }

            else{
            immediate_result_button.setVisible(false);
            next_step_button.setVisible(false);
            }

        });

        start_over_button.setOnAction(event -> {
            previous_step_button.setVisible(false);
            start_over_button.setVisible(false);
            for (int Q = 0; Q < V; Q++)
                for (int W = 0; W < V; W++)
                    dist[Q][W] = adj_matrix[Q][W];
            i=0; k =0; j = 0;
            immediate_result_button.setVisible(true);
            next_step_button.setVisible(true);
            FloydWarshallAlgo(MATRIX_CODE, dist, array_of_labels, i, j, k);
        });

        previous_step_button.setOnAction(event -> {
            immediate_result_button.setVisible(true);
            start_over_button.setVisible(true);
            next_step_button.setVisible(true);
            for (int Q = 0; Q < V; Q++)
                for (int W = 0; W < V; W++)
                    dist[Q][W] = adj_matrix[Q][W];

            if(i==V && j == V && k == V){
                i--; j--; k--;
            }
            else {

                if (j != 0) {
                    j--;

                } else if (i != 0) {
                    i--;
                    j = V - 1;

                } else if (k != 0) {
                    i = V - 1;
                    j = V - 1;
                    k--;

                }
            }
            int k2 = k, i2 = i, j2 = j; //backup for ijk
            k = 0; j = 0; i = 0;

            while(i!=i2||j!=j2||k!=k2){

                boolean increase_j = true;
                if (k<V) {

                    FloydWarshallAlgo(MATRIX_CODE, dist, array_of_labels,i, j, k);

                    if (i == V - 1&& j == V-1) {
                        k++;
                        j = 0;
                        i = 0;
                        increase_j = false;
                    }
                    if (j == V - 1) {//
                        i++;
                        j = 0;//
                        increase_j = false;
                    }
                    if (increase_j) j++;

                }

            }

            if(i==0 && j==0 && k==0){
                previous_step_button.setVisible(false);
                start_over_button.setVisible(false);
            }

        });

    }

    static int i=0,j=0,k=0; //////

    final static int INF = 100000;
    protected  String read_from_file()  { //read the file with adjacency matrix input from GUI
        File file = new File("adjacency_matrix.txt");


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String st = null; //System.out.println(st);
        try {
            st = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;

    }


    protected void FloydWarshallAlgo(int MATRIX_CODE, int [][] dist,Label [][] array_of_labels, int i, int j, int k) {

        for(int q = 0; q<MATRIX_CODE;q++)
            for(int w = 0;w<MATRIX_CODE;w++) {
                array_of_labels[q][w].setTextFill(Color.web("#777777"));
                array_of_labels[q][w].setFont(new Font("Calibri Light", 18));
            }

        array_of_labels[i][k].setFont(new Font("Calibri", 28));
        array_of_labels[k][j].setFont(new Font("Calibri", 28));
        array_of_labels[i][j].setFont(new Font("Calibri", 28));


        array_of_labels[i][k].setTextFill(Color.web("#bb0a1e"));
        array_of_labels[k][j].setTextFill(Color.web("#49796b"));
        array_of_labels[i][j].setTextFill(Color.web("#18004c"));

        k_label.setText(String.format("k = %d", k));
        i_label.setText(String.format("i = %d", i));
        j_label.setText(String.format("j = %d", j));


        if (dist[i][k] + dist[k][j] < dist[i][j]) {
            if_statement_label.setText(String.format("dist[%d][%d] + dist[%d][%d] < dist[%d][%d]", i, k, k, j, i, j));
            dist[i][j] = dist[i][k] + dist[k][j];
            if_statement_result_label.setText(String.format(" dist[%d][%d] = %d = %d + %d", i, j, dist[i][j], dist[i][k], dist[k][j]));
            Tooltip tp = new Tooltip(String.format("dist[%d][%d] = dist[%d][%d] + dist[%d][%d]", i, j, i, k, k, j));
            Tooltip.install(if_statement_result_label, tp);

        } else {
            if_statement_result_label.setText("oh no! hover over to know why");
            StringBuilder str = new StringBuilder();
            if (dist[i][k] + dist[k][j]>=INF)
                str.append(String.format("dist[%d][%d] + dist[%d][%d] = ∞\nand it's not less than ", i, k, k, j));
            else
                str.append(String.format("dist[%d][%d] + dist[%d][%d] = %d\nand it's not less than ", i, k, k, j, dist[i][k] + dist[k][j]));
            if (dist[i][j]>=INF)
                str.append(String.format("dist[%d][%d] = ∞!", i, j));
            else
                str.append(String.format("dist[%d][%d] = %d!", i, j, dist[i][j]));

            Tooltip tp = new Tooltip(str.toString());
            Tooltip.install(if_statement_result_label, tp);
            if_statement_label.setText(String.format("dist[%d][%d] + dist[%d][%d] >= dist[%d][%d]", i, k, k, j, i, j));
        }

        for(int z = 0; z < MATRIX_CODE; z++){

            for(int x = 0; x < MATRIX_CODE; x++){
                if (z==x)
                    continue;
                if (dist[z][x] < INF) array_of_labels[z][x].setText(String.valueOf(dist[z][x]));
                else {array_of_labels[z][x].setText("∞");

                }
            }
        }

    }

    protected void ImmediateFloydWarshallAlgo(int MATRIXCODE, int [][] dist,Label [][] array_of_labels, int V){
        for (k = 0; k < V; k++)
        {
            for (i = 0; i < V; i++)
            {
                for (j = 0; j < V; j++)
                {
                    FloydWarshallAlgo(MATRIXCODE, dist, array_of_labels, i,j,k);
                }
            }
        }
    }




}
