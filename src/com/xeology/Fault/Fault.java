/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xeology.Fault;

import com.xeology.Fault.AntiPiracy.PiracyHandler;
import com.xeology.Fault.GU.GUIHandler;
import com.xeology.Fault.GU.Gui;
import com.xeology.Fault.Game.Config;
import com.xeology.Fault.Game.Debug;
import com.xeology.Fault.Game.Game;
import com.xeology.Fault.Game.Log;
import com.xeology.Fault.Networking.ConnectionListener;
import com.xeology.Fault.Program.Commands.CommandHandler;
import com.xeology.Fault.Program.Program;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import jexxus.client.ClientConnectionListener;

/**
 *
 * @author Xeology
 */
public class Fault {

    public static File base;
    public static ConnectionListener networkListener;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	try {
	    base = new File(new File(Fault.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath());
	} catch (URISyntaxException ex) {
	}
	networkListener = new ConnectionListener();
	Log.deleteOld();
	Gui g = new Gui();
	GUIHandler gui = new GUIHandler(g);
	g.setVisible(true);	
	Log.setGui(gui);
	if (CommandHandler.keyMode) {
	    PiracyHandler.process(gui);
	    boolean pause = gui.getGUI().Pause.isSelected();
	    while (pause) {
		pause = gui.getGUI().Pause.isSelected();
		System.out.println("Paused: " + pause);
	    }
	}
	Debug.deleteOld();
	Config.loadConfig();
	Config.processDefaults();
	gui.updateSettings();
	//if (Config.useGUI) {
	//g.setVisible(true);
	//}

	File dir = new File(Config.programs);
	if (!dir.exists()) {
	    Log.write("The directory " + dir.getAbsolutePath() + " does not exist!");
	    return;
	}
	int rounds = Config.rounds;
	Game game = new Game(rounds);
	boolean breakEnter = false;
	File[] files = dir.listFiles();
	if (Config.isListEnabled()) {
	    files = Config.getProgramList();
	}
	for (File file : files) {
	    if (!file.getName().contains(".bcv")) {
		continue;
	    }
	    FileInputStream fstream = null;
	    try {
		fstream = new FileInputStream(file);
	    } catch (FileNotFoundException ex) {
		Log.write("File " + file.getName() + " not found!");
	    }
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;
	    try {
		strLine = br.readLine();
		if (!strLine.contains("#NAME ")) {
		    Log.write("File " + file.getName() + "'s name is not on the first line!");
		    continue;
		}
		String name = strLine.replace("#NAME ", "");
		List<String> commands = new ArrayList<String>();
		while ((strLine = br.readLine()) != null) {
		    if (strLine.contains("#")) {
			continue;
		    }
		    commands.add(strLine);
		}
		Program program = new Program(commands, name);
		if (!game.addProgram(program)) {
		    breakEnter = true;
		}
		in.close();
		if (breakEnter) {
		    break;
		}
	    } catch (IOException ex) {
		Log.write("File " + file.getName() + " caused an IO error!");
	    }
	}
	game.start(gui);
	Log.write("");
	Log.write("");
	Log.write("The game is over.  Check game.log for details on the game.");
	Log.write("");
	if (Config.useGUI) {
	    //Log.write("Press ENTER or close the Graphic Interface to exit...");
	} else {
	    //Log.write("Press ENTER to exit...");
	}
	Scanner sc = new Scanner(System.in);
	while (sc.nextLine() == null);
	gui.getGUI().dispose();

    }

    public static void copyResourcesToDirectory(String jarDir, String destDir) {
	JarFile fromJar = null;
	try {
	    try {
		fromJar = new JarFile(Fault.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	    } catch (IOException ex) {
	    }
	} catch (URISyntaxException ex) {
	}
	for (Enumeration<JarEntry> entries = fromJar.entries(); entries.hasMoreElements();) {
	    JarEntry entry = entries.nextElement();
	    if (entry.getName().startsWith(jarDir + "/") && !entry.isDirectory()) {
		File dest = new File(destDir + "/" + entry.getName().substring(jarDir.length() + 1));
		File parent = dest.getParentFile();
		if (parent != null) {
		    parent.mkdirs();
		}


		FileOutputStream out = null;
		InputStream in = null;
		try {
		    out = new FileOutputStream(dest);
		    in = fromJar.getInputStream(entry);
		    byte[] buffer = new byte[8 * 1024];

		    int s = 0;
		    while ((s = in.read(buffer)) > 0) {
			out.write(buffer, 0, s);
		    }
		} catch (IOException e) {
		    System.out.println("Error extracting defaults!");
		} finally {
		    try {
			in.close();
		    } catch (IOException ignored) {
		    }
		    try {
			out.close();
		    } catch (IOException ignored) {
		    }
		}
	    }
	}
    }
}
