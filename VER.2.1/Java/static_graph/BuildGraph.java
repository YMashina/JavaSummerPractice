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


public class BuildGraph extends JFrame
{

    /**
     *
     */
    //JFrame.setBackground( Color.getColor("#cdcdcd") );
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
        super("Your graph.    Krevchik Protsvetkina Mashina © 2019");


        //this.getContentPane().setBackground( Color.getColor("#323232") );

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

        int x = 40, y = 40;

       // morpher = new JGraphpadMorphingManager(graph);


        try
        {

            for(int w = 0; w< V; w++){
                array_of_vertexes[w] = graph.insertVertex(parent, null, (char)('A'+w), x, y, 50,50, "shape=ellipse;strokeColor=#333333;fillColor=#123456");
                graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#222427", new Object[]{array_of_vertexes[w]});
                graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#FFFFFF", new Object[]{array_of_vertexes[w]});
                graph.setCellStyles(mxConstants.DEFAULT_FONTFAMILY, "Quicksand-Regular", new Object[]{array_of_vertexes[w]});
                //graph.setCellStyles(mxConstants.STYLE_, "#FFFFFF", new Object[]{array_of_vertexes[w]});
                if (x< FRAME_WIDTH-80) x +=200;
                else {x = 50; y += 100;}


            }

            mxConstants.DEFAULT_FONTFAMILIES = "Quicksand-Regular"; //DEFAULT_FONTFAMILIES("Calibri Light");
            mxConstants.DEFAULT_FONTFAMILY = "Quicksand-Regular";
            mxConstants.DEFAULT_FONTSIZE = 30;


int counter = 0;

            for(int q = 0; q<V; q++){
                for(int j= 0;j<V;j++){
                    if(adj_matrix[q][j]>0&&adj_matrix[q][j]<INF){
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




        //new mxParallelEdgeLayout(graph, 5).execute(graph.getDefaultParent()); // REDO FOR DIFFERENT MATRICES !!!!!!
        new mxCircleLayout(graph, 150).execute(graph.getDefaultParent());
        //new mxParallelEdgeLayout(graph,100).execute(graph.getDefaultParent());
        //new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
        //getContentPane().setBackground( Color.getColor("#323232") );
        //JFrame.setDefaultLookAndFeelDecorated(true);
    }



    public static void main(String[] args)
    {

        BuildGraph frame = new BuildGraph();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
        //Color c = new Color(123456);
        //frame.getContentPane().setBackground(c);
        //frame.setBackground( Color.getColor("#cdcdcd") );
    }

}