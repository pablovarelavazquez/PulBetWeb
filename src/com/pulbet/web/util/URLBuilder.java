package com.pulbet.web.util;

import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Encapsula una URL y permite añadir / reemplazar sucesivamente 
 * par&aacute;metros a esa URL.
 * <p>
 * Para obtener la URL final el m&eacute;todo toString() ha sido 
 * convenientemente redefinido.
 *
 */
public class URLBuilder  {

	/** Log */
	private static final Logger logger = LogManager.getLogger(HttpUtils.class);
	
	/** Delimitador de inicio de la linea de parametros de la URL */
	public static final String URL_PARAMBEGIN_MARK = "?";
	
	/** Separador de pares calve/valor */
	public static final String URL_KEYVALUE_SEPARATOR = "=";
	 
	/** Separador de parametros de la URL */
	public static final String URL_PARAM_SEPARATOR = "&"; 
	
	private String paramBeginMark = URL_PARAMBEGIN_MARK;
	private String keyValueSeparator = URL_KEYVALUE_SEPARATOR;
	private String paramSeparator = URL_PARAM_SEPARATOR;
	
	private StringBuffer urlsb = null;
			
	/**
	 * Constructor por defecto
	 */
	public URLBuilder() {
		reset("");
	}
	
	/**
	 * Asigna a la url la cadena que se le pasa como parametro
	 * @param url cadena con la url
	 */
	public URLBuilder(String url) {		
		reset(url);		
	}
	
	/**
	 * Asigna a la url la cadena que se le pasa como parametro, modificada con los pares clave/valor que contiene el mapa
	 * @param url cadena con la url
	 * @param params mapa con los pares clave/valor que se añadiran a la nueva url
	 */
	public URLBuilder(String url, Map<String, String[]> params) {
		reset(url, params);
	}
	
	/**
	 * Asigna a la url la cadena que se le pasa como parametro, modificada con los pares clave/valor que contiene el mapa
	 * y asigna el valor a los separadores y delimitadores
	 * @param url cadena con la url
	 * @param params params mapa con los pares clave/valor que se añadiran a la nueva url
	 * @param paramBeginMark cadena con el delimitador de inicio
	 * @param keyValueSepator cadena con el separador de los pares clave/valor
	 * @param paramSeparator cadena con el separador de los parametros
	 */
	public URLBuilder(String url, Map<String, String[]> params, 
			String paramBeginMark, String keyValueSepator, 
			String paramSeparator) {
		
		this(url, params);
		this.paramBeginMark = paramBeginMark;
		this.keyValueSeparator = keyValueSepator;
		this.paramSeparator = paramSeparator;
	}	
	
	/**
	 * Modifica la url actual con la nueva url que se pasa como parametro
	 * @param newUrl la nueva url
	 */
	public void reset(String newUrl) {
		urlsb = new StringBuffer(newUrl);
	}
	
	/**
	 * Modifica la url actual con la nueva url que se pasa como parametro, añadiendole los pares clave/valor que contiene el mapa
	 * @param newUrl la nueva url
	 * @param newParameterMap mapa con los pares clave/valor que se añadiran a la nueva url
	 */
	public void reset(String newUrl, Map<String, String[]> newParameterMap) {
		reset(newUrl);
		addParameterMap(newParameterMap, false);
	}	

	/**
	 * Añade un nuevo par clave/valor a la url, comprueba si es el primer par clave/valor
	 * @param key nombre de la clave que se añadir� a la url
	 * @param value value valor de la clave que ve a añadir
	 * @param isFirst booleano que indica si es el primer par clave/valor que se añade a la url
	 */
	private void appendParam(String key, String value, boolean isFirst) {
		
		urlsb.append(isFirst ? this.paramBeginMark : this.paramSeparator);
		addParam(key, value);
	}
	
	/**
	 * Añade un nuevo par clave/valor a la url
	 * @param key nombre de la clave que se añadir� a la url
	 * @param value valor de la clave que ve a añadir
	 */
	private void addParam(String key, String value) {
		this.urlsb.append(key).append(this.keyValueSeparator).append(value);		
	}
	

	/**
	 * Añade un nuevo par clave/valor a la url, si existe el par y replaceIfExists es true lo remplaza
	 * @param parameterMap mapa con los pares clave/valor
	 * @param replaceIfExists booleano que indica si se debe sustituir el par en el caso de que exista
	 */
	public void addParameterMap(Map<String, String[]> parameterMap, boolean replaceIfExists) {
		addParameterMap(parameterMap, replaceIfExists, false);
	}
	

