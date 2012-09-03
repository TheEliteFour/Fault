
package com.xeology.Fault.Networking;

import com.xeology.Fault.Fault;
import java.util.UUID;
import jexxus.client.ClientConnection;
import jexxus.common.Delivery;

/**
 *
 * @author ExtendTeam
 */
public class KeyPacket {

    public static boolean send(String key){
	key=key.toLowerCase();
	ClientConnection conn=new ClientConnection(Fault.networkListener,"aesircraft.net",19563);
	conn.connect();
	if (!conn.isConnected()){
	    return false;
	}
	String msg="0x2¤";
	msg=msg+key+"¤"+Hash.getHash(key, ConnectionListener.salt)+"¤";	
	UUID uuid=UUID.randomUUID();
	msg=msg+uuid.toString();	
	ConnectionListener.isPacket(uuid.toString(), true);
	conn.send(msg.getBytes(), Delivery.RELIABLE);
	return true;
    }
    
}
