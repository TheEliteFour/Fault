package com.xeology.Fault.GU;

import com.xeology.Fault.Game.Config;
import com.xeology.Fault.Game.Log;
import com.xeology.Fault.Program.Program;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ExtendTeam
 */
public class GUIHandler {

    private Gui gui;
    private List<String> progList = new ArrayList<String>();
    private HashMap<String, Integer> cells;
    private HashMap<String, String> colors;
    private int cell;
    private HashMap<Integer, String> occs;
    private HashMap<Integer, String> memoccs;

    public Gui getGUI() {
	return gui;
    }

    public GUIHandler(Gui g) {
	gui = g;
    }

    public void updateCurrent(int round, int cycle, int cell) {
	BigDecimal base = BigDecimal.valueOf(Config.rounds);
	BigDecimal div = BigDecimal.valueOf(round);
	double percent = div.divide(base, 2, RoundingMode.HALF_UP).doubleValue();
	percent = div.divide(base, 2, RoundingMode.HALF_UP).doubleValue();
	int roundBars = (int) (percent * 25);
	base = BigDecimal.valueOf(Config.cycles);
	div = BigDecimal.valueOf(cycle);
	percent = div.divide(base, 2, RoundingMode.HALF_UP).doubleValue();
	int cyclesBars = (int) (percent * 25);
	base = BigDecimal.valueOf(Config.cells);
	div = BigDecimal.valueOf(cell);
	percent = div.divide(base, 2, RoundingMode.HALF_UP).doubleValue();
	int cellBars = (int) (percent * 25);
	String out = "<html><body><font color=\"#33FF00\">&nbsp;&nbsp;&nbsp;Round&nbsp;&nbsp;&nbsp;[</font><font color=\"red\">";
	for (int ctr = 0; ctr < roundBars; ctr++) {
	    out = out + "|";
	}
	for (int ctr = 0; ctr < (25 - roundBars); ctr++) {
	    out = out + "&nbsp;";

	}
	out = out + "</font><font color =\"#33FF00\">]</font><font color=\"red\">&nbsp;&nbsp;&nbsp;" + round + "</font><font color =\"#33FF00\"><br>";
	out = out + "&nbsp;&nbsp;&nbsp;Cycle&nbsp;&nbsp;&nbsp;[</font><font color=\"red\">";
	for (int ctr = 0; ctr < cyclesBars; ctr++) {
	    out = out + "|";
	}
	for (int ctr = 0; ctr < (25 - cyclesBars); ctr++) {
	    out = out + "&nbsp;";

	}
	out = out + "</font><font color =\"#33FF00\">]</font><font color=\"red\">&nbsp;&nbsp;&nbsp;" + cycle + "</font><font color =\"#33FF00\"><br>";
	out = out + "&nbsp;&nbsp;&nbsp;Cells&nbsp;&nbsp;&nbsp;[</font><font color=\"red\">";
	for (int ctr = 0; ctr < cellBars; ctr++) {
	    out = out + "|";
	}
	for (int ctr = 0; ctr < (25 - cellBars); ctr++) {
	    out = out + "&nbsp;";

	}
	out = out + "</font><font color =\"#33FF00\">]</font><font color=\"red\">&nbsp;&nbsp;&nbsp;" + cell + "</font></body></html>";
	gui.Current.setText(out);


    }

    public void updateOutput(String program, int amount) {
	int val = 0;
	if (cells.containsKey(program)) {
	    val = cells.get(program);
	    cells.remove(program);
	}
	if (val + amount < 0) {
	    val = 0;
	    amount = 0;
	}
	cells.put(program, val + amount);
	updateOutput(progList, cells, false);
    }

    public void updateOutput(List<String> programs, HashMap<String, Integer> cells, boolean newRound) {
	if (newRound) {
	    progList = programs;
	}
	this.cells = (HashMap<String, Integer>) cells.clone();

	String out = "<html><body>";
	for (String program : progList) {
	    out = out + "<font color=\"" + colors.get(program) + "\">";
	    int count = cells.get(program);
	    BigDecimal base = BigDecimal.valueOf(Config.cells);
	    BigDecimal div = BigDecimal.valueOf(count);
	    double percent = div.divide(base, 2, RoundingMode.HALF_UP).doubleValue();
	    int bars = (int) (percent * 25);
	    String string = "&nbsp;&nbsp;&nbsp;" + program + "&nbsp;&nbsp;&nbsp;</font><font color=\"#33ff00\">[</font><font color=\"red\">";
	    for (int ctr = 0; ctr < bars; ctr++) {
		string = string + "|";
	    }
	    for (int ctr = 0; ctr < (25 - bars); ctr++) {
		string = string + "&nbsp;";
	    }
	    out = out + string + "</font><font color=\"#33FF00\">]</font><font color=\"red\">&nbsp;&nbsp;" + count + "<br></font>";
	}
	out = out + "</font></body></html>";
	gui.Output.setText(out);
    }

