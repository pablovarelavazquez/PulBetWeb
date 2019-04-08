package com.pulbet.web.util;

import javax.servlet.http.HttpServletRequest;

public class ParamsUtils {

	public static final String getParameter(HttpServletRequest request, String name) {		
		String value = (String) request.getParameter(name);							
		if (value==null) value = "";
		return value;
	}
}
