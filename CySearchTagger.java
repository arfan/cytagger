import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.imageio.*;
import java.awt.image.*;

public class CySearchTagger extends JPanel implements ActionListener, ListSelectionListener
{
	JButton setDirectory = new JButton("Choose Directory");
	JFileChooser dirChooser = new JFileChooser();
	File currentDirectory = null;
	
	JPanel atastengah;
	JPanel atas;
	JPanel kiri;
	JButton search;
	
	JTextField query;
	JList list;
	
	File[] files;
	int index;
	
	Box box = Box.createVerticalBox();
	
	KanvasSearch kanvas;
	
	ArrayList<Tag> tag = new ArrayList<Tag>();
	
	Tagger parent;
	public CySearchTagger(Tagger parent)
	{
		//super("kambing");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(800,600);
		//setVisible(true);
		
		this.parent = parent;
		
		setLayout(new BorderLayout());
		
		//add(setDirectory, BorderLayout.WEST);
		
		atastengah = new JPanel();
		atas = new JPanel();
		kiri = new JPanel();
		search = new JButton("Search");
		query = new JTextField(20);
		list = new JList();
		kanvas = new KanvasSearch(this);
		//box.setLayout(new FlowLayout());
		
		//box.add(setDirectory);
		box.add(new JScrollPane(list));
		atastengah.add(query);
		atastengah.add(search);
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		atas.setLayout(new BorderLayout());
		atas.add(atastengah, BorderLayout.CENTER);
		atas.add(setDirectory, BorderLayout.WEST);
		
		add(atas, BorderLayout.NORTH);
		add(box, BorderLayout.WEST);
		
		JScrollPane kanvasScroll = new JScrollPane(kanvas, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		add(kanvasScroll, BorderLayout.CENTER);
		setDirectory.addActionListener(this);
		list.addListSelectionListener(this);
		search.addActionListener(this);
		
		validate();
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
				parent.setTitle(selectedFile.getPath()+" - TagIt");
			}
			else if(returnValue == JFileChooser.CANCEL_OPTION) 
			{
				//System.out.println("di cancel");
			}
		}
		
		if(currentDirectory==null)
			return;
		
		
		if(e.getSource() == search)
		{
			//System.out.println("kambing");
			
			SearchPictureFileFilter spff = new SearchPictureFileFilter(query.getText());
			
			files = currentDirectory.listFiles(spff);
			if(files.length==0)
			{
				JOptionPane.showMessageDialog(this, "tag tidak ditemukan");
			}
			else
			{
				index=0;
				list.setListData(files);
				displayPic();
			}
		}
	}
	
	public void valueChanged(ListSelectionEvent e)
	{
		if (!e.getValueIsAdjusting()) {
			System.out.println(list.getSelectedIndex());
			index = list.getSelectedIndex() ;
			displayPic();
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
			//setTitle(dir.getPath());
			currentDirectory = dir;
			/*//files = dir.listFiles(new PictureFileFilter());
			if(files.length==0)
			{
				JOptionPane.showMessageDialog(this, "tidak ada image pada direktori tersebut");
			}
			else
			{
				index=0;
				currentDirectory = dir;
				setTitle(dir.getPath());
				//displayPic();
				//list.setListData(files);
			}
			//tagSaved = true;*/
		}
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
			System.out.println("tst");
		}
	}
	
	public void displayPic()
	{
		try
		{
			System.out.println("index="+index);
			BufferedImage image = ImageIO.read(files[index]);
			loadTag(new File(files[index].getPath()+".tagg"));
			kanvas.setPic(image);
			//fileInfo.setText(files[index].getName());
			parent.setTitle(files[index].getPath()+" - TagIt");
			
		}
		catch (Exception e){
			System.out.println(e);
		}
	}
	
	public static void main(String args[])
	{
		
		JFrame frame = new JFrame("kambing");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,600);
		frame.setVisible(true);
		CySearchTagger searchTagger = new CySearchTagger(null);
		frame.getContentPane().add(searchTagger);
		frame.validate();
	}
}


