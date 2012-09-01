package com.xeology.Fault.Program.Commands;

import com.xeology.Fault.Program.Errors;
import com.xeology.Fault.Program.Program;

/**
 *
 * @author ExtendTeam
 */
public class MOV {

    public static String process(Program program, String[] command) {
	
	if (program.getCPUPoints()<1){
	    return Errors.pointError;
	}
	
	if (command.length<2 || command.length>2){
	    return Errors.syntaxError;
	}
	
	try {
	    
	    
	    
	    int value = Integer.parseInt(command[1]);
	    value=CPURounder.round(program.getCell() +value, program);
	    if (value==program.getCell()){
		program.getRound().addLog(program.getName()+" tried to move onto it's self.");
		return Errors.accessError;
	    }
	    String error2 = program.getRound().setCell(value, program, program, true);
	    program.getRound().setCell(program.getCell(), null, program, true);
	    if (error2.equals(Errors.syntaxError)) {
		return Errors.syntaxError;
	    }
	    if (error2.equals(Errors.crashError)) {
		return Errors.crashError;
	    }	    
	    program.setCell(value);
	    program.setCPUPoints(program.getCPUPoints()-1);
	    return Errors.okError;
	    
	    
	    
	} catch (NumberFormatException ex) {
	    
	    
	    if (command[1].toLowerCase().length()<3){
		return Errors.syntaxError;
	    } 
	    String str="";
	    if (command[1].toLowerCase().length()<4){
		str=command[1].toLowerCase().substring(0, 3);
	    }
	    else{
		str=command[1].toLowerCase().substring(0, 4);
	    }	    
	    if (!str.contains("math") && !str.toLowerCase().contains("get") && !str.toLowerCase().contains("rand")) {
		return Errors.syntaxError;
	    }	    
	    String error = CommandHandler.process(program, command[1], true);
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
	    value=CPURounder.round(program.getCell() +value, program);	    
	    if (value==program.getCell()){
		program.getRound().addLog(program.getName()+" tried to move onto it's self.");
		return Errors.accessError;
	    }
	    String error2 = program.getRound().setCell(value, program, program, true);
	    program.getRound().setCell(program.getCell(), null, program, true);
	    if (error2.equals(Errors.syntaxError)) {
		return Errors.syntaxError;
	    }
	    if (error2.equals(Errors.crashError)) {
		return Errors.crashError;
	    }	  
	    if (error.equals(Errors.accessError)) {
	    return Errors.accessError;
	}
	    program.setCell(value);
	    program.setCPUPoints(program.getCPUPoints()-1);
	    return Errors.okError;
	    
	    
	    
	}




    }
}
