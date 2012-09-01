package com.xeology.Fault.Program.Commands;

import com.xeology.Fault.Program.Errors;
import com.xeology.Fault.Program.Program;
import java.util.Random;

/**
 *
 * @author ExtendTeam
 */
public class RAND {

    public static String process(Program program, String command) {
	
	Random rand = new Random();
	if (command.toLowerCase().equals("rand")) {
	    int val = rand.nextInt(255);
	    return Errors.valueError + val;
	}

	if (command.toLowerCase().replace("rand", "").contains("rand")) {
	    return Errors.syntaxError;
	}
	if (!command.toLowerCase().contains("{") || !command.toLowerCase().contains("}") || !command.toLowerCase().contains(",")) {
	    return Errors.syntaxError;
	}
	String[] vals = command.toLowerCase().replace("rand{", "").replace("}", "").split(",");
	if (vals == null) {
	    return Errors.syntaxError;
	}
	if (vals.length < 2) {
	    return Errors.syntaxError;
	}



	int value;
	int value2;

	String error = processBiCommand(program, vals[0]);
	try {
	    value = Integer.parseInt(error.replace("0x3_", ""));
	} catch (NumberFormatException ex) {
	    return error;
	}
	error = processBiCommand(program, vals[1]);
	try {
	    value2 = Integer.parseInt(error.replace("0x3_", ""));
	} catch (NumberFormatException ex) {
	    return error;
	}


	if (value2>255){
	    return Errors.syntaxError;
	}
	
	if (value<-255){
	    return Errors.syntaxError;
	}
	if (value2<value){
	    return Errors.syntaxError;
	}
	
	int fin=range(value,value2);
	return Errors.valueError+fin;


    }
    
    public static int range(int min, int max) {
                return min + (int) (Math.random() * (max - min));
        }

    public static String processBiCommand(Program program, String command) {
	try {



	    int value = Integer.parseInt(command);
	    return Errors.valueError + value;



	} catch (NumberFormatException ex) {



	    if (command.toLowerCase().length()<3){		
		return Errors.syntaxError;
	    } 
	    String str="";
	    if (command.toLowerCase().length()<4){
		str=command.toLowerCase().substring(0, 3);
	    }
	    else{
		str=command.toLowerCase().substring(0, 4);
	    }	    
	    if (!str.contains("math") && !str.toLowerCase().contains("get")) {		
		return Errors.syntaxError;
	    }	    
	    String error = CommandHandler.process(program, command, true);
	    if (error.equals(Errors.syntaxError)) {
		return Errors.syntaxError;
	    }
	    if (error.equals(Errors.crashError)) {		
		return Errors.crashError;
	    }
	    if (error.equals(Errors.accessError)) {
	    return Errors.accessError;
	}
	    int value = Integer.parseInt(error.replace(Errors.valueError, ""));
	    return Errors.valueError + value;

	}
    }

    
}
