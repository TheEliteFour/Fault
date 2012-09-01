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
    private int cell;

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
	out=out+"</font><font color =\"#33FF00\">]</font><font color=\"red\">&nbsp;&nbsp;&nbsp;"+ round + "</font><font color =\"#33FF00\"><br>";
	out = out+"&nbsp;&nbsp;&nbsp;Cycle&nbsp;&nbsp;&nbsp;[</font><font color=\"red\">";
	for (int ctr = 0; ctr < cyclesBars; ctr++) {
	    out = out + "|";
	}
	for (int ctr = 0; ctr < (25 - cyclesBars); ctr++) {
	    out = out + "&nbsp;";
	    
	}
	out=out+"</font><font color =\"#33FF00\">]</font><font color=\"red\">&nbsp;&nbsp;&nbsp;"+ cycle + "</font><font color =\"#33FF00\"><br>";
	out = out+"&nbsp;&nbsp;&nbsp;Cells&nbsp;&nbsp;&nbsp;[</font><font color=\"red\">";
	for (int ctr = 0; ctr < cellBars; ctr++) {
	    out = out + "|";
	}
	for (int ctr = 0; ctr < (25 - cellBars); ctr++) {
	    out = out + "&nbsp;";
	    
	}
	out=out+"</font><font color =\"#33FF00\">]</font><font color=\"red\">&nbsp;&nbsp;&nbsp;"+ cell + "</font></body></html>";
	gui.Current.setText(out);
   

    }

    public void updateOutput(List<String> programs, HashMap<String, Integer> cells, boolean newRound) {
	if (newRound) {
	    progList = programs;
	    return;
	}
	this.cells = (HashMap<String, Integer>) cells.clone();
	String out = "<html><body><font color=\"#33FF00\">";
	for (String program : progList) {
	    int count = cells.get(program);
	    BigDecimal base = BigDecimal.valueOf(Config.cells);
	    BigDecimal div = BigDecimal.valueOf(count);
	    double percent = div.divide(base, 2, RoundingMode.HALF_UP).doubleValue();
	    percent = div.divide(base, 2, RoundingMode.HALF_UP).doubleValue();
	    int bars = (int) (percent * 25);
	    String string = "&nbsp;&nbsp;&nbsp;" + program + "&nbsp;&nbsp;&nbsp;[</font><font color=\"red\">";
	    for (int ctr = 0; ctr < bars; ctr++) {
		string = string + "|";
	    }
	    for (int ctr = 0; ctr < (25 - bars); ctr++) {
		string = string + "&nbsp;";
	    }
	    out = out + string + "</font><font color=\"#33FF00\">]</font><font color=\"red\">&nbsp;&nbsp;" + count + "<br></font><font color=\"#33FF00\">";
	}
	out = out + "</font></body></html>";
	gui.Output.setText(out);

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
