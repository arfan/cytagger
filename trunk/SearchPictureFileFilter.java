import java.io.*;
import java.util.*;

public class SearchPictureFileFilter implements FilenameFilter
{
	String picext []= {"jpg", "jpeg", "gif", "png", "tiff", "tif", "bmp"} ;
	String query;
	
	public SearchPictureFileFilter(String q)
	{
		this.query = q;
	}
	public boolean accept(File dir,String name)
	{
		int last = name.lastIndexOf(".");
		String ext = name.substring(last+1).toLowerCase() ;
		//System.out.println(name.substring(last+1));
		
		for(int i=0;i<picext.length;i++)
		{
			if(ext.equals(picext[i]))
			{
			//	System.out.println(dir.getPath()+ File.separator +name+".tagg");
				if(checkTag(new File(dir.getPath()+ File.separator+name+".tagg")))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkTag(File file)
	{
		try
		{
			BufferedReader read = new BufferedReader(new FileReader(file));
			
			String line;

			while((line=read.readLine())!=null)
			{
				//System.out.println(line);
				StringTokenizer token = new StringTokenizer(line, ";");
				int x = Integer.parseInt(token.nextToken());
				int y = Integer.parseInt(token.nextToken());
				int width = Integer.parseInt(token.nextToken());
				int height = Integer.parseInt(token.nextToken());
				String theTag  = token.nextToken();
				
				//System.out.println(theTag);
				//System.out.println(query);
				if(theTag.contains(query))
				{
					read.close();
					return true;
				}
			}
			
			read.close();
		}
		catch(Exception e)
		{
			System.out.println("tst"+e);
		}
		
		return false;
	}
	
	public static void main(String args[])
	{
		SearchPictureFileFilter pff = new SearchPictureFileFilter("muka");
		System.out.println(pff.accept(new File("."), "kambing.jpg"));
	}
}