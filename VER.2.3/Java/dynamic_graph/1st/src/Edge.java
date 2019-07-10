import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;


public class Edge
{
	public Node a,b;
	public int length;
	
	public void Draw(Graphics2D g)
	{
		g.setColor(Color.black);
		g.drawLine ((int)(a.posTodraw.getX() ), (int)(a.posTodraw.getY() ),(int)(b.posTodraw.getX() ),(int)(b.posTodraw.getY() )    );
		//Point sw = new Point((int)(a.posTodraw.getX()), (int)(a.posTodraw.getY() ));
        //Point ne = new Point((int)(b.posTodraw.getX()),(int)(b.posTodraw.getY()));
		
		Point sw = new Point((int)(a.posTodraw.getX()), (int)(a.posTodraw.getY() ));
        Point ne = new Point((int)(a.posTodraw.getX()+b.posTodraw.getX())/2, (int)(a.posTodraw.getY()+b.posTodraw.getY() )/2);
		//swap(sw,ne);
		drawArrowHead(g, ne, sw, Color.black);
		double xx,yy;
		xx= (a.posTodraw.getX()  + b.posTodraw.getX() )/2;
		yy= (a.posTodraw.getY()  + b.posTodraw.getY() )/2;
		
		g.drawString(""+length,(int) xx,(int) yy);
	}
	
	public Vector getForce(Node toCal)
	{
		Vector dir ;
		if(a== toCal) {
			dir = b.pos.sub(a.pos);
		}
		else
		{
			dir = a.pos.sub(b.pos);
		}
		 double t=dir.Size()-10*length;
		double ss = Math.signum(t) *Math.log( Math.abs(t))*0.01;
		////System.out.println("ss=" + ss);
		dir = dir.Unit().Mul(ss);
		return dir;
	}
	
	private void drawArrowHead(Graphics2D g2, Point tip, Point tail, Color color)
    {
        g2.setPaint(color);
        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);
        double phi = Math.toRadians(40);
        int barb = 20;
        //System.out.println("theta = " + Math.toDegrees(theta));
        double x, y, rho = theta + phi;
        for(int j = 0; j < 2; j++)
        {
            x = tip.x - barb * Math.cos(rho);
            y = tip.y - barb * Math.sin(rho);
            g2.draw(new Line2D.Double(tip.x, tip.y, x, y));
            rho = theta - phi;
        }
    }

}
