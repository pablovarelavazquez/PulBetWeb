package com.pulbet.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.log.Log;


/**
 * Clase de utilidades para http
 *
 */
public  class HttpUtils {
	
	/** Log */
	private static final Logger logger = LogManager.getLogger(HttpUtils.class);
	
	/** Constante con el tama�o del StringBuffer */
	private static final int SB_SIZE = 256;
	
	/** Constructor */
	private HttpUtils(){}
		
	
	public static String createCallbackURL(HttpServletRequest request, String url) {
		String referer = request.getHeader("referer");
		if(referer.indexOf("?")>=0) {
		referer = referer.substring(0, referer.indexOf("?"));
		}
		String target = referer + url;
		
		return target;
	}
	
	/**
	 * Genera un link a la p&aacute;gina actual, anadiendo/reemplazando los
	 * par&aacute;metros especificados en el Map
	 * <p>
	 * Recupera el link a la p&aacute;gina que se muestra a trav&eacute;s
	 * de la request y regenera el enlace añadiendo/reemplazando 
	 * par&aacute;metros
	 *  
	 * @param request	javax.servlet.http.HttpServletRequest
	 * @param params	conjunto de pares clave-valor a añadir/reemplazar
	 * @return			url como String
	 */
	public static String createLinkToSelf(HttpServletRequest request,
			Map<String, String[]> params) {
		
		StringBuffer url = new StringBuffer(SB_SIZE);
		String queryString = null;
		if(request!=null) {
		url.append(request.getRequestURL().toString());
		queryString = request.getQueryString();
		}
		if (queryString != null){
			url.append("?"+queryString);
		}
		URLBuilder urlBuilder = new URLBuilder(url.toString());

		urlBuilder.addParameterMap(params, true);
		return urlBuilder.toString();
	}
	
	/**
	 * Genera un link a la p&aacute;gina actual, añadiendo/reemplazando el
	 * par clave-valor especificado
	 * <p>
	 * Recupera el link a la p&aacute;gina que se muestra a trav&eacute;s
	 * de la request y regenera el enlace añadiendo/reemplazando 
	 * el par clave-valor 
	 * 
	 * @param request	javax.servlet.http.HttpServletRequest
	 * @param key		clave del par&aacute;metro a añadir/reemplazar
	 * @param value		valor del par&aacute;metro a añadir/reemplazar
	 * @return			url como String
	 */
	public static String createLinkToSelf(HttpServletRequest request,
			String key, String value) {
		
		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] values = new String[2];
		values[0] = value;
		map.put(key, values);
		
		if (logger.isDebugEnabled())
			logger.debug("| HttpUtils.createLinkToSelf | TRACE --> key="+ key+ ", value="+ value);
		
		return createLinkToSelf(request, map);
	}
	
	/**
	 * Añade un par�metro de la forma param=value a la URL.
	 * <p>
	 * Si la URL no tiene parñmetros agrega autom�ticamente el separador
	 * '?'. Si exist�a previamente algún parámetro añade el separador
	 * de parámetros '&'.
	 * <p>
	 * Si el par&aacute;metro indicado ya exist&iacutea se reemplaza o se 
	 * a&nacute;ade seg&uacute;n lo indicado en el &uacute;ltimo argumento
	 * 
	 * @param url				URL a la que se añade el parámetro
	 * @param paramName			nombre del parámetro a añadir
	 * @param paramValue		valor del par�metro a añadir
	 * @param replaceIfexists	indica reemplazar o añadir
	 * @return					URL con el par�metro añadido
	 * @see						es.ieci.common.http.URLBuilder
	 */
	public static String addParameterToURL(String url, String paramName,
			String paramValue, boolean replaceifExists) {
		
		URLBuilder urlBuilder = new URLBuilder(url);
		urlBuilder.addParam(paramName, paramValue, replaceifExists);
		return urlBuilder.toString();
	}
	
	/**
	 * Codifica la url a "ISO-8859-1"
	 * @param value
	 * @return
	 */
	public static String encodeUrl(String value) {
		try {
			return URLEncoder.encode(value, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			
			if (logger.isDebugEnabled())
				logger.error("| HttpUtils.encodeUrl | UNSUPPORTED_ENCODING_EXCEPTION --> msg: "+e.getMessage());
			
			e.printStackTrace();				
		}
		return value;
	}

}