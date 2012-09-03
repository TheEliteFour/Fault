package com.xeology.Fault.AntiPiracy;

import com.xeology.Fault.Fault;
import com.xeology.Fault.GU.GUIHandler;
import com.xeology.Fault.Game.Log;
import com.xeology.Fault.Networking.ConnectionListener;
import com.xeology.Fault.Networking.KeyPacket;
import com.xeology.Fault.Networking.SaltPacket;
import com.xeology.Fault.Program.Commands.CommandHandler;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author ExtendTeam
 */
public class PiracyHandler {
    
    public static boolean invalid=false;

    public static void process(GUIHandler gui){
	boolean valid=isValid();
	if (!valid){
	    if (invalid){
		Log.write("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		Log.write("It has been detected that you are using an");
		Log.write("invalid key.  If you know your key is valid");
		Log.write("and you believe there is some mistake, please");
		Log.write("visit http://faultthegame.com/support");
		Log.write("");
		Log.write("The game will now run in Demo Mode!");
		Log.write("");
		Log.write("Press the Pause button to continue...");
		Log.write("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		processInvalidateKey();
		gui.getGUI().Pause.setSelected(true);
		CommandHandler.demoMode=true;
	    }else{
		Log.write("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		Log.write("You need to connect to the internet at least");
		Log.write("once to verify that your key is valid.  If");
		Log.write("you believe there is some mistake, please");
		Log.write("visit http://faultthegame.com/support");
		Log.write("");
		Log.write("The game will now run in Demo Mode!");
		Log.write("");
		Log.write("Press the Pause button to continue...");
		Log.write("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		gui.getGUI().Pause.setSelected(true);
		CommandHandler.demoMode=true;
	    }
	}
    }
    
    public static boolean isValid() {
	File file = new File(Fault.base, ".key");
	if (!file.exists()) {
	    return false;
	}
	String key=getKey(file);
	if (key.equals("")){
		return false;
	}
	if (!SaltPacket.send()){
	    return processOffline();
	}
	String salt="";
	int ctr=0;
	while (salt.equals("")){
	    if (ctr>=100000){
		return processOffline();
	    }
	    salt=ConnectionListener.salt;
	    System.out.println("Waiting on salt.");
	    ctr++;
	}
	if (!KeyPacket.send(key)){
	    processOffline();
	}
	boolean valid=false;
	ctr=0;
	while (!valid){
	    if (ctr>=100000){
		return processOffline();
	    }
	    valid=ConnectionListener.received;
	    System.out.println("Waiting on key.");
	    ctr++;
	}
	if (!ConnectionListener.valid){
	    invalid=true;
	    return false;
	}
	if (ConnectionListener.valid){
	    processFirstKey();
	    return true;
	}
	return false;
    }
    
    public static void processFirstKey(){
	Preferences prefs=Preferences.userRoot();
	if (!prefs.get("Vldd¤", "-1").equals("-1")){
	    prefs.put("Vldd¤", "-9354/2");
	}
    }
    
    public static void processInvalidateKey(){
	Preferences prefs=Preferences.userRoot();
	prefs.put("Vldd¤", "-1");	
    }
    
    public static boolean processOffline(){
	Preferences prefs=Preferences.userRoot();
	if (!prefs.get("Vldd¤", "-1").equals("-9354/2")){
	    return false;
	}
	return true;
    }

    public static String getKey(File file) {
	try {
	    FileInputStream fstream = new FileInputStream(file);
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine = null;
	    try {
		strLine = br.readLine();
	    } catch (IOException ex) {
	    }
	    try {
		in.close();
	    } catch (IOException ex) {
	    }
	    if (strLine == null) {
		return "";
	    } else {
		return strLine;
	    }

	} catch (FileNotFoundException ex) {
	}
	return "";
    }
}
