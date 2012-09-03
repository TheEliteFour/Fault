
package com.xeology.Fault.Networking;

import com.xeology.Fault.Fault;
import java.util.UUID;
import jexxus.client.ClientConnection;
import jexxus.common.Delivery;

/**
 *
 * @author ExtendTeam
 */
public class SaltPacket {

    public static boolean send(){
	ClientConnection conn=new ClientConnection(Fault.networkListener,"aesircraft.net",19563);
	conn.connect();
	if (!conn.isConnected()){
	    return false;
	}
	String msg="0x0¤Salt-556655¤";
	UUID uuid=UUID.randomUUID();
	msg=msg+uuid.toString();	
	ConnectionListener.isPacket(uuid.toString(), true);
	conn.send(msg.getBytes(), Delivery.RELIABLE);
	return true;
    }
    
}
