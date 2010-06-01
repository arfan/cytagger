import java.io.*;

public class PictureFileFilter implements FilenameFilter
{
	String picext []= {"jpg", "jpeg", "gif", "png", "tiff", "tif", "bmp"} ;
	public boolean accept(File dir,String name)
	{
		int last = name.lastIndexOf(".");
		String ext = name.substring(last+1).toLowerCase() ;
		//System.out.println(name.substring(last+1));
		
		for(int i=0;i<picext.length;i++)
		{
			if(ext.equals(picext[i]))
				return true;
		}
		return false;
	}
	
	public static void main(String args[])
	{
		PictureFileFilter pff = new PictureFileFilter();
		System.out.println(pff.accept(null, "kambing.jpg"));
	}
}