package com.xeology.Fault.Game;

import com.xeology.Fault.GU.GUIHandler;
import com.xeology.Fault.Program.Errors;
import com.xeology.Fault.Program.Program;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ExtendTeam
 */
public class Round {

    private Program[] cells = new Program[Config.cells];
    private boolean[] corruptedCells = new boolean[Config.cells];
    private int[] blocks = new int[Config.memory];
    private boolean[] corruptedBlocks = new boolean[Config.memory];
    private boolean[] lockedBlocks = new boolean[Config.memory];
    private int cycles;
    private GUIHandler gui;
    private int round;
    private List<Program> programList;
    //private List<String> logs = new ArrayList<String>();
    private List<Program> winners = new ArrayList<Program>();

    ;

    public Round(List<Program> programs, GUIHandler gui, int round) {
	programList = programs;
	this.gui = gui;
	this.round = round;
	List<Integer> takenCells = new ArrayList<Integer>();
	Random rand = new Random();
	for (Program program : programs) {
	    int cell = rand.nextInt(Config.cells - 1);
	    while (takenCells.contains(cell)) {
		cell = rand.nextInt(Config.cells - 1);
	    }
	    cells[cell] = program;
	    program.setCell(cell);
	    Log.write(program.getName() + " was given Cell " + cell);
	    program.setRound(this);
	}
    }

    public void addLog(String string) {
	//Debug.write(string+" next line will explain more.");
	Log.write(string);
    }

    public String setCell(int cell, Program program, Program issueing, boolean mov) {
	if (program == null && !mov && cells[cell] != null) {
	    Log.write(cells[cell].getName() + " was destroyed in Cell " + cell + " by " + issueing.getName() + " deleting it.");
	}
	if (program != null && cells[cell] != null) {
	    if (mov) {
		Log.write(cells[cell].getName() + " was destroyed in Cell " + cell + " by " + issueing.getName() + " overwriting it.");
	    } else {
		Log.write(cells[cell].getName() + " was destroyed in Cell " + cell + " by " + issueing.getName() + " copying over it.");
	    }

	}
	if (program != null && cells[cell] == null && mov) {
	    Log.write(program.getName() + " Moved to Cell " + cell);
	}
	if (program != null && cells[cell] == null && !mov) {
	    Log.write(program.getName() + " Copied to Cell " + cell);
	}
	if (corruptedCells[cell]) {
	    Log.write(program.getName() + " accessed Corrupted Cell " + cell + ".");
	    corruptedCells[cell] = false;
	    setCell(issueing.getCell(), null, issueing, false);
	    return Errors.crashError;
	}

	cells[cell] = program;
	return Errors.okError;
    }

    public String corruptBlock(int block, Program program) {
	if (lockedBlocks[block]) {
	    Log.write(program.getName() + " tried to access locked Memory Block " + block + ".");
	    return Errors.accessError;
	}
	if (corruptedBlocks[block]) {
	    Log.write(program.getName() + " accessed Corrupted Memory Block " + block + ".");
	    corruptedBlocks[block] = false;
	    return Errors.crashError;
	}
	Log.write(program.getName() + " Corrupted Memory Block " + block + ".");
	corruptedBlocks[block] = true;
	return Errors.okError;
    }

    public String lockBlock(int block, Program program) {
	if (lockedBlocks[block]) {
	    Log.write(program.getName() + " tried to access locked Memory Block " + block + ".");
	    return Errors.accessError;
	}
	if (corruptedBlocks[block]) {
	    Log.write(program.getName() + " accessed Corrupted Memory Block " + block + ".");
	    corruptedBlocks[block] = false;
	    return Errors.crashError;
	}
	Log.write(program.getName() + " Locked Memory Block " + block + " for a CPU Cache.");
	lockedBlocks[block] = true;
	return Errors.okError;
    }

    public String setBlock(int block, int value, Program program) {
	if (lockedBlocks[block]) {
	    Log.write(program.getName() + " tried to access locked Memory Block " + block + ".");
	    return Errors.accessError;
	}
	if (corruptedBlocks[block]) {
	    Log.write(program.getName() + " accessed Corrupted Memory Block " + block + ".");
	    corruptedBlocks[block] = false;
	    return Errors.crashError;
	}
	Log.write(program.getName() + " Put " + value + " into Memory Block " + block + ".");
	blocks[block] = value;
	return Errors.okError;
    }

    public List<Program> getWinner() {
	return winners;
    }

    public String getBlock(int block, Program program) {
	if (lockedBlocks[block]) {
	    Log.write(program.getName() + " tried to access locked Memory Block " + block + ".");
	    return Errors.accessError;
	}
	if (corruptedBlocks[block]) {
	    Log.write(program.getName() + " accessed Corrupted Memory Block " + block + ".");
	    corruptedBlocks[block] = false;
	    return Errors.crashError;
	}
	Log.write(program.getName() + " Got " + blocks[block] + " from Memory Block " + block + ".");
	return Errors.valueError + blocks[block];
    }

    public String deleteBlock(int block, Program program) {
	if (lockedBlocks[block]) {
	    Log.write(program.getName() + " tried to access locked Memory Block " + block + ".");
	    return Errors.accessError;
	}
	if (corruptedBlocks[block]) {
	    Log.write(program.getName() + " accessed Corrupted Memory Block " + block + ".");
	    corruptedBlocks[block] = false;
	    return Errors.crashError;
	}
	Log.write(program.getName() + " Deleted Memory Block " + block + ".");
	blocks[block] = 0;
	return Errors.okError;
    }

