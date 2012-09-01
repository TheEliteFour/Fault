package com.xeology.Fault.Program.Commands;

import com.xeology.Fault.Game.Config;
import com.xeology.Fault.Program.Errors;
import com.xeology.Fault.Program.Program;
import java.util.Random;

/**
 *
 * @author ExtendTeam
 */
public class GET {

    public static String process(Program program, String command) {


	if (command.toLowerCase().replace("get", "").contains("get")) {
	    return Errors.syntaxError;
	}
	if (!command.toLowerCase().contains("[") || !command.toLowerCase().contains("]")) {
	    return Errors.syntaxError;
	}
	String val = command.toLowerCase().replace("get[", "").replace("]", "");
	if (val == null) {
	    return Errors.syntaxError;
	}

	int value;


	String error = processBiCommand(program, val);
	try {
	    value = Integer.parseInt(error.replace("0x3_", ""));
	} catch (NumberFormatException ex) {
	    return error;
	}


	if (value > Config.memory-1) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
	    return Errors.accessError;
	}
	if (value < 0) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
	    return Errors.accessError;
	}
	error = program.getRound().getBlock(value, program);
	if (error.equals(Errors.syntaxError)) {
	    return Errors.syntaxError;
	}
	if (error.equals(Errors.crashError)) {
	    return Errors.crashError;
	}
	if (error.equals(Errors.accessError)) {
	    return Errors.accessError;
	}
	int fin = Integer.parseInt(error.replace(Errors.valueError, ""));
	return Errors.valueError + fin;


    }

    public static String processBiCommand(Program program, String command) {
	try {



	    int value = Integer.parseInt(command);
	    return Errors.valueError + value;



	} catch (NumberFormatException ex) {



	    if (command.toLowerCase().length() < 3) {
		return Errors.syntaxError;
	    }
	    String str = "";
	    if (command.toLowerCase().length() < 4) {
		str = command.toLowerCase().substring(0, 3);
	    } else {
		str = command.toLowerCase().substring(0, 4);
	    }
	    if (!str.contains("math") && !str.toLowerCase().contains("rand")) {
		return Errors.syntaxError;
	    }
	    String error = CommandHandler.process(program, command, true);
	    if (error.equals(Errors.syntaxError)) {
		return Errors.syntaxError;
	    }
	    if (error.equals(Errors.crashError)) {
		return Errors.crashError;
	    }
	    int value = Integer.parseInt(error.replace(Errors.valueError, ""));
	    return Errors.valueError + value;

	}
    }
}
