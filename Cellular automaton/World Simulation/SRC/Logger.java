import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Logger {
	
	FileWriter fw;
	File newTextFile;
    BufferedWriter bw;
    PrintWriter out;
    boolean flag = false;
    String fileName = "logger.txt";

    public Logger() {  	}
	
    public Logger(String fileName) {   	this.fileName = fileName;   }

    public void writeTo(String stuff) {
    	
    	out.println("_______________________________________________________");
		out.flush();
    	out.println(stuff);
		out.flush();
    	
    }
    
    public File chooseFolder() {
		
    	JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose log folder:");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// disable the "All files" option.
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) { 
			this.flag=true;
			return chooser.getSelectedFile();
		  }
		else {
			this.flag=false;
			JOptionPane.showMessageDialog(null, "Attantion!\nNo Selection, so no path for outputing. ");
			return null;
		  }
	    }
    
    public void runLog() {
    	
    	//output to default directory
    	File fp = chooseFolder();
    	if(fp!=null)
	    	{
    		this.newTextFile = new File(fp, this.fileName + ".txt");
	    	
	    	try {
				this.fw = new FileWriter(newTextFile);
		    	this.bw = new BufferedWriter(fw);
			    this.out = new PrintWriter(bw);
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
	    	}
    }
}   