    public String shiftBlock(int block, int block2, Program program) {
	if (lockedBlocks[block]) {
	    Log.write(program.getName() + " was tried to access locked Memory Block " + block + ".");
	    return Errors.accessError;
	}
	if (lockedBlocks[block2]) {
	    Log.write(program.getName() + " was tried to access locked Memory Block " + block2 + ".");
	    return Errors.accessError;
	}

	Log.write(program.getName() + " Shifted Memory Block " + block + " to " + block2 + ".");
	corruptedBlocks[block2] = corruptedBlocks[block];
	blocks[block2] = blocks[block];
	return Errors.okError;
    }

    public int getCycles() {
	return cycles;
    }

    public String shiftCell(int cell, int cell2, Program program) {
	if (corruptedCells[cell] && cells[cell2] != null) {
	    Log.write(program.getName() + " Shifted Corrupted Cell " + cell + " onto " + cells[cell2].getName() + " in Cell " + cell2 + ".");
	} else {
	    if (corruptedCells[cell2] && cells[cell] != null) {
		Log.write(program.getName() + " Shifted " + cells[cell2].getName() + " in Cell " + cell + "onto Corrupted Cell " + cell2 + ".");
	    } else {
		Log.write(program.getName() + " Shifted Cell " + cell + " to " + cell2 + ".");
	    }
	}


	corruptedCells[cell2] = corruptedCells[cell];
	cells[cell2] = cells[cell];
	if (cells[cell2] != null) {
	    cells[cell2].setCell(cell2);
	}
	return Errors.okError;
    }

    public String corruptCell(int cell, Program program) {
	if (cells[cell] != null) {
	    Log.write(program.getName() + " Corrupted " + cells[cell].getName() + "'s Cell " + cell + ".");
	    cells[cell] = null;
	    return Errors.okError;
	}
	if (corruptedCells[cell]) {
	    Log.write(program.getName() + " accessed Corrupted Cell " + cell + ".");
	    corruptedCells[cell] = false;
	    return Errors.crashError;
	}
	Log.write(program.getName() + " Corrupted Cell " + cell + ".");
	corruptedCells[cell] = true;
	return Errors.okError;
    }

    public String deleteCell(int cell, Program program) {
	if (cells[cell] != null) {
	    Log.write(program.getName() + " deleted " + cells[cell].getName() + "'s Cell " + cell + ".");
	    cells[cell] = null;
	    return Errors.okError;
	}
	if (corruptedCells[cell]) {
	    Log.write(program.getName() + " accessed Corrupted Cell " + cell + ".");
	    corruptedCells[cell] = false;
	    return Errors.crashError;
	}
	Log.write(program.getName() + " Deleted Empty Cell " + cell + ".");
	return Errors.okError;
    }

    public String deleteCellAPI(int cell) {
	cells[cell] = null;
	return Errors.okError;
    }

    public void start() {
	List<String> stringPrograms = new ArrayList<String>();
	HashMap<String, Integer> counts = new HashMap<String, Integer>();
	for (Program prog : programList) {
	    stringPrograms.add(prog.getName());
	}
	gui.updateOutput(stringPrograms, null, true);
	while (true) {
	    cycles = cycles + 1;
	    if (cycles > Config.cycles) {
		Log.write("");
		Log.write("* Max CPU Cycles hit *");
		Log.write("");
		WinnerHandler winner = new WinnerHandler();
		winner.processWinner(new ArrayList<Program>());
		this.winners = winner.getWinner();
		cycles--;
		break;

	    }
	    gui.updateCurrent(round, cycles);
	    Log.write("");
	    Log.write("* Beggining CPU Cycle " + cycles + " *");
	    Log.write("");
	    List<Program> programs = new ArrayList<Program>();
	    for (Program program : cells) {
		if (program == null) {
		    continue;
		}
		if (program.isNew()) {
		    continue;
		}
		Log.write("- Executing " + program.getName() + " in Cell " + program.getCell());
		program.setCPUPoints(15);
		program.execute();
		for (int ctr = 0; ctr < Config.memory; ctr++) {
		    if (lockedBlocks[ctr]) {
			lockedBlocks[ctr] = false;
		    }
		}
	    }
	    for (Program program : cells) {
		if (program == null) {
		    continue;
		}
		if (program.isNew()) {
		    program.setNewProgram(false);
		}
		if (counts.containsKey(program.getName())) {
		    int count = counts.get(program.getName());
		    count++;
		    counts.remove(program.getName());
		    counts.put(program.getName(), count);
		} else {
		    counts.put(program.getName(), 1);
		}
		programs.add(program);
	    }
	    for (String prog : stringPrograms) {
		if (!counts.containsKey(prog)) {
		    counts.put(prog, 0);
		}
	    }
	    gui.updateOutput(stringPrograms, counts, false);
	    counts.clear();
	    WinnerHandler winner = new WinnerHandler();
	    if (winner.processWinner(programs)) {
		this.winners = winner.getWinner();
		break;
	    }
	}
    }
}
