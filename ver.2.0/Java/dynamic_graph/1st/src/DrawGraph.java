 
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

//import jdk.internal.platform.Container;



public class DrawGraph extends JPanel
{
	//int V = 0;
	
	public static int [][] inputTable ;
	//		{ 	{0,1,1,1,1} ,
	//			{1,0,5,0,0} ,
	//			{1,5,0,0,1} ,
	//			{1,0,0,0,1} ,
	//			{1,0,1,1,0} ,
	//		};
	public String[] Nodename = new String[]{"A","B","C","D","E"};
	
	ArrayList<Node> all;
	ArrayList<Edge> edges;
	
	final int INF = 100000;
	
	public void GenerateGraph()
	{
		
		String matrix = read_from_file();
	    String [] arrayOfStrings = matrix.split(" ");//turn input line from file into an array of basically "integers",
	    //but they are in a form of strings. We'll parse them into an array of integers right away

	    //mxMorphing();
	    V = Integer.parseInt(arrayOfStrings[0]);
	    
	    
	    
	    //System.out.printf("V='%d'",V);
	    
	    
	    int input_array[] = new int [arrayOfStrings.length-1];

	    for (int i = 0; i< arrayOfStrings.length-1; i++){
	        input_array[i]= Integer.parseInt(arrayOfStrings[i+1]);
	    }

	    
	    
for(int y = 0;y<input_array.length;y++) {
	//System.out.printf("'%d'\n",input_array[y]);
}
	    int adj_matrix[][] = new int [V][V];

	    int input_array_iterator = 0;
	    for(int q = 0; q<V; q++)
	        for(int j= 0;j<V;j++){
	            adj_matrix[q][j] = input_array[input_array_iterator];
	            //System.out.println(adj_matrix[q][j]);
	            input_array_iterator++;
	        }
		
	    int n= V;
		inputTable = new int[n][n];
	    
	    for(int k = 0; k<V;k++) {
			for (int q = 0; q< V; q++) {
				if (adj_matrix[k][q]<INF)
				inputTable[k][q] = adj_matrix[k][q];
				else
					inputTable[k][q] = 0;
			}
		}
		
	    
	    /*for (int i = 0; i < n; i++) {/////////////////////////////////////////
            for (int j = i+1; j < n; j++) {
            	//if(inputTable[i][j]<inputTable[j][i]) {
                int temp = inputTable[i][j];
                inputTable[i][j] = inputTable[j][i];
                inputTable[j][i] = temp;
                //}
            }
        } *///////////////////////////////////////////////////////////////////
		
		Nodename = new String[n];
		
		/*for(int i=0;i< Nodename.length;i++)
		{ 
			for(int j=i+1;j< Nodename.length;j++)
			{ 
				if(Math.random() > 0.6)
				{
					inputTable[i][j] = (int)(Math.random() * 20);
					inputTable[j][i] =inputTable[i][j] ;
				}
			}
		}*/
		for(int i=0;i< Nodename.length;i++)
		{ 
			Nodename[i]= "" + (char)(65+i);
		}
	}
	
	static int V = 0;
	
