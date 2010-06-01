import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class KanvasSearch extends JLabel
{
	public static final int OFFSET_X = 50;
	public static final int OFFSET_Y = 50;
	
	CySearchTagger parent;
	BufferedImage pic;
	
	public KanvasSearch(CySearchTagger parent)
	{
		this.parent=parent;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.clearRect(0,0,getWidth(), getHeight());
		if(pic!=null)
		{
			System.out.println("masuk");
			g2.drawImage(pic, 0,0,null);
		}
		
		g.setColor(Color.red);
		
		Iterator<Tag> it = parent.tag.iterator();
		while(it.hasNext())
		{
			Tag curr =  it.next();
			g2.draw(curr.rect);
			g2.drawString(curr.str, curr.rect.x, curr.rect.y);
		}
	}
	
	public void setPic(BufferedImage pict)
	{
		this.pic = pict;
		setPreferredSize(new Dimension(pict.getWidth(), pict.getHeight()));
		repaint();
	}
}
