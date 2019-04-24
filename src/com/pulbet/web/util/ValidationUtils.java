package com.pulbet.web.util;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.util.StringUtils;
import com.pulbet.web.controller.ErrorCodes;
import com.pulbet.web.controller.Errors;

public class ValidationUtils {

	private static Logger logger = LogManager.getLogger(ValidationUtils.class);

	public ValidationUtils() {

	}

	public static final Pattern EMAIL_PATTERN =  Pattern.compile("^([0-9a-zA-Z]{1,20}+[-._+&]{1,5})*[0-9a-zA-Z]{1,20}+@([-0-9a-zA-Z]{1,15}+[.]{1})+[a-zA-Z]{2,3}$");
	public static final Pattern NAMES_ONLY_LETTERS_PATTERN =  Pattern.compile("^[a-zA-ZÒ—·ÈÌÛ˙¸‹‰ƒÎÀœÔˆ÷¸‹¡…Õ”⁄ ]{1,45}$");
	public static final Pattern NAMES_WITH_NUMBERS_PATTERN =  Pattern.compile("^[A-Za-zÒ—·ÈÌÛ˙¸‹‰ƒÎÀœÔˆ÷¸‹¡…Õ”⁄0-9.@_-~#]{5,45}$");
	public static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{9,30}$");
	public static final Pattern PASS_PATTERN =  Pattern.compile("^(?=.{8,255}$)(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$");
	public static final Pattern LETTER_PATTERN = Pattern.compile("^[a-zA-Z]{1}");
	
	public static String letterValidator(String param, Errors errors, String clave, Boolean mandatory) {
		String s = null; 

		if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
			s = param.trim();

			if(!LETTER_PATTERN.matcher(s).matches()) {
				errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT);
			}

