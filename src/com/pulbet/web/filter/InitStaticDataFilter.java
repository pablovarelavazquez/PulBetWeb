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
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pulbet.web.util.LocaleManager;
import com.pulbet.web.util.SessionManager;
import com.pulbet.web.util.WebConstants;
import com.pvv.pulbet.exceptions.DataException;
import com.pvv.pulbet.model.Deporte;
import com.pvv.pulbet.model.Evento;
import com.pvv.pulbet.service.DeporteService;
import com.pvv.pulbet.service.EventoCriteria;
import com.pvv.pulbet.service.EventoService;
import com.pvv.pulbet.service.Results;
import com.pvv.pulbet.service.impl.DeporteServiceImpl;
import com.pvv.pulbet.service.impl.EventoServiceImpl;

public class InitStaticDataFilter implements Filter {
	
	private static Logger logger = LogManager.getLogger(InitStaticDataFilter.class.getName());
	
	private DeporteService deporteService = null;
	private EventoService eventoService = null;
	
    public InitStaticDataFilter() {
    	deporteService =  new DeporteServiceImpl();
    	eventoService = new EventoServiceImpl();

    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		Locale userLocale = (Locale) SessionManager.get(httpRequest, WebConstants.USER_LOCALE);
		String idioma = LocaleManager.getIdioma(userLocale.toString());
		
		logger.debug("Idioma {}", idioma);
		
		EventoCriteria evento = new EventoCriteria();
		
		try {
			List<Deporte> deportes = deporteService.findAll(idioma);
			Results<Evento> eventos = eventoService.findByCriteria(evento, 1, 4, idioma);
			
			request.setAttribute("deportes", deportes);
			request.setAttribute("eventos", eventos);
			
			logger.debug("Deportes {} | Eventos {}", deportes, eventos);
			
			
		} catch (DataException e) {
			logger.warn(e.getMessage(),e);
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
