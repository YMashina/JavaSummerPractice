import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.view.mxGraph;
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

import javax.swing.JFrame;
import javax.swing.JPanel;
public class BuildGraph extends JFrame
{

    /**
     *
     */
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

    public BuildGraph(){

        super("Your graph.    Krevchik Protsvetkina Mashina © 2019");

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        String matrix = read_from_file();
        var arrayOfStrings = matrix.split(" ");//turn input line from file into an array of basically "integers",
        //but they are in a form of strings. We'll parse them into an array of integers right away

        //mxMorphing();
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

        Object [] array_of_vertexes = new  Object[V];
        Object [] array_of_edges = new  Object[V];


        //////////
        graph.getModel().beginUpdate();
        //////////////

        int x = 50, y = 50;

        // morpher = new JGraphpadMorphingManager(graph);


        try
        {

            for(int w = 0; w< V; w++){

                if (w%4==0){
                    x = 50;
                }
                else if(w%4==1){
                    x=200;
                }
                else if(w%4==2){
                    x=100; y+=100;
                }
                else if(w%4==3){
                    x=400;
                }

                array_of_vertexes[w] = graph.insertVertex(parent, null, (char)('A'+w), x, y, 50,50, "shape=ellipse;strokeColor=#333333;fillColor=#123456");
                graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#222427", new Object[]{array_of_vertexes[w]});
                graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#FFFFFF", new Object[]{array_of_vertexes[w]});
                graph.setCellStyles(mxConstants.DEFAULT_FONTFAMILY, "Quicksand-Regular", new Object[]{array_of_vertexes[w]});
                //graph.setCellStyles(mxConstants.STYLE_, "#FFFFFF", new Object[]{array_of_vertexes[w]});
                if (w%2==0&& x+200<FRAME_WIDTH) {
                    x +=200;
                }
                else if(x+100>=FRAME_WIDTH) {
                    if (w % 2 == 1)
                        x = 70;
                    else x = 50;
                    y+=100;
                }
                else { y += 100; x = 100;}



            }

            //graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FFAACC", new Object[]{array_of_vertexes[2]});

            mxConstants.DEFAULT_FONTFAMILIES = "Quicksand-Regular"; //DEFAULT_FONTFAMILIES("Calibri Light");
            mxConstants.DEFAULT_FONTFAMILY = "Quicksand-Regular";
            mxConstants.DEFAULT_FONTSIZE = 30;


            int counter = 0;

            for(int q = 0; q<V; q++){
                for(int j= 0;j<V;j++){
                    if(adj_matrix[q][j]<INF){
                        array_of_edges[counter] = graph.insertEdge(parent, null, adj_matrix[q][j], array_of_vertexes[q], array_of_vertexes[j]);
                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "#636668", new Object[]{array_of_edges[counter]});
                        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#222427", new Object[]{array_of_edges[counter]});
                    }
                }
            }

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
        //graph.refresh();
        graph.setEnabled(false);

        int o = 0;



        //new mxParallelEdgeLayout(graph, 5).execute(graph.getDefaultParent()); // REDO FOR DIFFERENT MATRICES !!!!!!
        //new mxCircleLayout(graph, 150).execute(graph.getDefaultParent());
        new mxParallelEdgeLayout(graph,100).execute(graph.getDefaultParent());
        //new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);


    }




    //public void BuildGraphf (int vertex) throws IOException, InterruptedException {
    //super("Your graph.    Krevchik Protsvetkina Mashina © 2019");


    //this.getContentPane().setBackground( Color.getColor("#323232") );


    //getContentPane().setBackground( Color.getColor("#323232") );
    //JFrame.setDefaultLookAndFeelDecorated(true);


    // }



    public static void main(String[] args) throws IOException, InterruptedException {

        BuildGraph frame = new BuildGraph();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);







        //frame.setVisible(true);



        //Color c = new Color(123456);
        //frame.getContentPane().setBackground(c);
        //frame.setBackground( Color.getColor("#cdcdcd") );
    }

}