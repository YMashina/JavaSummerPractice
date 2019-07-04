
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;

import java.io.*;
import javax.swing.JFrame;

import com.mxgraph.view.mxGraph;

import javax.swing.*;

public class BuildGraph extends JFrame
{

    /**
     *
     */
   // private static final long serialVersionUID = -2707712944901661771L;
    final static int INF = 100000, FRAME_WIDTH = 500,FRAME_HEIGHT = 500;


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

    public BuildGraph()
    {
        super("Your graph");


        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        String matrix = read_from_file();
        var arrayOfStrings = matrix.split(" ");//turn input line from file into an array of basically "integers",
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

        Object [] array_of_vertexes = new  Object[V];


        //////////
        graph.getModel().beginUpdate();
        //////////////

        int x = 40, y = 40;


        try
        {

            for(int w = 0; w< V; w++){
                array_of_vertexes[w] = graph.insertVertex(parent, null, (char)('A'+w), x, y, 50,50, "shape=ellipse");

                if (x< FRAME_WIDTH-80) x +=100;
                else {x = 20; y += 50;}
            }

            for(int q = 0; q<V; q++){
                for(int j= 0;j<V;j++){
                    if(adj_matrix[q][j]>0&&adj_matrix[q][j]<INF){
                        graph.insertEdge(parent, null, adj_matrix[q][j], array_of_vertexes[q], array_of_vertexes[j]);

                    }
                }
            }

        }
        finally
        {
            graph.getModel().endUpdate();
        }

        //graph.setConnectable(false);

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


        new mxCircleLayout(graph, 150).execute(graph.getDefaultParent());
        //new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }

    public static void main(String[] args)
    {

        BuildGraph frame = new BuildGraph();

        JLabel label = new JLabel(
                "This JPanel uses Absolute Positioning"
                , JLabel.CENTER);
        label.setSize(300, 30);
        label.setLocation(5, 5);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}