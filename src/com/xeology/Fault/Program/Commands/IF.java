package com.xeology.Fault.Program.Commands;

import com.xeology.Fault.Game.Config;
import com.xeology.Fault.Program.Errors;
import com.xeology.Fault.Program.Program;

/**
 *
 * @author ExtendTeam
 */
public class IF {

    public static String process(Program program, String[] command) {


	if (command.length < 4) {
	    return Errors.syntaxError;
	}
	if (!command[2].toLowerCase().equals("=")&&!command[2].toLowerCase().equals("!")) {
	    return Errors.syntaxError;
	}


	int value;
	int value2;
	String error = processBiCommand(program, command, 0);
	try {
	    value = Integer.parseInt(error.replace("0x3_", ""));
	} catch (NumberFormatException ex) {
	    return error;
	}
	error = processBiCommand(program, command, 2);
	try {
	    value2 = Integer.parseInt(error.replace("0x3_", ""));
	} catch (NumberFormatException ex) {
	    return error;
	}

	program.setIfsDeep(program.getIfsDeep()+1);
	if (command[2].toLowerCase().equals("=")) {
	    return processIf(program, value, value2);
	}else{
	return processNegativeIf(program, value, value2);
	}
	

    }

    public static String processBiCommand(Program program, String[] command, int offset) {
	try {



	    int value = Integer.parseInt(command[1 + offset]);	    
	    return Errors.valueError + value;



	} catch (NumberFormatException ex) {



	    if (command[1 + offset].toLowerCase().length() < 3) {
		return Errors.syntaxError;
	    }
	    String str = "";
	    if (command[1 + offset].toLowerCase().length() < 4) {
		str = command[1].toLowerCase().substring(0, 3);
	    } else {
		str = command[1 + offset].toLowerCase().substring(0, 4);
	    }
	    if (!str.contains("math") && !str.toLowerCase().contains("get") && !str.toLowerCase().contains("rand")) {
		return Errors.syntaxError;
	    }
	    String error = CommandHandler.process(program, command[1 + offset], true);
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

    
    public static String processIf(Program program, int value, int value2) {

	if (value!=value2){	    
	    program.setSkipping(true);
	}

	return Errors.okError;

    }
    
    public static String processNegativeIf(Program program, int value, int value2) {

	if (value==value2){	   
	    program.setSkipping(true);
	}

	return Errors.okError;

    }
}
