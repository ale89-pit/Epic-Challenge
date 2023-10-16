package com.biblioTech.Security.configurator;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import jakarta.persistence.AttributeConverter;

import jakarta.persistence.Converter;

// @Converter(autoApply = true)
@Converter
public class SecretCodeConvert implements AttributeConverter<String, String> {

	// Character[] arrChar = {'T','F','!','#','Q','§','P','a','ò','<','-','v'};
	// Integer[] arrInt = {8,6,9,1,2,4,7,0,3,5,7,1};

	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final byte[] KEY = "supercalifragili".getBytes(StandardCharsets.UTF_8);
	private String initVector = "1452874596321455";
	IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));

	@Override
	public String convertToDatabaseColumn(String attribute) {
		Key key = new SecretKeySpec(KEY, "AES");
		try {
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE, key, iv);
			return Base64.getEncoder().encodeToString((c.doFinal(attribute.getBytes())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// String result = "";
		// if(attribute == null) {
		// return result;
		// }
		// for(Character c : attribute.toCharArray()) {
		//
		// if(Character.isDigit(c)) {
		// String s = c.toString();
		// System.out.println("numero"+ s);
		// int num = Integer.parseInt(s);
		// System.out.println("numero " +num);
		// result += arrChar[num];
		// System.out.println(result);
		//
		// }else {
		// String s = c.toString();
		// System.out.println("lettera" + s);
		// int num = s.charAt(c);
		// System.out.println(num);
		// result += arrInt[attribute.indexOf(c)];
		// System.out.println(result);
		// }
		// }
		// return result;
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		Key key = new SecretKeySpec(KEY, "AES");

		try {

			final Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, key, iv);
			String decrypted = new String(c.doFinal(Base64.getDecoder().decode(dbData)));
			return decrypted;
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}

}