	public void GenerateGraph2()
	{
		
		
		String matrix = read_from_file();
	    String [] arrayOfStrings = matrix.split(" ");//turn input line from file into an array of basically "integers",
	    //but they are in a form of strings. We'll parse them into an array of integers right away

	    //mxMorphing();
	    V = Integer.parseInt(arrayOfStrings[0]);
	    
	    
	    
	    //System.out.printf("V='%d'",V);
	    
	    
	    int input_array[] = new int [arrayOfStrings.length-1];

	    for (int i = 0; i< arrayOfStrings.length-1; i++){
	        input_array[i]= Integer.parseInt(arrayOfStrings[i+1]);
	    }

	    
	    
for(int y = 0;y<input_array.length;y++) {
	//System.out.printf("'%d'\n",input_array[y]);
}
	    int adj_matrix[][] = new int [V][V];

	    int input_array_iterator = 0;
	    for(int q = 0; q<V; q++)
	        for(int j= 0;j<V;j++){
	            adj_matrix[q][j] = input_array[input_array_iterator];
	            //System.out.println(adj_matrix[q][j]);
	            input_array_iterator++;
	        }
		
	    //n= V;
	    int n= V;
	    
	    inputTable = new int[n][n];
		Nodename = new String[n];
		
		
	    
	    for(int k = 0; k<V;k++) {
			for (int q = 0; q< V; q++) {
				if (adj_matrix[k][q]<INF)
				inputTable[k][q] = adj_matrix[k][q];
				else
					inputTable[k][q] = 0;
			}
		}
		
	    /*for (int i = 0; i < n; i++) {////////////////////////////
            for (int j = i+1; j < n; j++) {
                int temp = inputTable[i][j];
                inputTable[i][j] = inputTable[j][i];
                inputTable[j][i] = temp;
            }
        }*/
		/*for(int i=0;i< Nodename.length;i++)
		{ 
			for(int j=i+1;j< Nodename.length;j++)
			{ 
				if(     Math.random()/Math.log(j-i)*Math.log(2)*2 > 0.5)
				{
					inputTable[i][j] = (int)(Math.random() * 7*(j-i));
					inputTable[j][i] =inputTable[i][j] ;
				}
			}
		}*/
		for(int i=0;i< Nodename.length;i++)
		{ 
			Nodename[i]= "" + (char)(65+i);
		}
		
		GenerateGraph();
	}
	
	
	public void ProcessInput() throws Exception
	{
		GenerateGraph();
		//GenerateGraph2();
		all = new ArrayList<Node>();
		edges =new ArrayList<Edge>();
		
		for(int i=0;i< Nodename.length;i++)
		{
			all.add(new Node());
			all.get(i).name = Nodename[i]; 
			all.get(i).Adj= new ArrayList<Edge>();
		}
		
		
		
		for(int i=0;i< Nodename.length;i++)
		{ 
			for(int j=i+1;j< Nodename.length;j++)
			{ 
				if(inputTable[i][j] != 0)
				{
					Edge e = new Edge();
					e.a = all.get(i);
					e.b = all.get(j);
					e.length = inputTable[i][j] ;
					edges.add(e);
					all.get(i).Adj.add(e);
					all.get(j).Adj.add(e); 
				}
			}
		}
		
		
	}
	
	
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
	
	
	public DrawGraph() throws Exception
	{
		//this.getContentPane().setBackground(Color.RED);
		ProcessInput();
		Thread t = new Thread(new Runnable()
		{ 
			@Override
			public void run()
			{
				 while(true)
				 {
					try
					{
						Thread.sleep(50);
					} catch (InterruptedException e)
					{ 
						e.printStackTrace();
					}
					for(int j=0;j< Nodename.length;j++)
					{ 
						all.get(j).acc=new Vector();
						all.get(j).calForce(all);
						all.get(j). calForceEdge( ); 
						all.get(j).move();
					}
					//scale
					Scale();
					//centroid
					Vector centoid = all.get(0).getCentroid(all);
					Vector temp = (new Vector(300,300)).sub(centoid);
					for(int j=0;j< Nodename.length;j++)
					{ 
						 all.get(j).posTodraw=all.get(j).posTodraw.add(temp) ;
					}
					
					
					
					
					repaint();
					
				 }
			}
		});
		t.start();
		
		
		//JPanel p = new JPanel();
		JFrame f = new JFrame("Your flying graph: secondary connections");
		//f.setLocationRelativeTo(right);
		f.add(new JPanel() {

            @Override // placeholder for actual content
            public Dimension getPreferredSize() {
                return new Dimension(585, 500);
            }

        });
        f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - f.getWidth();
        int y = 0;
        f.setLocation(x, y);
        f.setVisible(true);
		//p.setVisible(true);
		//f.add(p);
		
		f.getContentPane().setBackground(Color.black);//getColor("#232428")
		f.setBackground(Color.black);
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600,600);
		f.setVisible(true);
		
		final Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir"));
		//System.out.println(path);
		try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
		    final WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
		    while (true) {
		        final WatchKey wk = watchService.take();
		        for (WatchEvent<?> event : wk.pollEvents()) {
		            //we only register "ENTRY_MODIFY" so the context is always a Path.
		            final Path changed = (Path) event.context();
		            //System.out.println(changed);
		            if (changed.endsWith("changes.txt")) {
		                //System.out.println("c");
		            	ProcessInput();
		            }
		        }
		        // reset the key
		        boolean valid = wk.reset();
		        if (!valid) {
		            System.out.println("Key has been unregisterede");
		        }
		    }
		}
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		for(int j=0;j< Nodename.length;j++)
		{
			try {
				all.get(j).Draw(g);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Graphics2D g2 = (Graphics2D) g;
		for(int j=0;j< edges.size();j++)
		{
			edges.get(j).Draw((Graphics2D)g);
		}
	}
	public static void main(String[] args) throws Exception
	{
		new DrawGraph();
	}
	
	
	public void Scale()
	{
		double XMin= Integer.MAX_VALUE;
		double YMin= Integer.MAX_VALUE;
		double XMax= Integer.MIN_VALUE;
		double YMax= Integer.MIN_VALUE;
		
		for(int j=0;j< Nodename.length;j++)
		{
			if(all.get(j).pos.getX() < XMin)
			{
				XMin=all.get(j).pos.getX();
			}
			if(all.get(j).pos.getY() < YMin)
			{
				YMin=all.get(j).pos.getY();
			}
			if(all.get(j).pos.getX() > XMax)
			{
				XMax=all.get(j).pos.getX();
			}
			if(all.get(j).pos.getY() > YMax)
			{
				YMax=all.get(j).pos.getY();
			}
		}
		
		double length_x = XMax- XMin;
		double length_y = YMax- YMin;
		double length  = Math.max(length_x, length_y);
		for(int j=0;j< Nodename.length;j++)
		{
			 Vector vv = all.get(j).pos;
			 vv= vv.Mul(400.0 / length);
			 all.get(j).setPosToDraw(vv);
		}
		
		
		
	}

}
