package FloydWarshall;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class Visualisation5 extends Visualisation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    private Label label6;

    @FXML
    private Label label7;

    @FXML
    private Label label8;

    @FXML
    private Label label9;

    @FXML
    private Label label10;

    @FXML
    private Label label11;

    @FXML
    private Label label12;

    @FXML
    private Label label13;

    @FXML
    private Label label14;

    @FXML
    private Label label15;

    @FXML
    private Label label16;

    @FXML
    private Label label17;

    @FXML
    private Label label18;

    @FXML
    private Label label19;

    @FXML
    private Label label20;

    @FXML
    private Label label21;

    @FXML
    private Label label22;

    @FXML
    private Label label23;

    @FXML
    private Label label24;

    @FXML
    private Label label25;

    @FXML
    private Label label26;

    @FXML
    private Label label27;

    @FXML
    private Label label28;

    @FXML
    private Label label29;

    @FXML
    private Label label30;

    @FXML
    private Label label31;

    @FXML
    private Label label32;

    @FXML
    private Label label33;

    @FXML
    private Label label34;

    @FXML
    private Label label35;

    @FXML
    private Label label36;

    @FXML
    private Label label37;

    @FXML
    private Label label38;

    @FXML
    private Label label39;

    @FXML
    private Label label40;

    @FXML
    private Label label41;

    @FXML
    private Label label42;

    @FXML
    private Label label43;

    @FXML
    private Label label44;

    @FXML
    private Label label45;

    @FXML
    private Label label46;

    @FXML
    private Label label47;

    @FXML
    private Label label48;

    @FXML
    private Label label49;

    @FXML
    private Button start_over_button;

    @FXML
    private Button immediate_result_button;

    @FXML
    private Button next_step_button;

    @FXML
    private Button previous_step_button;

    @FXML
    private Label k_label;

    @FXML
    private Label i_label;

    @FXML
    private Label j_label;

    @FXML
    private Label if_statement_label;

    @FXML
    private Label if_statement_result_label;

    @FXML
    void initialize() {
        Label [][] array_of_labels = {
                {label1,label2,label3,label4,label5,label6,label7},
                {label8,label9,label10,label11,label12,label13,label14},
                {label15,label16,label17,label18,label19,label20,label21},
                {label22,label23,label24,label25,label26,label27,label28},
                {label29,label30,label31,label32,label33,label34,label35},
                {label36,label37,label38,label39,label40,label41,label42},
                {label43,label44,label45,label46,label47,label48,label49}
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


        for(int z = 0; z < V; z++)
            for(int x = 0; x< V; x++){
                array_of_labels[z][x].setTooltip(new Tooltip(String.format("dist [%d][%d]", z,x)));
            }

        int dist[][] = new int[V][V]; //new distance matrix

        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++)
                dist[i][j] = adj_matrix[i][j];


        immediate_result_button.setOnAction(event -> {
            changes();
            previous_step_button.setVisible(true);
            start_over_button.setVisible(true);
            ImmediateFloydWarshallAlgo(MATRIX_CODE, dist, array_of_labels,V);
            immediate_result_button.setVisible(false);
            next_step_button.setVisible(false);

            i=Visualisation.i;
            j=Visualisation.j;
            k=Visualisation.k;
            //System.out.printf("%d %d %d", i, j, k);
        });

        next_step_button.setOnAction(event -> {
            changes();
            immediate_result_button.setVisible(true);
            previous_step_button.setVisible(true);
            start_over_button.setVisible(true);

            boolean increase_j = true;
            if (k<V) {

                FloydWarshallAlgo(MATRIX_CODE, dist, array_of_labels, i, j, k);

                if (i == V - 1&& j == V-1) {
                    k++;
                    j = 0;
                    i = 0;
                    increase_j = false;
                }
                if (j == V - 1) {
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
            changes();
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
            changes();
            immediate_result_button.setVisible(true);
            start_over_button.setVisible(true);
            next_step_button.setVisible(true);
            for (int Q = 0; Q < V; Q++)
                for (int W = 0; W < V; W++)
                    dist[Q][W] = adj_matrix[Q][W];


            if(i==V&&j==V&&k==V){
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
    private final static int MATRIX_CODE = 7;

    private static int i=0,j=0,k=0; ////////////////////////////////////////// STATIC
}
