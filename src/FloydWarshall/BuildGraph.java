package FloydWarshall;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
public class BuildGraph extends JFrame
{
   // super("Your graph.    Krevchik Protsvetkina Mashina © 2019");


    //JFrame.setBackground( Color.getColor("#cdcdcd") );
    // private static final long serialVersionUID = -2707712944901661771L;
    final static int INF = 100000, FRAME_WIDTH = 500,FRAME_HEIGHT = 500;


    public  static String read_from_file()  { //read the file with adjacency matrix input from GUI
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

    static mxGraph graph = new mxGraph();
    static mxGraphComponent graphComponent = new mxGraphComponent(graph);
    static Object [] array_of_vertexes ;
    static Object [][] array_of_edges ;
    static int V = 0;
    static int adj_matrix[][];

    static int flag = 0;

    public void BuildGraphf(){




        Object parent = graph.getDefaultParent();

        String matrix = read_from_file();
        String [] arrayOfStrings = matrix.split(" ");//turn input line from file into an array of basically "integers",
        //but they are in a form of strings. We'll parse them into an array of integers right away

        int V = Integer.parseInt(arrayOfStrings[0]);
        int input_array[] = new int [arrayOfStrings.length-1];

        adj_matrix = new int [V][V];

        for (int i = 0; i< arrayOfStrings.length-1; i++){
            input_array[i]= Integer.parseInt(arrayOfStrings[i+1]);
        }


        array_of_vertexes = new  Object[V];
        array_of_edges = new  Object[V][V];



        int input_array_iterator = 0;
        for(int q = 0; q<V; q++)
            for(int j= 0;j<V;j++){
                adj_matrix[q][j] = input_array[input_array_iterator];
                input_array_iterator++;
            }

        graph.getModel().beginUpdate();

        int x = 0, y = 0;


        try
        {

            for(int w = 0; w< V; w++){

                if (w%4==0){
                    x = 50; y += 100;
                }
                else if(w%4==1){
                    x=300;
                }
                else if(w%4==2){
                    x=100; y+=100;
                }
                else if(w%4==3){
                    x=400;
                }

                array_of_vertexes[w] = graph.insertVertex(parent, null, (char)('A'+w), x, y, 50,50, "shape=ellipse;strokeColor=#000000;fillColor=#123456");
                graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#26282b", new Object[]{array_of_vertexes[w]});
                graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#dddddd", new Object[]{array_of_vertexes[w]});
                graph.setCellStyles(mxConstants.DEFAULT_FONTFAMILY, "Quicksand-Regular", new Object[]{array_of_vertexes[w]});



            }


            mxConstants.DEFAULT_FONTFAMILIES = "Quicksand-Regular"; //DEFAULT_FONTFAMILIES("Calibri Light");
            mxConstants.DEFAULT_FONTFAMILY = "Quicksand-Regular";
            mxConstants.DEFAULT_FONTSIZE = 30;


            int counter = 0;

            for(int q = 0; q<V; q++){
                for(int j= 0;j<V;j++){
                    if(adj_matrix[q][j]<INF&&q!=j){
                        array_of_edges[q][j] = graph.insertEdge(parent, null, adj_matrix[q][j], array_of_vertexes[q], array_of_vertexes[j]);
                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "#3e4047", new Object[]{array_of_edges[q][j]});
                        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#222427", new Object[]{array_of_edges[q][j]});
                        graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "2", new Object[]{array_of_edges[q][j]});

                        counter++;
                    }
                }
            }

           // System.out.printf("'%d'", counter);
        }
        finally
        {
            graph.getModel().endUpdate();
        }


        graph.setCellsEditable(false);
        graph.setAllowDanglingEdges(false);
        graph.setAllowLoops(false);
        graph.setCellsDeletable(false);
        graph.setCellsCloneable(false);
        graph.setCellsDisconnectable(false);
        graph.setDropEnabled(false);
        graph.setSplitEnabled(false);
        graph.setCellsBendable(false);
        graph.setDisconnectOnMove(false);
        graph.setDropEnabled(false);
        graph.setAllowNegativeCoordinates(false);
        graph.setSwimlaneNesting(true);
        graph.setCellsCloneable(false);
        graph.setCellsDisconnectable(false);
        graph.setAllowLoops(false);
        graph.setSplitEnabled(false);
        graph.setCellsEditable(false);
        graph.setAllowDanglingEdges(false);


