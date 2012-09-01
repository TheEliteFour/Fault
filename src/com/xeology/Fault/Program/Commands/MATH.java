package com.xeology.Fault.Program.Commands;

import com.xeology.Fault.Program.Errors;
import com.xeology.Fault.Program.Program;
import java.util.Random;

/**
 *
 * @author ExtendTeam
 */
public class MATH {

    public static String process(Program program, String command) {

	if (command.toLowerCase().replace("math", "").contains("math")) {
	    return Errors.syntaxError;
	}
	if (!command.toLowerCase().contains("(") || !command.toLowerCase().contains(")")|| !command.toLowerCase().contains("_") || (!command.toLowerCase().contains("+") && !command.toLowerCase().contains("*") && !command.toLowerCase().contains("-"))) {
	    return Errors.syntaxError;
	}
	String math = command.toLowerCase().replace("math(", "").replace(")", "");
	String[] vals = math.split("_");
	if (vals == null) {
	   return Errors.syntaxError;
	}
	if (vals.length < 3) {
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
	error = processBiCommand(program, vals[2]);
	try {
	    value2 = Integer.parseInt(error.replace("0x3_", ""));
	} catch (NumberFormatException ex) {
	    return error;
	}
	if (vals[1].equals("-")&&vals[1].equals("+")&&vals[1].equals("*")){
	    return Errors.syntaxError;
	}
	int fin = 0;
	if (vals[1].equals("-")){
	    fin=value-value2;
	}
	if (vals[1].equals("+")){
	    fin=value+value2;
	}
	if (vals[1].equals("*")){
	    fin=value*value2;
	}


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
	    if (!str.contains("rand") && !str.toLowerCase().contains("get")) {
		System.out.println("Test 7 : "+str);
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
