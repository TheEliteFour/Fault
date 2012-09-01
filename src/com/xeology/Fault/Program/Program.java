package com.xeology.Fault.Program;

import com.xeology.Fault.Game.Debug;
import com.xeology.Fault.Program.Commands.CommandHandler;
import com.xeology.Fault.Game.Round;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ExtendTeam
 */
public class Program {

    private Round round;
    private String name;
    private int cell;
    private int cpuPoints;
    private boolean skipping = false;
    private int ifsDeep = 0;
    private List<String> commandLines = new ArrayList<String>();
    private boolean newProgram = false;

    public void setIfsDeep(int ifs) {
	ifsDeep = ifs;
    }

    public int getIfsDeep() {
	return ifsDeep;
    }

    public boolean isSkipping() {
	return skipping;
    }

    public void setSkipping(boolean bool) {
	skipping = bool;
    }

    public void setNewProgram(boolean bool) {
	newProgram = bool;
    }

    public boolean isNew() {
	return newProgram;
    }

    public Program(List<String> commandLines, String name) {
	this.commandLines = commandLines;
	this.name = name;
    }

    public Program clone() {
	Program program = new Program(commandLines, name);
	program.setRound(round);
	program.setNewProgram(true);

	return program;
    }

    public void execute() {
	int ctr = 1;
	for (String command : commandLines) {
	    if (skipping) {
		if (command.toLowerCase().equals("endif")) {
		    if (ifsDeep != 1) {
			ifsDeep--;
			Debug.write("Skipping command " + command + " on Line " + ctr + " from " + name + " on " + cell + " because of trailing IF STATEMENT");
			ctr++;
			continue;
		    }
		    Debug.write("Ending skipping from " + name + " on " + cell + " because of ENDIF on Line " + ctr);
		    skipping = false;
		    ctr++;
		    continue;
		} else {
		    Debug.write("Skipping command " + command + " on Line " + ctr + " from " + name + " on " + cell + " because of trailing IF STATEMENT");
		    ctr++;
		    continue;
		}
	    }
	    if (command.toLowerCase().equals("endif")) {
		if (ifsDeep != 0) {
		    ifsDeep--;
		    Debug.write("Ending IF STATEMENT because of command " + command + " on Line " + ctr + " from " + name + " on " + cell + ".");
		    ctr++;
		    continue;
		}
	    }
	    String error = CommandHandler.process(this, command, false);
	    if (error.equals(Errors.accessError)) {
		round.deleteCellAPI(cell);
		break;
	    }
	    if (error.equals(Errors.crashError)) {
		round.deleteCellAPI(cell);
		break;
	    }
	    if (error.equals(Errors.pointError)) {
		round.addLog(name + " suffered a Out of Points Error on LINE " + ctr + ".");
		round.deleteCellAPI(cell);
		break;
	    }
	    if (error.equals(Errors.syntaxError)) {
		round.addLog(name + " suffered a Syntax Error on LINE " + ctr + ".");
		round.deleteCellAPI(cell);
		break;
	    }
	    Debug.write("Error Code: " + error + " received from running " + command + " from " + name + " on " + cell);
	    ctr++;
	}
	setNewProgram(true);
    }

    public int getCell() {
	return cell;
    }

    public void setCell(int cell) {
	this.cell = cell;
    }

    public int getCPUPoints() {
	return cpuPoints;
    }

    public void setCPUPoints(int points) {
	this.cpuPoints = points;
    }

    public String getName() {
	return name;
    }

    public void setRound(Round round) {
	this.round = round;
    }

    public Round getRound() {
	return round;
    }
}
