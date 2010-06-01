import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.imageio.*;
import java.awt.image.*;

public class CyTagger extends JPanel implements ActionListener
{
	JButton setDirectory = new JButton("Choose Directory");
	JFileChooser dirChooser = new JFileChooser();
	File currentDirectory = null;
	
	JPanel atastengah;
	JPanel atas;
	JPanel kiri;
	JButton back;
	JButton forward;
	JButton tagIt;
	JButton saveTag;
	
	File[] files;
	int index;
	
	Kanvas kanvas;
	
	Border border = BorderFactory.createLineBorder(Color.black);
	
	ArrayList<Tag> tag = new ArrayList<Tag>();
	
	JLabel fileInfo;
	
	boolean tagSaved;
	
	Tagger parent;

	JScrollPane kanvasScroll;
	
	public CyTagger(Tagger parent)
	{
		//super("kambing");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(800,600);
		//setVisible(true);
		this.parent = parent;
		
		setLayout(new BorderLayout());
		
		//add(setDirectory, BorderLayout.WEST);
		
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		setDirectory.addActionListener(this);
		
		
		kanvas = new Kanvas(this);
		
		atastengah = new JPanel();
		atas = new JPanel();
		kiri = new JPanel();
		back = new JButton("<< Previous");
		forward = new JButton("Next >>");
		tagIt = new JButton("TagIt");
		saveTag = new JButton("SaveTag");

		fileInfo = new JLabel();

		//kiri.add(setDirectory);
		//add(kiri, BorderLayout.WEST);
		
		//atas.setBorder(border);
		//kiri.setBorder(border);
		
		back.addActionListener(this);
		forward.addActionListener(this);
		tagIt.addActionListener(this);
		saveTag.addActionListener(this);
		
		atas.setLayout(new BorderLayout());
		
		atas.add(setDirectory, BorderLayout.WEST);
		atas.add(atastengah, BorderLayout.CENTER);
		atastengah.add(back);
		atastengah.add(forward);
		atastengah.add(tagIt);
		atastengah.add(saveTag);
		
		add(atas, BorderLayout.NORTH);
		
		//kanvas.setBorder(border);
		kanvasScroll = new JScrollPane(kanvas, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		/*kanvasScroll.addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent event)
			{
				kanvas.repaint();
			}
		});*/
		add(kanvasScroll, BorderLayout.CENTER);
		//tag.add(new Tag(new Rectangle(10,10,10,10), "kambing"));
		
		
		changeDirectory(currentDirectory);
		validate();
		
		///loadTag(new File("kambing.txt"));
		//saveTag(new File("kucing.txt"));
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == setDirectory)
		{
			System.out.println("set");
			int returnValue = dirChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) 
			{
				File selectedFile = dirChooser.getSelectedFile();
				System.out.println(selectedFile.getPath());
				changeDirectory(selectedFile);
			}
			else if(returnValue == JFileChooser.CANCEL_OPTION) 
			{
				System.out.println("di cancel");
			}
		}
		
		if(currentDirectory==null)
			return;
		
		if(e.getSource()==forward)
		{
			try
			{
				index++;
				if(index>=files.length)
				{
					index=0;
					//JOptionPane.showMessageDialog(null, "tidak bisa maju lagi, kembali ke awal");
				}
				//BufferedImage image = ImageIO.read(files[index]);
				displayPic();
				fileInfo.setText(files[index].getName());
				kanvas.clearBox();
				//kanvas.repaint();
				System.out.println("kambing");
			}
			catch (Exception f){}
		}
		else if(e.getSource()==back)
		{
			try
			{
				index--;
				if(index<0)
				{
					index=files.length-1;
					//JOptionPane.showMessageDialog(null, "tidak bisa mundur lagi, kembali ke akhir");
				}
				//BufferedImage image = ImageIO.read(files[index]);
				displayPic();
				fileInfo.setText(files[index].getName());
				kanvas.clearBox();
				//parent.validate();
			}
			catch (Exception f){}
		}
		else if(e.getSource() == tagIt)
		{
			System.out.println("tagit");
			if(currentDirectory==null)
				return;
			
			if(kanvas.created)
			{
				String namaTag = JOptionPane.showInputDialog(this, "isi tag");
				if(namaTag!=null)
				{
					tag.add(new Tag(new Rectangle(kanvas.startx, kanvas.starty, kanvas.endx-kanvas.startx, kanvas.endy-kanvas.starty), namaTag));
					kanvas.repaint();
				}
			}
		}
		else if(e.getSource() == saveTag)
		{
			saveTag(new File(files[index].getPath()+".tagg"));
			JOptionPane.showMessageDialog(this, "tag sudah disimpan");
		}
	}
	
	public void changeDirectory(File dir)
	{
		if(dir==null)
		{
			//setTitle("[belum ada directory yang dipilih]");
		}
		else
		{
			files = dir.listFiles(new PictureFileFilter());
			if(files.length==0)
			{
				JOptionPane.showMessageDialog(this, "tidak ada image pada direktori tersebut");
			}
			else
			{
				index=0;
				currentDirectory = dir;
				//setTitle(dir.getPath());
				displayPic();
			}
			tagSaved = true;
		}
	}
	
	public void saveTag(File file)
	{
		try
		{
			BufferedWriter write = new BufferedWriter(new FileWriter(file));
			
			Iterator<Tag> it = tag.iterator();
			while(it.hasNext())
			{
				Tag curr =  it.next();
				Rectangle currRect =  curr.rect;
				write.write(currRect.x+";"+currRect.y+";"+currRect.width+";"+currRect.height+";"+curr.str+"\n");
			}
			//write.write("kambing "+new Date());
			write.close();
		}
		catch(Exception e)
		{}
	}
	
	public void loadTag(File file)
	{
		try
		{
			tag.clear();
			BufferedReader read = new BufferedReader(new FileReader(file));
			
			String line;

			while((line=read.readLine())!=null)
			{
				StringTokenizer token = new StringTokenizer(line, ";");
				int x = Integer.parseInt(token.nextToken());
				int y = Integer.parseInt(token.nextToken());
				int width = Integer.parseInt(token.nextToken());
				int height = Integer.parseInt(token.nextToken());
				String theTag  = token.nextToken();
				
				tag.add(new Tag( new Rectangle(x,y,width,height), theTag));
			}
			
			read.close();
		}
		catch(Exception e)
		{
		}
	}
	
	public void displayPic()
	{
		try
		{
			BufferedImage image = ImageIO.read(files[index]);
			loadTag(new File(files[index].getPath()+".tagg"));
			kanvas.setPic(image);
			parent.setTitle(files[index].getPath()+" - TagIt");
			//fileInfo.setText(files[index].getName());
			
			kanvasScroll.revalidate();
			
			//parent.repaint();
			
		}
		catch (Exception e){}
	}
	
	public static void main(String args[])
	{
		JFrame frame = new JFrame("kambing");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,600);
		frame.setVisible(true);
		CyTagger tagger = new CyTagger(null);
		frame.getContentPane().add(tagger);
		frame.validate();
	}
}