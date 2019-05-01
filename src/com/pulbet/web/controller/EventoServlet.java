package com.pulbet.web.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pulbet.web.config.ConfigurationManager;
import com.pulbet.web.config.ConfigurationParameterNames;
import com.pulbet.web.util.HttpUtils;
import com.pulbet.web.util.LocaleManager;
import com.pulbet.web.util.ParameterUtils;
import com.pulbet.web.util.SessionManager;
import com.pulbet.web.util.ValidationUtils;
import com.pulbet.web.util.WebConstants;
import com.pulbet.web.util.WebUtils;
import com.pvv.pulbet.exceptions.DataException;
import com.pvv.pulbet.exceptions.InstanceNotFoundException;
import com.pvv.pulbet.model.Competicion;
import com.pvv.pulbet.model.Deporte;
import com.pvv.pulbet.model.Evento;
import com.pvv.pulbet.service.CompeticionService;
import com.pvv.pulbet.service.DeporteService;
import com.pvv.pulbet.service.EventoCriteria;
import com.pvv.pulbet.service.EventoService;
import com.pvv.pulbet.service.Results;
import com.pvv.pulbet.service.impl.CompeticionServiceImpl;
import com.pvv.pulbet.service.impl.DeporteServiceImpl;
import com.pvv.pulbet.service.impl.EventoServiceImpl;


