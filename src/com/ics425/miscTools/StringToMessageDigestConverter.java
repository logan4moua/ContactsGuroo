package com.ics425.miscTools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.ics425.config.Config;

public class StringToMessageDigestConverter {
	/**
	 * 
	 * @param password
	 * @return
	 */
	public static String convert(String password){
		return convert(password, Config.mdAlgorithm);
	}
	/**
	 * Convert string to message digest in hex form
	 * @param text			string input
	 * @param algorithm 	such as md2, md5, sha-1, sha-224, sha-256, sha-384, sha-512
	 * @return String of message digest in hex form
	 */
	public static String convert(String text, String algorithm){
		
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sha.reset();
		sha.update(text.getBytes());
		
		return bytesToHex(sha.digest());
		
	}
	
	private static String bytesToHex(byte[] b) {
	      char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
	                         '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	      StringBuffer buf = new StringBuffer();
	      for (int j=0; j<b.length; j++) {
	         buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
	         buf.append(hexDigit[b[j] & 0x0f]);
	      }
	      return buf.toString();
	}

}
