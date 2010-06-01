import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class Kanvas extends JLabel implements MouseMotionListener, MouseListener
{
	public static final int OFFSET_X = 50;
	public static final int OFFSET_Y = 50;
	
	int startx;
	int starty;
	int dragx;
	int dragy;
	int endx;
	int endy;
	
	boolean created=false;
	
	BufferedImage pic;
	int indexNamaFile=0;
	
	CyTagger parent;
	
	public Kanvas(CyTagger parent)
	{
		addMouseMotionListener(this);
		addMouseListener(this);
		this.parent=parent;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.clearRect(0,0,getWidth(), getHeight());
		if(pic!=null)
		{
			g2.drawImage(pic, 0,0,null);
		}
		g2.setColor(Color.red);
		g2.drawRect(startx, starty, dragx-startx,dragy-starty);
		
		Iterator<Tag> it = parent.tag.iterator();
		while(it.hasNext())
		{
			Tag curr =  it.next();
			g2.draw(curr.rect);
			g2.drawString(curr.str, curr.rect.x, curr.rect.y);
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		//System.out.println( e );
		dragx = e.getX();
		dragy = e.getY();
		
		//int min = dragx-startx>dragy-starty?dragy-starty:dragx-startx;
		//dragx = startx+min;
		//dragy = starty+min;
		repaint();
	}
	
	public void mouseMoved(MouseEvent e) 
	{
		
	}
	
	
	public void mouseClicked(MouseEvent e)
	{
		clearBox();
	}
	//Invoked when the mouse button has been clicked (pressed and released) on a component.
	public void mouseEntered(MouseEvent e)
	{
		
	}
	//Invoked when the mouse enters a component.
	public void mouseExited(MouseEvent e)
	{
		
	}
	//Invoked when the mouse exits a component.
	public void mousePressed(MouseEvent e)
	{
		//System.out.println(e.getX());
		//System.out.println(e.getY());
		
		startx = e.getX();
		starty = e.getY();
	}
	//Invoked when a mouse button has been pressed on a component.
	public void mouseReleased(MouseEvent e)
	{
		//System.out.println(e.getX());
		//System.out.println(e.getY());
		
		endx = e.getX();
		endy = e.getY();
		created = true;
		//int min = endx-startx>endy-starty?endy-starty:endx-startx;
		//endx = min + startx;
		//endy = min + starty;
	}
	//Invoked when a mouse button has been released on a component.
	
	public void setPic(BufferedImage pict)
	{
		this.pic = pict;
		setPreferredSize(new Dimension(pict.getWidth(), pict.getHeight()));
		clearBox();
		repaint();
	}
	
	public Dimension getPreverredSize()
	{
		return new Dimension(pic.getWidth(), pic.getHeight());
	}
	
	public void clearBox()
	{
		startx=0;
		starty=0;
		dragx=0;
		dragy=0;
		endx=0;
		endy=0;
		created = true;
		repaint();
	}
	
	public void saveTag(StringBuffer buff)
	{
		
	}
}
