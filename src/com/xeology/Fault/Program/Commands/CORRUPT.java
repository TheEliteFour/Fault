package com.xeology.Fault.Program.Commands;

import com.xeology.Fault.Game.Config;
import com.xeology.Fault.Program.Errors;
import com.xeology.Fault.Program.Program;

/**
 *
 * @author ExtendTeam
 */
public class CORRUPT {

    public static String process(Program program, String[] command) {

	if (program.getCPUPoints() < 4) {
	    return Errors.pointError;
	}

	if (command.length < 3) {
	    return Errors.syntaxError;
	}
	if (!command[1].toLowerCase().equals("cell") && !command[1].toLowerCase().equals("block")) {
	    return Errors.syntaxError;
	}
	if (command[1].toLowerCase().equals("cell")) {
	    return processCell(program, command);
	} else {
	    return processBlock(program, command);
	}

    }

    public static String processBlock(Program program, String[] command) {



	try {



	    int value = Integer.parseInt(command[2]);
	    if (value > Config.memory - 1) {
		program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		return Errors.accessError;
	    }
	    if (value < 0) {
		program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		return Errors.accessError;
	    }
	    String error2 = program.getRound().corruptBlock(value, program);
	    if (error2.equals(Errors.syntaxError)) {
		return Errors.syntaxError;
	    }
	    if (error2.equals(Errors.crashError)) {
		return Errors.crashError;
	    }
	    program.setCPUPoints(program.getCPUPoints() - 4);
	    return Errors.okError;



	} catch (NumberFormatException ex) {



	    if (command[2].toLowerCase().length() < 3) {
		return Errors.syntaxError;
	    }
	    String str = "";
	    if (command[2].toLowerCase().length() < 4) {
		str = command[2].toLowerCase().substring(0, 3);
	    } else {
		str = command[2].toLowerCase().substring(0, 4);
	    }
	    if (!str.contains("math") && !str.toLowerCase().contains("get") && !str.toLowerCase().contains("rand")) {
		System.out.println("Test 3 " + str);
		return Errors.syntaxError;
	    }
	    String error = CommandHandler.process(program, command[2], true);
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
	    if (value > Config.memory - 1) {
		program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		return Errors.accessError;
	    }
	    if (value < 0) {
		program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		return Errors.accessError;
	    }
	    String error2 = program.getRound().corruptBlock(value, program);
	    if (error2.equals(Errors.syntaxError)) {
		return Errors.syntaxError;
	    }
	    if (error2.equals(Errors.crashError)) {
		return Errors.crashError;
	    }
	    if (error.equals(Errors.accessError)) {
		return Errors.accessError;
	    }
	    program.setCPUPoints(program.getCPUPoints() - 4);
	    return Errors.okError;

	}
    }

    public static String processCell(Program program, String[] command) {



	try {



	    int value = Integer.parseInt(command[2]);
	    value = CPURounder.round(program.getCell() + value, program);
	    if (value == program.getCell()) {
		program.getRound().addLog(program.getName() + " tried to Corrupt it's own Cell.");
		return Errors.accessError;
	    }
	    String error2 = program.getRound().corruptCell(value, program);
	    if (error2.equals(Errors.syntaxError)) {
		return Errors.syntaxError;
	    }
	    if (error2.equals(Errors.crashError)) {
		return Errors.crashError;
	    }
	    program.setCPUPoints(program.getCPUPoints() - 4);
	    return Errors.okError;



	} catch (NumberFormatException ex) {



	    if (command[2].toLowerCase().length() < 3) {
		return Errors.syntaxError;
	    }
	    String str = "";
	    if (command[2].toLowerCase().length() < 4) {
		str = command[2].toLowerCase().substring(0, 3);
	    } else {
		str = command[2].toLowerCase().substring(0, 4);
	    }
	    if (!str.contains("math") && !str.toLowerCase().contains("get") && !str.toLowerCase().contains("rand")) {
		return Errors.syntaxError;
	    }
	    String error = CommandHandler.process(program, command[2], true);
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
	    value = CPURounder.round(program.getCell() + value, program);
	    if (value == program.getCell()) {
		program.getRound().addLog(program.getName() + " tried to Corrupt it's own Cell.");
		return Errors.accessError;
	    }
	    String error2 = program.getRound().corruptCell(value, program);
	    if (error2.equals(Errors.syntaxError)) {
		return Errors.syntaxError;
	    }
	    if (error2.equals(Errors.crashError)) {
		return Errors.crashError;
	    }
	    if (error.equals(Errors.accessError)) {
		return Errors.accessError;
	    }
	    program.setCPUPoints(program.getCPUPoints() - 4);
	    return Errors.okError;

	}
    }
}
