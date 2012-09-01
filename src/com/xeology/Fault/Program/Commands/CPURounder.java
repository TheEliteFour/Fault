package com.xeology.Fault.Program.Commands;

import com.xeology.Fault.Game.Config;
import com.xeology.Fault.Program.Program;

/**
 *
 * @author ExtendTeam
 */
public class CPURounder {

    public static int round(int value, Program program) {
	if (value > Config.cells-1) {
	    while (value > Config.cells-1) {
		value = value - Config.cells;
	    }
	    return value;
	}
	if (value < 0) {
	    while (value < 0) {
		value = Config.cells + value;
	    }
	    return value;
	}
	return roundCheck(value);
    }
    
    public static int roundCheck(int value) {
	if (value > Config.cells-1) {
	    while (value > Config.cells-1) {
		value =value - Config.cells;
	    }
	    return value;
	}
	if (value < 0) {
	    while (value < 0) {
		value = Config.cells +  value;
	    }
	    return value;
	}
	return value;
    }
}