			return s;
		} else {
			if(mandatory) {
				errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
				logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
			}
			return null;
		}
	}

	public static String nifValidator(String param, Errors errors, String clave, Boolean mandatory) {
		String s = null; 

		if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
			s = param.trim();

			if(!isNifNie(s)) {
				errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT);
				return null;
			}

			return s;
		} else {
			if(mandatory) {
				errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
				logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
			}
			return null;
		}

		
	}
	
	public static String ibanValidator(String param, Errors errors, String clave, Boolean mandatory) {
		String s = null; 

		if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
			s = param.trim();

			if(!ibanTest(param)) {
				errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT);
				return null;
			}

			return s;
		} else {
			if(mandatory) {
				errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
				logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
			}
			return null;
		}

		
	}
	
	public static String namesOnlyLettersValidator(String param, Errors errors, String clave, Boolean mandatory) {

		String s = null; 

		if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
			s = param.trim();

			if(!NAMES_ONLY_LETTERS_PATTERN.matcher(s).matches()) {
				errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT);
			}

			return s;
		} else {
			if(mandatory) {
				errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
				logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
			}
			return null;
		}

	}

	public static String namesWithNumbersValidator(String param, Errors errors, String clave, Boolean mandatory) {

		String s = null; 

		if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
			s = param.trim();

			if(!NAMES_WITH_NUMBERS_PATTERN.matcher(s).matches()) {	
				errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT);
			}

			return s;
		} else {
			if(mandatory) {
				errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
				logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
			}
			return null;
		}

	}

	public static String emailValidator(String param, Errors errors, String clave, Boolean mandatory) {

		String s = null;
		if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
			s=param.trim();

			if(!EMAIL_PATTERN.matcher(s).matches()) {
				errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT);
			}
			return s;
		} else {

			if(mandatory) {
				if(mandatory) {
					errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
					logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
				}
			}
			return null;
		}

	}


	public static String passwordValidator (String param, Errors errors, String clave, Boolean mandatory) {

		String s = null;

		if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
			s=param.trim();

			if(!PASS_PATTERN.matcher(s).matches()) {
				errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT);
			}
			return s;
		} else {

			if(mandatory) {
				if(mandatory) {
					errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
					logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
				}
			}
			return null;
		}

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
					logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
				}
				return null;
			}
		} catch (NumberFormatException ex) {
			logger.warn(ex.getMessage(), ex);
			errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT); 
			logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.NOT_VALID_FORMAT);
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
					logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
				}
				return null;
			}
		} catch (NumberFormatException ex) {
			logger.warn(ex.getMessage(), ex);
			errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT); 
			logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.NOT_VALID_FORMAT);
			return null;
		}

	} 

	public static Double doubleValidator(String param, Errors errors, String clave, Boolean mandatory) {

		Double d = null;
		try {

			if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
				d = Double.parseDouble(param.trim());
				return d;
			} else {
				if(mandatory) {
					errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
					logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
				}
				return null;
			}
		} catch (NumberFormatException ex) {
			logger.warn(ex.getMessage(), ex);
			errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT); 
			logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.NOT_VALID_FORMAT);
			return null;
		}

	} 

	
	public static Date dateValidator(String param, Errors errors, String clave, Boolean mandatory) {

		Date d = null;

		try {
			if(!StringUtils.isEmptyOrWhitespaceOnly(param)) {
				d = DateUtils.SHORT_DATE_FORMAT.parse(param);

				return d;
			} else {
				if(mandatory) {
					errors.addError(clave, ErrorCodes.MANDATORY_PARAMETER);
					logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
				}
				return null;
			}

		} catch (ParseException ex) {
			logger.warn(ex.getMessage(), ex);
			errors.addError(clave, ErrorCodes.NOT_VALID_FORMAT); 
			logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.NOT_VALID_FORMAT);
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
				logger.debug("AÒadido error. Clave:{} Codigo_Error:{}", clave, ErrorCodes.MANDATORY_PARAMETER);
			}
			return null;
		}


	}
	
	public static boolean isNifNie(String nif){

		if (nif.toUpperCase().startsWith("X")||nif.toUpperCase().startsWith("Y")||nif.toUpperCase().startsWith("Z"))
			nif = nif.substring(1);

		Pattern nifPattern =Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
		Matcher m = nifPattern.matcher(nif);
		if(m.matches()){
			String letra = m.group(2);
			String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
			int dni = Integer.parseInt(m.group(1));
			dni = dni % 23;
			String reference = letras.substring(dni,dni+1);

			if (reference.equalsIgnoreCase(letra)){
				return true;
			}else{
				return false;
			}
		}
		else
			return false;
	}
	
	 	public static final int IBANNUMBER_MIN_SIZE = 15;
	    public static final int IBANNUMBER_MAX_SIZE = 34;
	    public static final BigInteger IBANNUMBER_MAGIC_NUMBER = new BigInteger("97");
	    
	    

	    public static boolean ibanTest(String accountNumber) {
	        String newAccountNumber = accountNumber.trim();

	        // Check that the total IBAN length is correct as per the country. If not, the IBAN is invalid. We could also check
	        // for specific length according to country, but for now we won't
	        if (newAccountNumber.length() < IBANNUMBER_MIN_SIZE || newAccountNumber.length() > IBANNUMBER_MAX_SIZE) {
	            return false;
	        }

	        // Move the four initial characters to the end of the string.
	        newAccountNumber = newAccountNumber.substring(4) + newAccountNumber.substring(0, 4);

	        // Replace each letter in the string with two digits, thereby expanding the string, where A = 10, B = 11, ..., Z = 35.
	        StringBuilder numericAccountNumber = new StringBuilder();
	        for (int i = 0;i < newAccountNumber.length();i++) {
	            numericAccountNumber.append(Character.getNumericValue(newAccountNumber.charAt(i)));
	        }

	        // Interpret the string as a decimal integer and compute the remainder of that number on division by 97.
	        BigInteger ibanNumber = new BigInteger(numericAccountNumber.toString());
	        return ibanNumber.mod(IBANNUMBER_MAGIC_NUMBER).intValue() == 1;

	    }

}