	/**
	 * Añade un todos los pares clave/valor que contiene el mapa a la url, si existe el par que se añadiendo
	 * y replaceIfExists es true lo remplaza, si encode es true codifica la url
	 * @param parameterMap mapa con los pares clave/valor
	 * @param replaceIfExists booleano que indica si se debe sustituir el par en el caso de que exista
	 * @param encode bolenano que indica si de debe codificar la url
	 */
	public void addParameterMap(Map<String, String[]> parameterMap, boolean replaceIfExists, boolean encode) {
		
		if (parameterMap != null) {
			
			for (String key: parameterMap.keySet()) {
	
				// chapucilla, para soportar solo parametros univaludados
				String[] values = parameterMap.get(key);
				if (values!=null) {					
					addParam(key, values[0], replaceIfExists, encode);
				} else {
					addParam(key, null);
				}
			}
			
		}
	}	
	
	
	
	/**
	 * Añade un nuevo par clave/valor a la url, si existe el par y replaceIfExists es true lo remplaza
	 * @param key nombre de la clave que se añadir� a la url
	 * @param value valor de la clave que ve a añadir
	 * @param replaceIfExists booleano que indica si se debe sustituir el par en el caso de que exista
	 */
	public void addParam(String key, String value, boolean replaceIfExists) {

		addParam(key, value, replaceIfExists, false);
	}
	
	/**
	 * Añade un nuevo par clave/valor a la url, si existe el par y replaceIfExists es true lo remplaza, si encode es true codifica la url
	 * @param key nombre de la clave que se añadir� a la url
	 * @param value valor de la clave que ve a añadir
	 * @param replaceIfExists booleano que indica si se debe sustituir el par en el caso de que exista
	 * @param encode bolenano que indica si de debe codificar la url
	 */
	public void addParam(String key, String value, boolean replaceIfExists, boolean encode) {
		
		int indexOfParamSeparator = urlsb.indexOf(this.paramBeginMark);
		String url = new String(value);
		
		
		if (encode) {
			url = HttpUtils.encodeUrl(value);
		}
		
		if (indexOfParamSeparator >= 0) {

			int paramIndex = urlsb.indexOf(key+keyValueSeparator);			
			boolean existsParam = paramIndex > 0 
				&& paramIndex > indexOfParamSeparator;
						
			if (existsParam && replaceIfExists) {
				replaceParam(key, value);
			} else if (indexOfParamSeparator < urlsb.length()-1) {
				appendParam(key, url, false);
			} else {
				addParam(key, url);
			}
		} else {
			appendParam(key, url, true);
		}		
		
	}
	
	
	/**
	 * Elimina una clave de la url
	 * @param key nombre de la clave que va a ser eliminada
	 */
	public void deleteParam(String key) {
		replaceParamBlock(key, "");
	}
	
	/**
	 * Remplaza una el valor de una clave en la url
	 * @param key clave cuyo valor sera remplazado
	 * @param value nuevo valor de la clave
	 */
	public void replaceParam(String key, String newValue) {
		
		replaceParamBlock(key, 
				new StringBuffer(key).append(this.keyValueSeparator).
					append(newValue).toString());
	}	
	
	/**
	 * Remplaza una clave y su valor por una cadena con el nombre de dicha clave y un nuevo valor
	 * @param key clave que va a ser remplazada
	 * @param replaceStr nombre de la clave y nuevo valor
	 */
	private void replaceParamBlock(String key, String replaceStr) {
			
		int keyIndex = urlsb.indexOf(key);
		if (keyIndex >= 0) {
			int nextParamIndex = urlsb.indexOf(this.paramSeparator, keyIndex);
			if (nextParamIndex < 0) {
				nextParamIndex = urlsb.length();
			}
			
			urlsb.replace(keyIndex, nextParamIndex, replaceStr); 
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		return urlsb.toString();		
	}

	public String getKeyValueSeparator() {
		return keyValueSeparator;
	}

	public void setKeyValueSeparator(String keyValueSeparator) {
		this.keyValueSeparator = keyValueSeparator;
	}

	public String getParamSeparator() {
		return paramSeparator;
	}

	public void setParamSeparator(String paramSeparator) {
		this.paramSeparator = paramSeparator;
	}

	public String getParamBeginMark() {
		return paramBeginMark;
	}

	public void setParamBeginMark(String paramBeginMark) {
		this.paramBeginMark = paramBeginMark;
	}
}

