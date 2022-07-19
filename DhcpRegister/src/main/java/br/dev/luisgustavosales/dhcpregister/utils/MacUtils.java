package br.dev.luisgustavosales.dhcpregister.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MacUtils {
	
	private List<String> validChars = new ArrayList<>(
			Arrays.asList(
					"0", "1", "2", "3", "4", "5", "6", "7", "8","9", "a", "b", "c", "d", "e", "f"));
	
	public boolean validateMac(String mac) {
		
		mac = mac.toLowerCase();
		
		
		// Tamanho inválido
		if(!(mac.length() == 12)) {
			return false;
		}
		
		System.out.println(mac);
		
		
		for( String m: mac.split("")) {
			// Caracter inválido
			
			boolean valid = validChars.stream().anyMatch(m::equals);

			if (!valid) {
				return false;
			}
		}
		
		
		return true;
	}

	public String formatMac(String mac) {
		
		String formattedMac = "";
		
		mac = mac.toLowerCase();
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < mac.length(); i++) {
			// 01:23:45:67:89:ab
			if(i%2 == 0) {
				if (i != 0 ) {
					sb.append(":"+mac.charAt(i));					
				} else {
					sb.append(mac.charAt(i));
				}
			} else {
				sb.append(mac.charAt(i));
			}
			
		}
		
		formattedMac = sb.toString();
		
		return formattedMac;
	}
}