    public void newColors(List<Program> program) {
	HashMap<String, String> colors = new HashMap<String, String>();
	int ctr = 0;
	for (Program prog : program) {
	    if (ctr == 0) {
		colors.put(prog.getName(), "blue");
	    }
	    if (ctr == 1) {
		colors.put(prog.getName(), "green");
	    }
	    if (ctr == 2) {
		colors.put(prog.getName(), "#FFF700");
	    }
	    if (ctr == 3) {
		colors.put(prog.getName(), "#FFB83D");
	    }
	    if (ctr == 4) {
		colors.put(prog.getName(), "#FF00FB");
	    }
	    ctr++;
	}
	colors.put("Corrupted.xVx.xVx", "red");
	this.colors = colors;
	occs = new HashMap<Integer, String>();
	memoccs = new HashMap<Integer, String>();
    }

    public void updateCPU(String string, int cell) {
	String bar = "<html><body>";
	String color = "";
	for (int ctr = 0; ctr < Config.cells; ctr++) {

	    if (ctr == 0 || ctr == 100 || ctr == 200 || ctr == 300 || ctr == 400 || ctr == 500 || ctr == 600 || ctr == 700 || ctr == 800) {
		color = color + "&nbsp;&nbsp;&nbsp;";
	    }
	    if (ctr == cell) {
		if (string == null) {
		    color = color + "<font color=\"#C4C4C4\">|</font>";
		    if (occs.containsKey(ctr)) {
			occs.remove(ctr);
		    }
		} else {
		    color = color + "<font color=\"" + colors.get(string) + "\">|</font>";
		    occs.put(ctr, string);
		}

	    } else {
		if (occs.containsKey(ctr)) {
		    color = color + "<font color=\"" + colors.get(occs.get(ctr)) + "\">|</font>";
		} else {
		    color = color + "<font color=\"#C4C4C4\">|</font>";
		}
	    }
	    if (ctr == 99 || ctr == 199 || ctr == 299 || ctr == 399 || ctr == 499 || ctr == 599 || ctr == 699 || ctr == 799 || ctr == 899) {
		color = color + "<br>";
	    }
	}
	bar = bar + color + "</body></html>";
	gui.CPU.setText(bar);
	gui.CPU.repaint();
    }

    public void updateCPU(HashMap<Integer, String> cells) {
	String bar = "<html><body>";
	String color = "";
	for (int ctr = 0; ctr < Config.cells; ctr++) {

	    if (ctr == 0 || ctr == 100 || ctr == 200 || ctr == 300 || ctr == 400 || ctr == 500 || ctr == 600 || ctr == 700 || ctr == 800) {
		color = color + "&nbsp;&nbsp;&nbsp;";
	    }
	    if (cells.containsKey(ctr)) {
		color = color + "<font color=\"" + colors.get(cells.get(ctr)) + "\">|</font>";
	    } else {
		color = color + "<font color=\"#C4C4C4\">|</font>";
	    }
	    if (ctr == 99 || ctr == 199 || ctr == 299 || ctr == 399 || ctr == 499 || ctr == 599 || ctr == 699 || ctr == 799 || ctr == 899) {
		color = color + "<br>";
	    }
	}
	bar = bar + color + "</body></html>";
	gui.CPU.setText(bar);
	gui.CPU.repaint();

    }

    public void updateMemory(int block, int value, boolean corrupted, boolean locked) {
	String bar = "<html><body>";
	String color = "";
	for (int ctr = 0; ctr < Config.memory; ctr++) {

	    if (ctr == 0 || ctr == 100 || ctr == 200 || ctr == 300) {
		color = color + "&nbsp;&nbsp;&nbsp;";
	    }


	    if (ctr != block && !memoccs.containsKey(ctr)) {
		color = color + "<font color=\"#C4C4C4\">|</font>";
	    }

	    if (ctr != block && memoccs.containsKey(ctr)) {
		color = color + "<font color=\"" + memoccs.get(ctr) + "\">|</font>";
	    }

	    if (ctr == block && !memoccs.containsKey(ctr)) {
		if (corrupted) {
		    color = color + "<font color=\"red\">|</font>";
		    memoccs.put(ctr, "red");
		}
		if (locked) {
		    color = color + "<font color=\"blue\">|</font>";
		    memoccs.put(ctr, "blue");
		}
		if (!locked && !corrupted && value != 0) {
		    color = color + "<font color=\"#33ff00\">|</font>";
		    memoccs.put(ctr, "#33ff00");
		}
		if (!locked && !corrupted && value == 0) {
		    color = color + "<font color=\"#C4C4C4\">|</font>";
		    memoccs.put(ctr, "#C4C4C4");
		}
	    } else {

		if (ctr == block && memoccs.containsKey(ctr)) {
		    if (corrupted) {
			color = color + "<font color=\"red\">|</font>";
			memoccs.remove(ctr);
			memoccs.put(ctr, "red");
		    }
		    if (locked) {
			color = color + "<font color=\"blue\">|</font>";
			memoccs.remove(ctr);
			memoccs.put(ctr, "blue");
		    }
		    if (!locked && !corrupted && value != 0) {
			color = color + "<font color=\"#33ff00\">|</font>";
			memoccs.remove(ctr);
			memoccs.put(ctr, "#33ff00");
		    }
		    if (!locked && !corrupted && value == 0) {
			color = color + "<font color=\"#C4C4C4\">|</font>";
			memoccs.remove(ctr);
			memoccs.put(ctr, "#C4C4C4");
		    }
		}
	    }


	    if (ctr == 99 || ctr == 199 || ctr == 299) {
		color = color + "<br>";
	    }
	}
	bar = bar + color + "</body></html>";
	gui.Memory.setText(bar);
	gui.Memory.repaint();

    }

