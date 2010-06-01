import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

public class Tagger extends JFrame implements ChangeListener 
{
	CyTagger tagger;
	CySearchTagger searchTagger;
	
	JTabbedPane tabPane = new JTabbedPane();
	
	//JPanel atas = new JPanel();
	//JButton setDirectory = new JButton("Pilih Direktori");

	public Tagger()
	{
		super("TagIt");
		
		tagger = new CyTagger(this);
		searchTagger = new CySearchTagger(this);
	
		
		tabPane.add("Tagging",tagger);
		tabPane.add("Searching", searchTagger);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setVisible(true);
		
		setLayout(new BorderLayout());
		
		//atas.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//atas.add(setDirectory);
		getContentPane().add(tabPane, BorderLayout.CENTER);
		//getContentPane().add(atas, BorderLayout.NORTH);
		
		tabPane.addChangeListener(this);
		validate();
	}
	
	public void stateChanged(ChangeEvent e)
	{
		System.out.println(e);
		setTitle("TagIt");
	}
	public static void main(String args[])
	{
		new Tagger();
	}
}