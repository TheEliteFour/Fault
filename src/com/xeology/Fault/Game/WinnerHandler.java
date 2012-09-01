
package com.xeology.Fault.Game;

import com.xeology.Fault.Program.Program;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ExtendTeam
 */
public class WinnerHandler {

    private List<Program> winners=new ArrayList<Program>();
    private List<String> stringWinners=new ArrayList<String>();
    private int wins=0;
    
    public boolean processWinner(List<Program> programs){
	HashMap<String,Program> names=new HashMap<String,Program>();
	for (Program program : programs){
	    if (names.containsKey(program.getName().toLowerCase())){
		continue;
	    }
	    names.put(program.getName().toLowerCase(),program);
	}
	if (names.size()==1){
	    String name=(String) names.keySet().toArray()[0];
	    winners.add(names.get(name));
	    return true;
	}
	if (names.size()==0){	    
	    winners.add(new Program(null,"None"));
	    return true;
	}
	return false;
    }
    
    public  List<Program> getWinner(){
	return winners;
    }
    
    public  String getStringWinners(){
	String winner="";
	List<String> names=new ArrayList<String>();
	    int wctr=0;
	    for (String win : stringWinners){
		if (wctr==0){
		    if (win.toLowerCase().equals("none")){
			continue;
		    }
		    winner=win;
		    names.add(win);
		}
		else{		
		    if (names.contains(win)){
			continue;
		    }
		    if (win.toLowerCase().equals("none")){
			continue;
		    }
		    winner=winner+", "+win;
		    names.add(win);
		}
		wctr++;
	    }
	    if (names.size()==0){
		winner="None";
	    }
	return winner;
    }
    
    public int getWins(){
	return wins;
    }
    
    public void processAverageWinner(List<Program> programs){
	HashMap<String,Integer> wins=new HashMap<String,Integer>();
	for (Program program : programs){
	    int count=1;
	    if (wins.containsKey(program.getName().toLowerCase())){
		count=wins.get(program.getName().toLowerCase())+1;
		wins.remove(program.getName().toLowerCase());
	    }
	    wins.put(program.getName().toLowerCase(), count);
	    if (count==this.wins){
		winners.add(program);
		this.wins=count;
	    }
	    if (count>this.wins){
		winners.clear();
		winners.add(program);
		this.wins=count;
	    }
	}		
    }
    
    public void processStringAverageWinner(List<String> programs){
	HashMap<String,Integer> wins=new HashMap<String,Integer>();
	for (String program : programs){
	    int count=1;
	    if (wins.containsKey(program.toLowerCase())){
		count=wins.get(program.toLowerCase())+1;
		wins.remove(program.toLowerCase());
	    }
	    wins.put(program.toLowerCase(), count);
	    if (count>=this.wins){
		stringWinners.add(program);
		this.wins=count;
	    }
	}		
    }
    
    
    
}
