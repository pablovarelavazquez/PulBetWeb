package com.pulbet.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pulbet.web.util.CookieManager;
import com.pulbet.web.util.ParameterUtils;
import com.pulbet.web.util.SessionAttributeNames;
import com.pulbet.web.util.SessionManager;
import com.pulbet.web.util.ValidationUtils;
import com.pulbet.web.util.WebConstants;
import com.pvv.pulbet.exceptions.DataException;
import com.pvv.pulbet.exceptions.InstanceNotFoundException;
import com.pvv.pulbet.model.Evento;
import com.pvv.pulbet.service.EventoCriteria;
import com.pvv.pulbet.service.EventoService;
import com.pvv.pulbet.service.Results;
import com.pvv.pulbet.service.impl.EventoServiceImpl;


@WebServlet("/evento")
public class EventoServlet extends HttpServlet {

	private EventoService eventoService = null;
	private static Logger logger = LogManager.getLogger(EventoServlet.class);
	public EventoServlet() {
		eventoService = new EventoServiceImpl();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(logger.isDebugEnabled()) {
			logger.debug(ParameterUtils.print(request.getParameterMap()));
		}

		String action = request.getParameter(ParameterNames.ACTION);
		String target = null;
		boolean redirect = false;
		Errors errors = new Errors(); 

		if(Actions.BUSCADOR.equalsIgnoreCase(action)) {
			
		String idioma= (String) SessionManager.get(request, WebConstants.IDIOMA);
		
		EventoCriteria e = new EventoCriteria();
		Results<Evento> resultados = null;
		List<Evento> eventos = new ArrayList<Evento>();


		//Recuperamos parametros
		String idEvento = request.getParameter(ParameterNames.EVENTO);
		String competicion =  request.getParameter(ParameterNames.COMPETICION);
		String hasta = request.getParameter(ParameterNames.FECHA);
		String deporte = request.getParameter(ParameterNames.DEPORTE);
		String participante = request.getParameter(ParameterNames.PARTICIPANTE);

		//Validar parametros
		e.setIdEvento(ValidationUtils.longValidator(idEvento,errors,ParameterNames.EVENTO,false));
		e.setIdCompeticion(ValidationUtils.longValidator(competicion,errors,ParameterNames.COMPETICION,false));
		e.setFecha(ValidationUtils.dateValidator(hasta, errors,ParameterNames.FECHA_HASTA,false));
		e.setIdDeporte(ValidationUtils.longValidator(deporte,errors,ParameterNames.DEPORTE,false));
		e.setParticipante(ValidationUtils.stringValidator(participante,errors,ParameterNames.PARTICIPANTE,false));


		try {
			logger.debug(errors.hasErrors());
			if(!errors.hasErrors()) {
				resultados = eventoService.findByCriteria(e,1,13,idioma);
				
				
				request.setAttribute("resultados",resultados.getPage());
			} 
			
			if (errors.hasErrors()) {	
				errors.addError(ParameterNames.ACTION,ErrorCodes.FIND_ERROR);				
				request.setAttribute(AttributeNames.ERRORS, errors);				
			}

		} catch (DataException ex) {
			ex.printStackTrace();
			
		} finally {
			target=ViewPaths.HOME;
			//redirect=true;
		}
		
		} else if (Actions.FIND_DETAIL.equalsIgnoreCase(action)) {
			String idParamValue = request.getParameter(ParameterNames.ID);
			Long id = Long.valueOf(idParamValue);
			String idioma = (String) SessionManager.get(request,WebConstants.IDIOMA);
			Evento evento;
			
			try {
				evento = eventoService.findById(id,idioma);
				request.setAttribute(AttributeNames.EVENTO, evento);
			} catch (InstanceNotFoundException e) {
				e.printStackTrace();
			} catch (DataException e) {
				e.printStackTrace();
			}			
			
			
			target = ViewPaths.DETALLE;
		}
		
		if(redirect) {
			logger.info("Redirecting to "+target);
			response.sendRedirect(request.getContextPath()+target);
		} else {
			logger.info("Forwarding to "+target);
			request.getRequestDispatcher(target).forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
