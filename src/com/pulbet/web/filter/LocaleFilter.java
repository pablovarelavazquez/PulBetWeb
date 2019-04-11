package com.pulbet.web.filter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.util.StringUtils;
import com.pulbet.web.util.CookieManager;
import com.pulbet.web.util.LocaleManager;
import com.pulbet.web.util.SessionManager;
import com.pulbet.web.util.WebConstants;

public class LocaleFilter implements Filter {
	
	private static Logger logger = LogManager.getLogger(LocaleFilter.class.getName());
	
    public LocaleFilter() {       
    }

	public void init(FilterConfig cfg) throws ServletException {
		// Habitualmente la configuracion de los parametros
		// de un filtro se hace en el web.xml.

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpServletResponse httpResponse = ((HttpServletResponse) response);
		
		

		String idioma = (String) SessionManager.get(httpRequest, WebConstants.IDIOMA);
		Locale locale = (Locale) SessionManager.get(httpRequest, WebConstants.USER_LOCALE);
		
		logger.debug("Sesion , idioma {} locale {}", idioma, locale);
		
		if (locale == null) {

			// Primero intentamos inicializar el locale de cookie.
			Cookie cookieLocale = CookieManager.getCookie(httpRequest, WebConstants.USER_LOCALE);
			if (cookieLocale!=null) {
				//idioma = cookieLocale.getValue();
				
				locale = getLocale(cookieLocale.getValue());
				if (locale!=null && logger.isDebugEnabled()) {
					logger.debug("Locale initialized from cookie: "+locale);
				}	
			} else {
				// En ultimo término, a modo de "por defecto", inicializamos a partir 
				// del header Accept-Language de la request. 
				// Más info: https://www.w3.org/International/questions/qa-accept-lang-locales
				locale = getLocale(httpRequest);
				if (locale!=null && logger.isDebugEnabled()) {
					logger.debug("Locale initialized from header: "+locale);
				}
				
			}

			if (locale==null) {
				// En ultimo caso, el primero de los soportados como opcion por defecto.
				locale = LocaleManager.getDefault();
				logger.warn("Using default locale: "+locale);			
			}

			idioma = LocaleManager.getIdioma(locale.toString());
			
			SessionManager.set(httpRequest, WebConstants.USER_LOCALE, locale);			
			CookieManager.addCookie(httpResponse, WebConstants.USER_LOCALE, locale.toString(), "/", 365*24*60*60);

			SessionManager.set(httpRequest, WebConstants.IDIOMA, idioma);			
			CookieManager.addCookie(httpResponse, WebConstants.IDIOMA, idioma, "/", 365*24*60*60);
			
			
		}
		
		// Continuar la invocacion de la cadena de responsabilidad.
		// Solamente no se invocaría si el filtro determinase otro 
		// como por ejemplo en el caso de un filtro de autenticación.
		
		chain.doFilter(request, response);		
	
	}


	protected Locale getLocale(HttpServletRequest httpRequest) {			
		String acceptLanguageHeader = httpRequest.getHeader("Accept-Language");
		return getLocale(acceptLanguageHeader);		       
	}
	
	protected Locale getLocale(String ranges) {
		// Miramos cuales de los lenguajes establecidos por el usuario en su navegador
		// son soportados por nuesetra web
		List<Locale> matchedLocales = LocaleManager.getMatchedLocales(ranges);
		
		Locale locale = null;
		if (matchedLocales.size()>0) {
			locale = matchedLocales.get(0);
			if (logger.isDebugEnabled()) {
				logger.debug("Matched "+matchedLocales.size()+" locales. Selected: "+locale);
			}
		} else {
			logger.warn("No matched locale: "+ranges);
		}
		return locale;

	}

	public void destroy() {
		
	}
	
}
