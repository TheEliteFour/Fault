package com.xeology.Fault.Networking;

import java.util.ArrayList;
import java.util.List;
import jexxus.client.ClientConnectionListener;

/**
 *
 * @author ExtendTeam
 */
public class ConnectionListener implements ClientConnectionListener {

    public static String salt = "";
    public static boolean valid=false;
    public static boolean received=false;
    private static List<String> uuids = new ArrayList<String>();

    public synchronized static boolean isPacket(String uuid, boolean input) {
	if (input) {
	    if (!uuids.contains(uuid)) {
		uuids.add(uuid);
	    }
	    return false;
	} else {
	    if (uuids.contains(uuid)) {
		uuids.remove(uuid);
		return true;
	    }
	    return false;

	}
    }
    

    public ConnectionListener() {
    }

    ;
    
    @Override
    public void receive(byte[] data) {
	if (data == null) {
	    return;
	}
	String args = new String(data);
	if (args == null) {
	    return;
	}
	if (!args.contains("¤")) {
	    return;
	}
	String[] split=args.split("¤");
	if (split.length<3){
	    return;
	}
	boolean isPacket=isPacket(split[2],false);
	if (!isPacket){
	    return;
	}
	if (split[0].equals("0x1")){
	    salt=split[1];
	    return;
	}
	if (split[0].equals("0x3")){
	    String bool=split[1];
	    String trueHash=Hash.getHash("true", salt);
	    boolean isValid=false;
	    if (bool.equals(trueHash)){
		isValid=true;
	    }
	    valid=isValid;
	    received=true;
	    return;
	}
    }

    @Override
    public void connectionBroken(boolean forced) {
    }
}
