package com.pulbet.web.util;

import java.util.Map;

public class ParameterUtils {

	public static final String print(Map<String, String[]> parameters) {
		
		StringBuilder sb = new StringBuilder();
		String[] values = null;
		for(String clave: parameters.keySet()) {
			sb.append(clave).append("={");
			values = parameters.get(clave); 
			for(int i = 0; i<values.length-1;i++) {
				sb.append(values[i]).append(",");
			}
			sb.append(values[values.length-1]);
			sb.append("}");
		}
		return sb.toString();
	}
	
}