@WebServlet("/evento")
public class EventoServlet extends HttpServlet {
	
	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)); 
	
	private static int pagingPageCount = Integer.valueOf(
	ConfigurationManager.getInstance().getParameter(
				ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT)); 

	private EventoService eventoService = null;
	private CompeticionService competicionService = null;
	private DeporteService deporteService = null;
	private static Logger logger = LogManager.getLogger(EventoServlet.class);
	

	
	public EventoServlet() {
		eventoService = new EventoServiceImpl();
		competicionService = new CompeticionServiceImpl();
		deporteService = new DeporteServiceImpl();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(logger.isDebugEnabled()) {
			logger.debug(ParameterUtils.print(request.getParameterMap()));
		}

		String action = request.getParameter(ParameterNames.ACTION);
		String target = null;
		boolean redirect = false;
		Errors errors = new Errors(); 
		
		Map<String, String[]> mapa = new HashMap<String, String[]>(request.getParameterMap());
		String url = "";
		
		Locale userLocale = (Locale) SessionManager.get(request, WebConstants.USER_LOCALE);
		String idioma = LocaleManager.getIdioma(userLocale.toString());

		if(Actions.BUSCADOR.equalsIgnoreCase(action)) {
		
		String pageValue = null;
		Integer page = null;
		if(mapa.get(ParameterNames.PAGE)!=null) {
			pageValue = mapa.get(ParameterNames.PAGE)[0];	
			page = Integer.valueOf(pageValue);
		}

		mapa.remove(ParameterNames.PAGE);

		EventoCriteria e = new EventoCriteria();
		Results<Evento> results = null;


		//Recuperamos parametros
		String competicion =  request.getParameter(ParameterNames.COMPETICION);
		String desde =  request.getParameter(ParameterNames.FECHA_DESDE);
		String hasta = request.getParameter(ParameterNames.FECHA_HASTA);
		String deporte = request.getParameter(ParameterNames.DEPORTE);
		String participante = request.getParameter(ParameterNames.PARTICIPANTE);
		

		//Validar parametros
		e.setIdCompeticion(ValidationUtils.longValidator(competicion,errors,ParameterNames.COMPETICION,false));
		e.setIdDeporte(ValidationUtils.longValidator(deporte,errors,ParameterNames.DEPORTE,false));
		e.setParticipante(ValidationUtils.stringValidator(participante,errors,ParameterNames.PARTICIPANTE,false));
		
		Date fechauno = ValidationUtils.dateValidator(desde,errors,ParameterNames.FECHA_DESDE, false);
		if(fechauno!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechauno); 
			calendar.add(Calendar.DAY_OF_YEAR, 1);  
			fechauno = calendar.getTime(); 
			e.setFecha(fechauno);
		}
		Date fechados = ValidationUtils.dateValidator(hasta,errors,ParameterNames.FECHA_HASTA, false);
		if(fechados!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechados); 
			calendar.add(Calendar.DAY_OF_YEAR, 2);  
			fechados = calendar.getTime(); 
			e.setFechaHasta(fechados);
		}


		try {
			logger.debug(errors.hasErrors());
			
			if(!errors.hasErrors()) {
				
				page = WebUtils.
						getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
				
				results = eventoService.findByCriteria(e,(page-1)*pageSize+1,pageSize,idioma);
				
				// Resultados de la busqueda (siempre preparar comodos para renderizar)
				request.setAttribute(AttributeNames.RESULTADOS, results.getPage());
				request.setAttribute(AttributeNames.TOTAL, results.getTotal());
				
				// Datos para paginacion															
				// (Calculos aqui, datos comodos para renderizar)
				int totalPages = (int) Math.ceil((double)results.getTotal()/(double)pageSize);
				int firstPagedPage = Math.max(1, page-pagingPageCount);
				int lastPagedPage = Math.min(totalPages, page+pagingPageCount);
				request.setAttribute(ParameterNames.PAGE, page);
				request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
				request.setAttribute(AttributeNames.FIRST_PAGED_PAGES, firstPagedPage);
				request.setAttribute(AttributeNames.LAST_PAGED_PAGES, lastPagedPage);
				
				//parametros de busqueda actuales
				url = HttpUtils.createLinkToSelf(null, mapa);
				request.setAttribute(ParameterNames.URL, url);
				request.setAttribute(ParameterNames.PAGE, page);
				
				if(participante != null) {
				request.setAttribute(ParameterNames.PARTICIPANTE, participante);
				}
				
				if(hasta != null) {
					request.setAttribute(ParameterNames.FECHA_DESDE, e.getFecha());
				}
				
				if(desde != null) {
					request.setAttribute(ParameterNames.FECHA_HASTA, e.getFechaHasta());
				}
				
				
				request.setAttribute(ParameterNames.COMPETICION, mapa.get(ParameterNames.COMPETICION)[0]);
				request.setAttribute(ParameterNames.DEPORTE, mapa.get(ParameterNames.DEPORTE)[0]);
				target=ViewPaths.HOME;
				
			} 
			
			if (errors.hasErrors()) {	
				errors.addError(ParameterNames.ACTION,ErrorCodes.FIND_ERROR);				
				request.setAttribute(AttributeNames.ERRORS, errors);	
				target=ViewPaths.HOME;
			}

		} catch (DataException ex) {
			logger.warn(ex.getMessage(),ex);
			
		} 
		
		} else if (Actions.FIND_DETAIL.equalsIgnoreCase(action)) {
			String idParamValue = request.getParameter(ParameterNames.ID);
			Long id = Long.valueOf(idParamValue);
			Evento evento;
			Competicion competicion;
			Deporte deporte;
			
			try {
				evento = eventoService.findById(id,idioma);
				competicion = competicionService.findById(evento.getIdCompeticion());
				deporte = deporteService.findById(evento.getIdDeporte(), idioma);
				request.setAttribute(AttributeNames.EVENTO, evento);
				request.setAttribute(AttributeNames.COMPETICION, competicion);
				request.setAttribute(AttributeNames.DEPORTE, deporte);
			} catch (InstanceNotFoundException e) {
				logger.warn(e.getMessage(),e);
			} catch (DataException e) {
				logger.warn(e.getMessage(),e);
			}			
			
			target = ViewPaths.DETALLE;
			
		} else if (Actions.FIND_COMPETITION.equalsIgnoreCase(action)) {
			
			String idDeporte = request.getParameter(ParameterNames.ID);
			Long id = Long.valueOf(idDeporte);
			List<Competicion> competiciones;

			try {
				competiciones = competicionService.findByDeporte(id);
				
				JsonObject competicion = null;
				JsonArray array = new JsonArray();
				for (Competicion c : competiciones) {
					competicion = new JsonObject();
					competicion.addProperty("id", c.getIdCompeticion());
					competicion.addProperty("nome", c.getNome());
					array.add(competicion);
				}	
				
				response.setContentType("application/json;charset=ISO-8859-1");
				response.getOutputStream().write(array.toString().getBytes());
				
			} catch (DataException e) {
				logger.warn(e.getMessage(),e);
			} 
			
		}else {
			logger.error("Action desconocida");
			logger.debug("Erro 404 - IP : "+ request.getRemoteAddr() +" - URI ");
			target = ViewPaths.ERROR_404;
			redirect =  true;
		}
		
		if(!Actions.FIND_COMPETITION.equalsIgnoreCase(action)) {
		if(redirect) {
			logger.info("Redirecting to "+target);
			response.sendRedirect(request.getContextPath()+target);
		} else {
			logger.info("Forwarding to "+target);
			request.getRequestDispatcher(target).forward(request, response);
		}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