        graph.setEnabled(false);

        int o = 0;


        new mxCircleLayout(graph, 200).execute(graph.getDefaultParent());
        new mxParallelEdgeLayout(graph, 30).execute(graph.getDefaultParent()); // REDO FOR DIFFERENT MATRICES !!!!!!


        mxGraphComponent graphComponent2 = new mxGraphComponent(graph);
        graphComponent = graphComponent2;


    }



    static void addcomponent(int Num, int vertex1,int vertex2, int vertex3){

       /* for(int q = 0; q<Num; q++){
            for(int j= 0;j<Num;j++){
                System.out.printf("%d ",adj_matrix[q][j]);
            }
            System.out.printf("\n");
        }*/

       // System.out.println("1-2");
        for(int q = 0; q<Num; q++){
            for(int j= 0;j<Num;j++){
                if(q!=j){
                    if (adj_matrix[q][j]<=INF){
                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "#3e4047", new Object[]{array_of_edges[q][j]});
                        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#323232", new Object[]{array_of_edges[q][j]});
                        graph.setCellStyles(mxConstants.STYLE_FONTSIZE, "30", new Object[]{array_of_edges[q][j]});
                        graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "2", new Object[]{array_of_edges[q][j]});
                    }
                }

            }

            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#26282b", new Object[]{array_of_vertexes[q]});


            graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#dddddd", new Object[]{array_of_vertexes[q]});

        }
//#76b800 NVIDIA

        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#222427", new Object[]{array_of_vertexes[vertex1],array_of_vertexes[vertex2],array_of_vertexes[vertex3]});
        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#76b800", new Object[]{array_of_vertexes[vertex1],array_of_vertexes[vertex2],array_of_vertexes[vertex3]});

         //graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#76b800", new Object[]{array_of_vertexes[vertex1],array_of_vertexes[vertex2],array_of_vertexes[vertex3]});

        if (adj_matrix[vertex1][vertex2]<=INF){
            graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "#669f00", new Object[]{array_of_edges[vertex1][vertex2]});
           // System.out.println("11");
            graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#3a5a00", new Object[]{array_of_edges[vertex1][vertex2]});
            graph.setCellStyles(mxConstants.STYLE_FONTSIZE, "50", new Object[]{array_of_edges[vertex1][vertex2]});
            graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "3", new Object[]{array_of_edges[vertex1][vertex2]});
        }
        if (adj_matrix[vertex2][vertex3]<=INF) {
           // System.out.println("12");
            graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "#669f00", new Object[]{array_of_edges[vertex2][vertex3]});
            //System.out.println("13");
            graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#3a5a00", new Object[]{array_of_edges[vertex2][vertex3]});
            graph.setCellStyles(mxConstants.STYLE_FONTSIZE, "50", new Object[]{array_of_edges[vertex2][vertex3]});
            graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "3", new Object[]{array_of_edges[vertex2][vertex3]});
        }
       // System.out.println("14");
        if (adj_matrix[vertex1][vertex3]<=INF) {
            graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "#669f00", new Object[]{array_of_edges[vertex1][vertex3]});
           // System.out.println("15");
            graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#3a5a00", new Object[]{array_of_edges[vertex1][vertex3]});
            graph.setCellStyles(mxConstants.STYLE_FONTSIZE, "50", new Object[]{array_of_edges[vertex1][vertex3]});
            //System.out.println("16");
            graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "3", new Object[]{array_of_edges[vertex1][vertex3]});
        }

        //graphComponent.refresh(); // perhaps unnecessary

        // graph.refresh();// perhaps unnecessary
        // System.out.println("add component entered");

    }



    void addC(){
        getContentPane().add(graphComponent);
    }

    static BuildGraph frame = new BuildGraph();
    public static void Buildgraph() throws IOException, InterruptedException {


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);

        frame.setTitle("Your graph.    Krevchik Protsvetkina Mashina © 2019");
        frame.BuildGraphf();

        frame.addC();


        frame.setVisible(true);


    }

}