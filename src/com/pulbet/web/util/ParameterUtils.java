package com.pulbet.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.pulbet.web.controller.ConstantsValues;

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
	
	public static String URLBuilder (String url, Map<String, String[]> valores) throws UnsupportedEncodingException {
		int cont = 1;
		StringBuilder urlBuilder = new StringBuilder();
		String[] values = null;
		urlBuilder.append(url);
		for(String mapKey: valores.keySet()) {
			if(cont == 1) urlBuilder.append(ConstantsValues.QUESTION_MARK);
			urlBuilder.append(URLEncoder.encode(trimmer(mapKey), ConstantsValues.ENCODING))
				.append(ConstantsValues.EQUAL);
			values = valores.get(mapKey);
				urlBuilder.append(URLEncoder.encode(trimmer(values[0]), ConstantsValues.ENCODING));
			if(cont != valores.size()) {
				urlBuilder.append(ConstantsValues.AMPERSAND_URL);
			}
			cont++;
		}
		return urlBuilder.toString();
	}
	
	public static String trimmer(String param) {
		return param.trim();
	}
	
		
}
