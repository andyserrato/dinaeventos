package org.dinamizadores.dinaeventos.utiles;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConversorNumeroSerie {
	

	public String convertirNumero(String numero) throws NoSuchAlgorithmException{
		
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(numero.getBytes());
        
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
		
		return sb.toString();
	}

}
