package com.xeology.Fault.Networking;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Hash {
    
    public static String getHash(String string, String salt) {
	MessageDigest digest = null;
	try {
	    digest = MessageDigest.getInstance("SHA-256");
	} catch (NoSuchAlgorithmException ex) {
	}
	digest.reset();
	digest.update(salt.getBytes());
	byte[] input = null;
	try {
	    input = digest.digest(string.getBytes("UTF-8"));
	} catch (UnsupportedEncodingException ex) {
	}
	input = digest.digest(input);

	return byteToHex(input);
    }

    private static String byteToHex(final byte[] hash) {
	Formatter formatter = new Formatter();
	for (byte b : hash) {
	    formatter.format("%02x", b);
	}
	return formatter.toString().substring(0, 20);
    }
    
}
