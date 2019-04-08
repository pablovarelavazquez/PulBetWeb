package com.pulbet.web.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.util.StringUtils;
import com.pulbet.web.controller.ErrorCodes;
import com.pulbet.web.controller.Errors;

public class ValidationUtils {
	
	private static Logger logger = LogManager.getLogger(ValidationUtils.class);
	
	public ValidationUtils() {
		
	}
	
	public static Integer intValidator(String param, Errors errors, String clave, Boolean mandatory) {

		Integer i = null;
		try {
			if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
				i = Integer.parseInt(param.trim());
				return i;
			} else {
				if(mandatory) {
					errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
					logger.debug("Añadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
				}
				return null;
			}
		} catch (NumberFormatException ex) {
			errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT); 
			logger.debug("Añadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.NOT_VALID_FORMAT);
			return null;
		}

	} 
	
	public static Long longValidator(String param, Errors errors, String clave, Boolean mandatory) {

		Long l = null;
		try {
			
			if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
				l = Long.parseLong(param.trim());
				return l;
			} else {
				if(mandatory) {
					errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
					logger.debug("Añadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
				}
				return null;
			}
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT); 
			logger.debug("Añadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.NOT_VALID_FORMAT);
			return null;
		}

	} 

	public static Date dateValidator(String param, Errors errors, String clave, Boolean mandatory) {

		Date d = null;
		
		try {
			if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
				d =  DateUtils.SIMPLE_DATE_FORMAT.parse(param);

				return d;
			} else {
				if(mandatory) {
					errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
					logger.debug("Añadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
				}
				return null;
			}

		} catch (ParseException ex) {
			ex.printStackTrace();
			errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT); 
			logger.debug("Añadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.NOT_VALID_FORMAT);
			return null;
		}

	}
	
	public static String stringValidator(String param, Errors errors, String clave, Boolean mandatory) {

		String s = null;
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
			s = param.trim();

			return s;
		} else {
			if(mandatory) {
				errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
				logger.debug("Añadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
			}
			return null;
		}
		

	}

}
