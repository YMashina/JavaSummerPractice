package FloydWarshall;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.mxPartitionLayout;
import com.mxgraph.swing.mxGraphComponent;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.HashMap;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JFrame;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import javax.swing.*;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Visualisation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label if_statement_label;


    @FXML
    private Label if_statement_result_label;

    @FXML
    private Label AB_connection_label;

    @FXML
    private Label AC_connection_label;

    @FXML
    private Label BA_connection_label;

    @FXML
    private Label BC_connection_label;

    @FXML
    private Label CA_connection_label;

    @FXML
    private Label CB_connection_label;

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

    //private static final Object sMonitor = new Object();

    //private boolean button_pressed = false;

    @FXML
    void initialize() {
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

        switch(V){
            case 3:
                int input_array_iterator = 0;
                for(int q = 0; q<V; q++)
                    for(int j= 0;j<V;j++){
                        adj_matrix[q][j] = input_array[input_array_iterator];
                        input_array_iterator++;
                    }


                break;
            default:
                break;

        }



        int dist[][] = new int[V][V]; //new adjacency matrix
        //int i, j, k;

        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++)
                dist[i][j] = adj_matrix[i][j];


        immediate_result_button.setOnAction(event -> {
            ImmediateFloydWarshallAlgo(dist, V);
        });

        next_step_button.setOnAction(event -> {

            boolean increase_j = true;
            if (k<V) {
                //System.out.printf("%d %d %d\n",k,i,j);
                FloydWarshallAlgo(dist, i, j, k);

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


                //else j++;
                //j++;//
            }

        });

    }





    int i=0,j=0,k=0;

    final static int INF = 100000;
    public  String read_from_file()  { //read the file with adjacency matrix input from GUI
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
        //System.out.println(st);
        //System.out.println("Hello World!");
    }

    public void FloydWarshallAlgo(int [][] dist, int i, int j, int k) {

        k_label.setText(String.format("k = %d", k));
        i_label.setText(String.format("i = %d", i));
        j_label.setText(String.format("j = %d", j));


        if (dist[i][k] + dist[k][j] < dist[i][j]) {
            if_statement_label.setText(String.format("dist[%d][%d] + dist[%d][%d] < dist[%d][%d]", i, k, k, j, i, j));
            dist[i][j] = dist[i][k] + dist[k][j];
            if_statement_result_label.setText(String.format(" dist[%d][%d] = %d = %d + %d", i, j, dist[i][j], dist[i][k], dist[k][j]));
            Tooltip tp = new Tooltip(String.format("dist[%d][%d] + dist[%d][%d] < dist[%d][%d]", i, k, k, j, i, j));
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

        if (dist[0][1] < INF) AB_connection_label.setText(String.valueOf(dist[0][1]));
        else AB_connection_label.setText("∞");
        if (dist[0][2] < INF) AC_connection_label.setText(String.valueOf(dist[0][2]));
        else AC_connection_label.setText("∞");
        if (dist[1][0] < INF) BA_connection_label.setText(String.valueOf(dist[1][0]));
        else BA_connection_label.setText("∞");
        if (dist[1][2] < INF) BC_connection_label.setText(String.valueOf(dist[1][2]));
        else BC_connection_label.setText("∞");
        if (dist[2][0] < INF) CA_connection_label.setText(String.valueOf(dist[2][0]));
        else CA_connection_label.setText("∞");
        if (dist[2][1] < INF) CB_connection_label.setText(String.valueOf(dist[2][1]));
        else CB_connection_label.setText("∞");

    }

    public void ImmediateFloydWarshallAlgo(int [][] dist, int V){
        for (k = 0; k < V; k++)
        {
            for (i = 0; i < V; i++)
            {
                for (j = 0; j < V; j++)
                {
                    FloydWarshallAlgo(dist, i,j,k);
                }
            }
        }
    }

}
