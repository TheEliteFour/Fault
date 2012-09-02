package com.xeology.Fault.Game;

import com.xeology.Fault.Fault;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ExtendTeam
 */
public class Log {

    public static void write(String string) {
	File file = new File(Fault.base, "game.log");
	boolean newFile = false;
	if (!file.exists()) {
	    newFile = true;
	    try {
		file.createNewFile();
	    } catch (IOException ex) {
		System.out.println("Failed to create log " + file.getAbsolutePath() + "!");
	    }
	}
	try {
	    FileWriter fstream = new FileWriter(file, true);
	    BufferedWriter out = new BufferedWriter(fstream);
	    System.out.println(string);
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
