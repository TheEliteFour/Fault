package com.xeology.Fault.Program.Commands;

import com.xeology.Fault.Game.Config;
import com.xeology.Fault.Program.Errors;
import com.xeology.Fault.Program.Program;

/**
 *
 * @author ExtendTeam
 */
public class EXTRA {

    public static String process(Program program, String[] command) {

	if (command.length < 4) {
	    return Errors.syntaxError;
	}
	
	int value;
	int value2;
	int value3;	
	    String error = processBiCommand(program, command, 0);
	    try {
		value = Integer.parseInt(error.replace("0x3_", ""));
	    } catch (NumberFormatException ex) {
		return error;
	    }
	    error = processBiCommand(program, command, 1);
	    try {
		value2 = Integer.parseInt(error.replace("0x3_", ""));
	    } catch (NumberFormatException ex) {
		return error;
	    }
	    error = processBiCommand(program, command, 2);
	    try {
		value3 = Integer.parseInt(error.replace("0x3_", ""));
	    } catch (NumberFormatException ex) {
		return error;
	    }
	



	return processBlock(program, command, value, value2, value3);
	

    }

    public static String processBiCommand(Program program, String[] command, int offset) {
	try {



	    int value = Integer.parseInt(command[1 + offset]);
	    
		if (value > Config.memory-1) {
		    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		    return Errors.accessError;
		}
		if (value < 0) {
		    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		    return Errors.accessError;
		}	    
		
	    
	    return Errors.valueError + value;



	} catch (NumberFormatException ex) {



	    if (command[1+offset].toLowerCase().length()<3){
		return Errors.syntaxError;
	    } 
	    String str="";
	    if (command[1+offset].toLowerCase().length()<4){
		str=command[1+offset].toLowerCase().substring(0, 3);
	    }
	    else{
		str=command[1+offset].toLowerCase().substring(0, 4);
	    }	    
	    if (!str.contains("math") && !str.toLowerCase().contains("get") && !str.toLowerCase().contains("rand")) {
		return Errors.syntaxError;
	    }	    
	    String error = CommandHandler.process(program, command[1+offset], true);
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
	    
		if (value > Config.memory-1) {
		    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		    return Errors.accessError;
		}
		if (value < 0) {
		    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		    return Errors.accessError;
		}	    
	    return Errors.valueError + value;

	}
    }

    public static String processBlock(Program program, String[] command, int value, int value2, int value3) {



	if (value > Config.memory-1) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
	    return Errors.accessError;
	}
	if (value < 0) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
	    return Errors.accessError;
	}
	if (value2 > Config.memory-1) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value2 + ".");
	    return Errors.accessError;
	}
	if (value2 < 0) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value2 + ".");
	    return Errors.accessError;
	}
	if (value3 > Config.memory-1) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value3 + ".");
	    return Errors.accessError;
	}
	if (value3 < 0) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value3 + ".");
	    return Errors.accessError;
	}
	String error2 = program.getRound().lockBlock(value, program);
	if (error2.equals(Errors.syntaxError)) {
	    return Errors.syntaxError;
	}
	if (error2.equals(Errors.crashError)) {
	    return Errors.crashError;
	}
	error2 = program.getRound().lockBlock(value2, program);
	if (error2.equals(Errors.syntaxError)) {
	    return Errors.syntaxError;
	}
	if (error2.equals(Errors.crashError)) {
	    return Errors.crashError;
	}
	if (error2.equals(Errors.accessError)) {
	    return Errors.accessError;
	}
	error2 = program.getRound().lockBlock(value3, program);
	if (error2.equals(Errors.syntaxError)) {
	    return Errors.syntaxError;
	}
	if (error2.equals(Errors.crashError)) {
	    return Errors.crashError;
	}
	if (error2.equals(Errors.accessError)) {
	    return Errors.accessError;
	}
	program.setCPUPoints(program.getCPUPoints() +2);
	program.getRound().addLog(program.getName() + " aquired more CPU Points.");
	return Errors.okError;


    }

    
}
