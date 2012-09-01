package com.xeology.Fault.Game;

import com.xeology.Fault.Fault;
import com.xeology.Fault.Util.Configurations.InvalidConfigurationException;
import com.xeology.Fault.Util.Configurations.file.YamlConfiguration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ExtendTeam
 */
public class Config {

    public static String programs = "";
    public static int rounds = 0;
    public static int cells = 0;
    public static int cycles = 0;
    public static int memory = 0;
    public static boolean debug=false;
    public static boolean useGUI=true;
    
    public static File getFile(){	
	File file = new File(Fault.base, "config.yml");
	if (!file.exists()) {	    
	    try {
		file.createNewFile();
	    } catch (IOException ex) {
		System.out.println("Failed to create config " + file.getAbsolutePath() + "!");
	    }
	    YamlConfiguration config = getYaml();
	    config.set("Programs-Directory", new File(Fault.base, "programs").getAbsolutePath());
	    config.set("Rounds", 3);
	    config.set("Max-Cycles", 50);
	    config.set("Cells", 100);
	    config.set("Memory-Blocks", 50);
	    config.set("Use-GUI", true);
	    config.set("Use-Program-List", false);
	    List<String> list=new ArrayList<String>();
	    list.add("virus");
	    list.add("virus3");
	    config.set("Program-List", list);
	    config.set("Debug-Mode", false);
	    save(config);
	}	
	return file;
    }
    
    public static void processDefaults(){
	File dir=new File(Config.programs);
	if (!dir.exists()){
	    dir.mkdir();
	    Fault.copyResourcesToDirectory("Defaults/Programs", programs);
	}
	File file=new File(Fault.base,"Fault-Readme.txt");
	if (!file.exists()){	    
	    Fault.copyResourcesToDirectory("Defaults/ReadMe", Fault.base.getAbsolutePath());
	}
	File version=new File(Fault.base,"Fault-Version.txt");
	if (!version.exists()){	    
	    Fault.copyResourcesToDirectory("Defaults/Versioning", Fault.base.getAbsolutePath());
	}
    }
    
    public static boolean isListEnabled(){
	YamlConfiguration config = getYaml();
	boolean bool = config.getBoolean("Use-Program-List");
	if (bool){
	    if (config.getStringList("Program-List").size()<2){
		System.out.println("Program list MUST have 2 or more programs, defaulting to folder.");
		return false;
	    }
	}
	return bool;
    }
    
    public static File[] getProgramList(){
	YamlConfiguration config = getYaml();
	List<String> list = config.getStringList("Program-List");
	File[] files=new File[list.size()];
	int ctr=0;
	for (String string : list){
	    files[ctr]=new File(programs,string+".bcv");
	    ctr++;
	}
	return files;
	
    }
    
    public static YamlConfiguration getYaml(){
	YamlConfiguration config=new YamlConfiguration();
	try {
	    config.load(getFile());
	} catch (FileNotFoundException ex) {
	    System.out.println("Failed to find config " + getFile().getAbsolutePath() + "!");
	} catch (IOException ex) {
	    System.out.println("Failed to load config " + getFile().getAbsolutePath() + "!");
	} catch (InvalidConfigurationException ex) {
	    System.out.println("Failed to read config " + getFile().getAbsolutePath() + "!");
	}
	return config;
	
    }
    
    public static void save(YamlConfiguration config){
	try {
	    config.save(getFile());
	} catch (IOException ex) {
	    System.out.println("Failed to write config " + getFile().getAbsolutePath() + "!");
	}
    }
    
    public static void loadConfig(){
	YamlConfiguration config = getYaml();
	String string = config.getString("Programs-Directory",new File(Fault.base, "programs").getAbsolutePath());
	if (!(new File(string).exists())){
	    System.out.println("Set directory not found, defaulting.");
	    programs=new File(Fault.base, "programs").getAbsolutePath();
	}else{
	    programs=string;
	}
	int round = config.getInt("Rounds",3);
	if (round<1){
	    round=3;
	    System.out.println("You must have at least 1 Round, setting to 3.");	    	    
	}
	rounds=round;
	int cell = config.getInt("Cells",100);
	if (cell<100){
	    cell=100;
	    System.out.println("You must have at least 100 Cells, setting to 100.");	        
	}
	cells=cell;
	int cycle = config.getInt("Max-Cycles",50);
	if (cycle<50){
	    cycle=50;
	    System.out.println("You must have at least 50 Cycles, setting to 50.");	        
	}
	cycles=cycle;
	int block = config.getInt("Memory-Blocks",50);
	if (block<50){
	    block=50;
	    System.out.println("You must have at least 50 Memory Blocks, setting to 50.");	        
	}
	debug=config.getBoolean("Debug-Mode",false);
	useGUI=config.getBoolean("Use-GUI",true);
	memory=block;
	config.set("Programs-Directory", programs);
	config.set("Rounds", rounds);
	config.set("Max-Cycles",cycles);
	config.set("Cells",cells);
	config.set("Memory-Blocks",memory);
	config.set("Use-GUI",useGUI);
	config.set("Debug-Mode",debug);	
	save(config);
    }

    
}