    public void updateRoundWinOutput(int round, String winner, int cycles) {
	if (!winner.equals("None")) {
	    String out = "<html><body>&nbsp;&nbsp;&nbsp;<font color=\"#33FF00\">--------------------------------------<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;Round: </font><font color=\"red\">" + round + "</font><font color=\"#33FF00\"><br>";
	    out = out + "&nbsp;&nbsp;&nbsp;--------------------------------------<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;Winner: </font><font color=\"red\">" + winner + "</font><font color=\"#33FF00\"><br>";
	    out = out + "&nbsp;&nbsp;&nbsp;Cells Occupied: </font><font color=\"red\">" + cells.get(winner) + "</font><font color=\"#33FF00\"><br>";
	    out = out + "&nbsp;&nbsp;&nbsp;Cycles: </font><font color=\"red\">" + cycles + "</font><font color=\"#33FF00\"><br>";
	    out = out + "&nbsp;&nbsp;&nbsp;--------------------------------------</font></body></html>";
	    gui.Output.setText(out);
	} else {
	    String out = "<html><body>&nbsp;&nbsp;&nbsp;<font color=\"#33FF00\">--------------------------------------<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;Round: </font><font color=\"red\">" + round + "</font><font color=\"#33FF00\"><br>";
	    out = out + "&nbsp;&nbsp;&nbsp;--------------------------------------<br>";
	    out = out + "<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;</font><font color=\"red\">No Virus Won</font><font color=\"#33FF00\"><br>";
	    out = out + "<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;--------------------------------------</font></body></html>";
	    gui.Output.setText(out);
	}

    }

    public void updateGameWinOutput(int rounds, String winner, int wins) {
	if (!winner.equals("None")) {
	    String out = "<html><body>&nbsp;&nbsp;&nbsp;<font color=\"#33FF00\">--------------------------------------<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;Rounds: </font><font color=\"red\">" + rounds + "</font><font color=\"#33FF00\"><br>";
	    out = out + "&nbsp;&nbsp;&nbsp;--------------------------------------<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;Winners: </font><font color=\"red\">" + winner + "</font><font color=\"#33FF00\"><br>";
	    out = out + "&nbsp;&nbsp;&nbsp;Wins: </font><font color=\"red\">" + wins + "</font><font color=\"#33FF00\"><br>";
	    out = out + "&nbsp;&nbsp;&nbsp;--------------------------------------<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;</font><font color=\"red\">For more details view the </font><font color=\"white\">game.log</font><font color=\"red\"> file.<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;</font><font color=\"red\">Close the window to exit...</font></body></html>";
	    gui.Output.setText(out);
	} else {
	    String out = "<html><body>&nbsp;&nbsp;&nbsp;<font color=\"#33FF00\">--------------------------------------<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;Rounds: </font><font color=\"red\">" + rounds + "</font><font color=\"#33FF00\"><br>";
	    out = out + "&nbsp;&nbsp;&nbsp;--------------------------------------<br>";
	    out = out + "<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;</font><font color=\"red\">No Virus Won</font><font color=\"#33FF00\"><br>";
	    out = out + "<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;--------------------------------------<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;</font><font color=\"red\">For more details view the </font><font color=\"white\">game.log</font><font color=\"red\"> file.<br>";
	    out = out + "&nbsp;&nbsp;&nbsp;</font><font color=\"red\">Close the window to exit...</font></body></html>";
	    gui.Output.setText(out);
	}

    }

    public void updateSettings() {

	String out = "<html><body><font color=\"#33FF00\">&nbsp;&nbsp;&nbsp;Rounds: </font><font color=\"red\">" + Config.rounds + "</font><font color=\"#33FF00\"><br>";
	out = out + "&nbsp;&nbsp;&nbsp;Cells: </font><font color=\"red\">" + Config.cells + "</font><font color=\"#33FF00\"><br>";
	out = out + "&nbsp;&nbsp;&nbsp;Memory: </font><font color=\"red\">" + Config.memory + "</font><font color=\"#33FF00\"><br>";
	out = out + "&nbsp;&nbsp;&nbsp;Cycles: </font><font color=\"red\">" + Config.cycles + "</font></body></html>";
	gui.settings.setText(out);


    }
}
