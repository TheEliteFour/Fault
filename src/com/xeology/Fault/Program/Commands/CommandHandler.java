package com.xeology.Fault.Program.Commands;

import com.xeology.Fault.Program.Errors;
import com.xeology.Fault.Program.Program;

/**
 *
 * @author ExtendTeam
 */
public class CommandHandler {

    public static String process(Program program, String command, boolean withinCommand) {
	String[] broken = command.split(" ");
	if (broken.length < 2) {
	    if (broken.length < 2 && !withinCommand) {
		return Errors.syntaxError;
	    }
	    if (command.toLowerCase().length() < 3) {
		return Errors.syntaxError;
	    }
	    String str = "";
	    if (command.toLowerCase().length() < 4) {
		str = command.toLowerCase().substring(0, 3);
	    } else {
		str = command.toLowerCase().substring(0, 4);
	    }
	    if (!str.contains("math") && !str.toLowerCase().contains("get") && !str.toLowerCase().contains("rand")) {
		return Errors.syntaxError;
	    }
	    if (str.contains("rand")) {
		return RAND.process(program, command);
	    }
	    if (str.contains("get")) {
		return GET.process(program, command);
	    }
	    if (str.contains("math")) {
		return MATH.process(program, command);
	    }
	    return Errors.syntaxError;
	}
	if (broken[0].toLowerCase().equals("mov")) {
	    return MOV.process(program, broken);
	}
	if (broken[0].toLowerCase().equals("if")) {
	    return IF.process(program, broken);
	}
	if (broken[0].toLowerCase().equals("copy")) {
	    return COPY.process(program, broken);
	}
	if (broken[0].toLowerCase().equals("corrupt")) {
	    return CORRUPT.process(program, broken);
	}
	if (broken[0].toLowerCase().equals("shift")) {
	    return SHIFT.process(program, broken);
	}
	if (broken[0].toLowerCase().equals("delete")) {
	    return DELETE.process(program, broken);
	}
	if (broken[0].toLowerCase().equals("extra")) {
	    return EXTRA.process(program, broken);
	}
	if (broken[0].toLowerCase().equals("put")) {
	    return PUT.process(program, broken);
	}

	return Errors.syntaxError;
    }
}
