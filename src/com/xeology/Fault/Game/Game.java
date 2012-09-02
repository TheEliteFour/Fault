package com.xeology.Fault.Game;

import com.xeology.Fault.GU.GUIHandler;
import com.xeology.Fault.Program.Program;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ExtendTeam
 */
public class Game {

    private List<Program> programs = new ArrayList<Program>();
    private List<String> winners = new ArrayList<String>();
    private String[] roundWinners;
    private int[] roundCycles;
    private List<String> programNames = new ArrayList<String>();
    private int rounds;

    public Game(int rounds) {
	this.rounds = rounds;
	roundWinners = new String[rounds];
	roundCycles = new int[rounds];
    }

    public void start(GUIHandler gui) {
	if (programs.size() < 2) {
	    Log.write("Game failed to start due to not enough programs! (" + programs.size() + ")");
	    return;
	}
	if (rounds < 1) {
	    Log.write("Game failed to start due to not enough rounds!");
	    return;
	}
	Log.write("");
	Log.write("--------------------------------------");
	Log.write("-----------------Game-----------------");
	Log.write("Rounds: " + rounds);
	String progs = "";
	int c = 0;
	for (Program prog : this.programs) {
	    if (c == 0) {
		progs = prog.getName();
	    } else {
		progs = progs + ", " + prog.getName();
	    }
	    c++;
	}
	Log.write("Programs: " + progs);
	Log.write("--------------------------------------");
	Log.write("--------------------------------------");
	Log.write("");
	for (int ctr = 0; ctr < rounds; ctr++) {
	    Log.write("");
	    Log.write("--------------------------------------");
	    Log.write("Round " + (ctr + 1));
	    Log.write("--------------------------------------");
	    Log.write("");
	    Round round = new Round(programs, gui, ctr + 1);
	    round.start();
	    String winner = "";
	    int wctr = 0;
	    for (Program win : round.getWinner()) {
		if (wctr == 0) {
		    winner = win.getName();
		} else {
		    winner = winner + ", " + win.getName();
		}
		wctr++;
	    }
	    winners.add(winner);
	    roundWinners[ctr] = winner;
	    roundCycles[ctr] = round.getCycles();
	    Log.write("");
	    Log.write("--------------------------------------");
	    Log.write("Winner is " + winner);
	    Log.write("Cycles: " + round.getCycles());
	    Log.write("--------------------------------------");
	    gui.updateRoundWinOutput(ctr + 1, winner, round.getCycles());
	    try {
		if (Config.useGUI) {
		    Thread.sleep(10000);
		}
	    } catch (InterruptedException ex) {
	    }
	}
	WinnerHandler winHandle = new WinnerHandler();
	winHandle.processStringAverageWinner(winners);
	Log.write("");
	Log.write("//////////////////////////////////////");
	Log.write("Winner is " + winHandle.getStringWinners() + " with " + winHandle.getWins() + " wins.");
	Log.write("");
	Log.write("-----------");
	Log.write("   Rounds  ");
	Log.write("-----------");
	for (int rctr = 0; rctr < rounds; rctr++) {
	    Log.write("Round " + (rctr + 1) + ": " + roundWinners[rctr] + " in " + roundCycles[rctr] + " CPU Cycles");
	}
	Log.write("//////////////////////////////////////");
	gui.updateGameWinOutput(rounds, winHandle.getStringWinners(), winHandle.getWins());	
	return;
    }

    public boolean addProgram(Program program) {
	if (programNames.contains(program.getName().toLowerCase())) {
	    Log.write("Didn't add program " + program.getName() + " because it is already entered.");
	    return true;
	}
	if (programNames.size() >= 5) {
	    Log.write("Didn't add program " + program.getName() + " because the queue is full.");
	    return false;
	}
	Log.write("Added program " + program.getName() + ".");
	programNames.add(program.getName());
	programs.add(program);
	return true;
    }
}
