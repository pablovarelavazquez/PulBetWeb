package com.pulbet.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pulbet.web.model.Carrito;
import com.pulbet.web.model.LineaCarrito;
import com.pulbet.web.util.ParameterUtils;
import com.pulbet.web.util.SessionAttributeNames;
import com.pulbet.web.util.SessionManager;
import com.pulbet.web.util.WebConstants;
import com.pvv.pulbet.exceptions.DataException;
import com.pvv.pulbet.exceptions.InstanceNotFoundException;
import com.pvv.pulbet.model.Evento;
import com.pvv.pulbet.model.Resultado;
import com.pvv.pulbet.service.EventoService;
import com.pvv.pulbet.service.ResultadoService;
import com.pvv.pulbet.service.impl.EventoServiceImpl;
import com.pvv.pulbet.service.impl.ResultadoServiceImpl;


@WebServlet("/carrito")
public class CarritoServlet extends HttpServlet {

	private EventoService eventoService = null;
	private ResultadoService resultadoService =  null;
	private static Logger logger = LogManager.getLogger(EventoServlet.class);

	public CarritoServlet() {
		eventoService = new EventoServiceImpl();
		resultadoService = new ResultadoServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(logger.isDebugEnabled()) {
			logger.debug(ParameterUtils.print(request.getParameterMap()));
		}

		String idioma= (String) SessionManager.get(request, WebConstants.IDIOMA);

		Carrito c = (Carrito) SessionManager.get(request, SessionAttributeNames.CARRITO);

		String action = request.getParameter(ParameterNames.ACTION);
		String target = null;
		boolean redirect = false;
		Errors errors = new Errors(); 

		if(Actions.ADD_CARRITO.equalsIgnoreCase(action)) {

			String id  = request.getParameter(ParameterNames.ID_EVENTO);
			Long idEvento =  Long.valueOf(id);
			id = request.getParameter(ParameterNames.ID_RESULTADO);
			Long idResultado =  Long.valueOf(id);

			Evento e;
			Resultado r;
			try {

				if(c != null) {

					e = eventoService.findById(idEvento, idioma);
					r = resultadoService.findCuota(idEvento, idResultado, idioma);
					LineaCarrito lc = new LineaCarrito(e, r);
					LineaCarrito borrar = null;

					if ( c.getLineas().isEmpty()) {
						c.add(lc);
					} else {
						if (c.hasEvent(idEvento)) {
							for(LineaCarrito linea : c.getLineas()) {
								
								if(linea.getEvento().getIdEvento() == idEvento) {
									borrar = linea;
								}
								
							}
							
							if(borrar!=null) {
							c.getLineas().remove(borrar);
							}
						} 
						c.add(lc);
					}

				}

			} catch (InstanceNotFoundException e1) {
				e1.printStackTrace();
			} catch (DataException e1) {
				e1.printStackTrace();
			}

			target = request.getHeader("referer");
			redirect = true;

		} else if (Actions.DEL_CARRITO.equalsIgnoreCase(action)){

			String id  = request.getParameter(ParameterNames.ID_EVENTO);
			Long idEvento =  Long.valueOf(id);
			id = request.getParameter(ParameterNames.ID_RESULTADO);
			Long idResultado =  Long.valueOf(id);

			List<LineaCarrito> lineasNuevas = new ArrayList<LineaCarrito>();

			for (LineaCarrito lc : c.getLineas()) {
				if (!(lc.getEvento().getIdDeporte() == idEvento) && !(lc.getResultado().getIdResultado() == idResultado)) {
					lineasNuevas.add(lc);
				}
			}

			c.setLineas(lineasNuevas);

			target = request.getHeader("referer");
			redirect = true;
			
		}else {
			logger.error("Action desconocida");
			logger.debug("Erro 404 - IP : "+ request.getRemoteAddr() +" - URI ");
			target = ViewPaths.ERROR_404;
			redirect =  true;
		}


		if(redirect) {
			logger.info("Redirecting to "+target);
			response.sendRedirect(target);
		} else {
			logger.info("Forwarding to "+target);
			request.getRequestDispatcher(target).forward(request, response);
		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
