package com.xeology.Fault.Game;

import com.xeology.Fault.Fault;
import com.xeology.Fault.GU.GUIHandler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ExtendTeam
 */
public class Log {
    
    private static GUIHandler gui;
    
    public static void setGui(GUIHandler guih){
	gui=guih;
    }

    public static void write(String string) {
	File file = new File(Fault.base, "game.log");
	boolean newFile = false;
	if (!file.exists()) {
	    newFile = true;
	    try {
		file.createNewFile();
		//gui.getGUI().Screen.setText("<html><body><font color=\"#33ff00\">");
	    } catch (IOException ex) {
		System.out.println("Failed to create log " + file.getAbsolutePath() + "!");
	    }
	}
	try {
	    FileWriter fstream = new FileWriter(file, true);
	    BufferedWriter out = new BufferedWriter(fstream);
	    gui.getGUI().Screen.append(" "+string+"\n");
	    gui.getGUI().Screen.setCaretPosition(gui.getGUI().Screen.getDocument().getLength());
	    //System.out.println(string);
	    
	    if (!newFile) {
		string = "\n" + string;
	    }
	    out.write(string);
	    out.close();
	} catch (Exception e) {
	    System.err.println("Error: " + e.getMessage());
	}
    }

    public static void deleteOld() {
	File file = new File(Fault.base, "game.log");
	if (file.exists()) {
	    file.delete();

	}
    }
}
