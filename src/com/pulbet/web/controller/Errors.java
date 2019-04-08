package com.pulbet.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Errors {
	
	private static final List<String> EMPTY_LIST = new ArrayList<String>();
	private Map<String, List<String>> errorMap = null;

	public Errors() {
		errorMap = new HashMap<String, List<String>>();
	}

	public void addError(String clave, String error){

		List<String> errors = errorMap.get(clave);
		
		if(errors == null) {
			errors = new ArrayList<String>();
			errorMap.put(clave, errors);
		} 
		
		errors.add(error);
	}

	public boolean hasErrors() {
		return !errorMap.isEmpty();
	}
	
	public List<String> showErrors(String clave) throws Exception{
		
		List<String> errors = errorMap.get(clave);
		
		if (errors==null) {
			errors = EMPTY_LIST;
		}
		return errors;
		}

}
