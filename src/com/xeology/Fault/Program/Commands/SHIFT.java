package com.xeology.Fault.Program.Commands;

import com.xeology.Fault.Game.Config;
import com.xeology.Fault.Program.Errors;
import com.xeology.Fault.Program.Program;

/**
 *
 * @author ExtendTeam
 */
public class SHIFT {

    public static String process(Program program, String[] command) {

	if (program.getCPUPoints() < 3) {
	    return Errors.pointError;
	}

	if (command.length < 4) {
	    return Errors.syntaxError;
	}
	if (!command[1].toLowerCase().equals("cell") && !command[1].toLowerCase().equals("block")) {
	    return Errors.syntaxError;
	}


	int value;
	int value2;
	if (command[1].toLowerCase().equals("block")) {
	    String error = processBiCommand(program, command, 0, true);
	    try {
		value = Integer.parseInt(error.replace("0x3_", ""));
	    } catch (NumberFormatException ex) {
		return error;
	    }
	    error = processBiCommand(program, command, 1, true);
	    try {
		value2 = Integer.parseInt(error.replace("0x3_", ""));
	    } catch (NumberFormatException ex) {
		return error;
	    }
	} else {
	    String error = processBiCommand(program, command, 0, false);
	    try {
		value = Integer.parseInt(error.replace("0x3_", ""));
	    } catch (NumberFormatException ex) {
		return error;
	    }
	    error = processBiCommand(program, command, 1, false);
	    try {
		value2 = Integer.parseInt(error.replace("0x3_", ""));
	    } catch (NumberFormatException ex) {
		return error;
	    }
	}



	if (command[1].toLowerCase().equals("cell")) {
	    return processCell(program, command, value, value2);
	} else {
	    return processBlock(program, command, value, value2);
	}

    }

    public static String processBiCommand(Program program, String[] command, int offset, boolean memory) {
	try {



	    int value = Integer.parseInt(command[2 + offset]);
	    if (memory) {
		if (value > Config.memory) {
		    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		    return Errors.accessError;
		}
		if (value < 0) {
		    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		    return Errors.accessError;
		}
	    } else {
		value = CPURounder.round(value, program);
	    }
	    return Errors.valueError + value;



	} catch (NumberFormatException ex) {



	    if (command[2 + offset].toLowerCase().length() < 3) {
		return Errors.syntaxError;
	    }
	    String str = "";
	    if (command[2 + offset].toLowerCase().length() < 4) {
		str = command[2].toLowerCase().substring(0, 3);
	    } else {
		str = command[2 + offset].toLowerCase().substring(0, 4);
	    }
	    if (!str.contains("math") && !str.toLowerCase().contains("get") && !str.toLowerCase().contains("rand")) {
		return Errors.syntaxError;
	    }
	    String error = CommandHandler.process(program, command[2 + offset], true);
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
	    if (memory) {
		if (value > Config.memory) {
		    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		    return Errors.accessError;
		}
		if (value < 0) {
		    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
		    return Errors.accessError;
		}
	    } else {
		value = CPURounder.round(value, program);
	    }
	    return Errors.valueError + value;

	}
    }

    public static String processBlock(Program program, String[] command, int value, int value2) {



	if (value > Config.memory) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
	    return Errors.accessError;
	}
	if (value < 0) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value + ".");
	    return Errors.accessError;
	}
	if (value2 > Config.memory) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value2 + ".");
	    return Errors.accessError;
	}
	if (value2 < 0) {
	    program.getRound().addLog(program.getName() + " tried to access Invalid Memory Block " + value2 + ".");
	    return Errors.accessError;
	}
	String error2 = program.getRound().shiftBlock(value, value2, program);
	if (error2.equals(Errors.syntaxError)) {
	    return Errors.syntaxError;
	}
	if (error2.equals(Errors.crashError)) {
	    return Errors.crashError;
	}
	if (error2.equals(Errors.accessError)) {
	    return Errors.accessError;
	}
	program.setCPUPoints(program.getCPUPoints() - 3);
	return Errors.okError;


    }

    public static String processCell(Program program, String[] command, int value, int value2) {





	value = CPURounder.round(program.getCell() + value, program);
	if (value == program.getCell()) {
	    program.getRound().addLog(program.getName() + " tried to Shift it's own Cell.");
	    return Errors.accessError;
	}
	value2 = CPURounder.round(program.getCell() + value2, program);
	if (value2 == program.getCell()) {
	    program.getRound().addLog(program.getName() + " tried to Shift onto it's own Cell.");
	    return Errors.accessError;
	}
	String error2 = program.getRound().shiftCell(value, value2, program);
	if (error2.equals(Errors.syntaxError)) {
	    return Errors.syntaxError;
	}
	if (error2.equals(Errors.crashError)) {
	    return Errors.crashError;
	}
	if (error2.equals(Errors.accessError)) {
	    return Errors.accessError;
	}
	program.setCPUPoints(program.getCPUPoints() - 3);
	return Errors.okError;



    }
}
