package com.pulbet.web.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pulbet.web.model.Carrito;
import com.pulbet.web.model.LineaCarrito;
import com.pulbet.web.util.DateUtils;
import com.pulbet.web.util.LocaleManager;
import com.pulbet.web.util.ParameterUtils;
import com.pulbet.web.util.SessionAttributeNames;
import com.pulbet.web.util.SessionManager;
import com.pulbet.web.util.WebConstants;
import com.pvv.pulbet.exceptions.DataException;
import com.pvv.pulbet.exceptions.DuplicateInstanceException;
import com.pvv.pulbet.model.Apuesta;
import com.pvv.pulbet.model.Evento;
import com.pvv.pulbet.model.LineaApuesta;
import com.pvv.pulbet.model.Resultado;
import com.pvv.pulbet.model.TipoResultado;
import com.pvv.pulbet.model.Usuario;
import com.pvv.pulbet.service.ApuestaService;
import com.pvv.pulbet.service.BancoService;
import com.pvv.pulbet.service.EventoService;
import com.pvv.pulbet.service.ResultadoService;
import com.pvv.pulbet.service.UsuarioService;
import com.pvv.pulbet.service.impl.ApuestaServiceImpl;
import com.pvv.pulbet.service.impl.BancoServiceImpl;
import com.pvv.pulbet.service.impl.EventoServiceImpl;
import com.pvv.pulbet.service.impl.ResultadoServiceImpl;
import com.pvv.pulbet.service.impl.UsuarioServiceImpl;

@WebServlet("/apuesta")
public class ApuestaServlet extends HttpServlet {

	
	private ApuestaService apuestaService = null;
	private BancoService bancoService = null;
	private UsuarioService usuarioService = null;
	private EventoService eventoService = null;
	private ResultadoService resultadoService = null;
	private static Logger logger = LogManager.getLogger(ApuestaServlet.class);
	
    public ApuestaServlet() {
    	apuestaService = new ApuestaServiceImpl();
    	bancoService = new BancoServiceImpl();
    	usuarioService = new UsuarioServiceImpl();
    	eventoService = new EventoServiceImpl();
    	resultadoService = new ResultadoServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(logger.isDebugEnabled()) {
			logger.debug(ParameterUtils.print(request.getParameterMap()));
		}

		Locale userLocale = (Locale) SessionManager.get(request, WebConstants.USER_LOCALE);
		String idioma = LocaleManager.getIdioma(userLocale.toString());

		Carrito c = (Carrito) SessionManager.get(request, SessionAttributeNames.CARRITO);
		Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

		String action = request.getParameter(ParameterNames.ACTION);
		String target = null;
		boolean redirect = false;
		Errors errors = new Errors(); 
		
		if(Actions.APOSTAR.equalsIgnoreCase(action)){
			
			Apuesta a = new Apuesta();
			List<LineaApuesta> lineas = new ArrayList<LineaApuesta>();
			List<LineaCarrito> lineasCarrito = new ArrayList<LineaCarrito>();
			int count = 1;
			double ganancias = 1.0;
			String importeValue = request.getParameter(ParameterNames.IMPORTE);
			Double importe = Double.valueOf(importeValue);
			
			Date actual  = new Date();

			
			if ((u != null) && (u.getBanco()>= importe)) {
				for(LineaCarrito lc : c.getLineas()) {
					LineaApuesta la = new LineaApuesta();
					la.setIdEvento(lc.getEvento().getIdEvento());
					la.setIdResultado(lc.getResultado().getIdResultado());
					la.setNumLinea(count);
					count++;
					ganancias = ganancias * lc.getResultado().getCuota();
					lineas.add(la);
				}
				
				ganancias = ganancias * importe;
				
				a.setIdUsuario(u.getIdUsuario());
				a.setImporte(importe);
				a.setLineas(lineas);
				a.setFecha(actual);
				a.setGanancias(ganancias);
				
				try {
					apuestaService.create(a);
					bancoService.retirar(a.getIdUsuario(), a.getImporte());
					
					u = usuarioService.findById(u.getIdUsuario());

				} catch (DuplicateInstanceException e) {
					logger.warn(e.getMessage(),e);
				} catch (DataException e) {
					logger.warn(e.getMessage(),e);
				}
				
				c.setLineas(lineasCarrito);
				
				SessionManager.set(request, SessionAttributeNames.USER, u);
				target="";
				redirect = true;

			} else if (u == null){ 
				errors.addError(ParameterNames.ACTION, ErrorCodes.NOT_LOGGED);
				request.setAttribute(AttributeNames.ERRORS, errors);			
				
				if(logger.isDebugEnabled()) {
					logger.debug("Error creando apuesta, usuario no logueado");
				}
				
				target=ViewPaths.LOGIN;
				
				
			} else if (u.getBanco()<importe) {
				errors.addError(ParameterNames.ACTION, ErrorCodes.NOT_ENOUGH_MONEY);
				request.setAttribute(AttributeNames.ERRORS, errors);
				
				if(logger.isDebugEnabled()) {
					logger.debug("Error creando apuesta, sin dinero suficiente");
				}
				
				target=ViewPaths.INGRESAR;
			}
						
			
		} else if(Actions.FIND_DETAIL.equalsIgnoreCase(action)) {
			
			String idApuesta = request.getParameter(ParameterNames.ID);
			
			Long id = Long.valueOf(idApuesta);
			
			Apuesta a = null;
			JsonObject linea = null;
			JsonArray array = new JsonArray();
			Evento e = null;
			Resultado r = null;
			Long l = null;
			String fecha = null;
			String mensaje = null;
			try {
				
				
				a = apuestaService.findById(id);
				
				for(LineaApuesta la : a.getLineas()) {
					linea = new JsonObject();
					
					e = eventoService.findById(la.getIdEvento(), idioma);
					r = resultadoService.findCuota(la.getIdEvento(), la.getIdResultado(), idioma);
					
					for(TipoResultado tr : e.getMercados()) {
						
						l = new Long(tr.getIdTipoResultado());
						
						if(r.getIdTipoResulatado() ==  Long.valueOf(l)) {
							linea.addProperty("tipoResultado", tr.getNome());
						}
					}
					
					if(idioma.equals("ESP") || idioma.equals("GAL")) {
						if(la.getProcesado() == 1) {
							linea.addProperty("mensaje", "ACERTADA");
							linea.addProperty("clase", "acertada");
						} else if(la.getProcesado() == 2) {
							linea.addProperty("mensaje", "FALLADA");
							linea.addProperty("clase", "fallada");
						} else {
							linea.addProperty("mensaje", "PENDIENTE");
							linea.addProperty("clase", "pendiente");
						}
					} else if (idioma.equals("ENG")) {
						if(la.getProcesado() == 1) {
							linea.addProperty("mensaje", "WINNED");
							linea.addProperty("clase", "acertada");
						} else if(la.getProcesado() == 2) {
							linea.addProperty("mensaje", "LOSED");
							linea.addProperty("clase", "fallada");
						} else {
							linea.addProperty("mensaje", "ACTIVE");
							linea.addProperty("clase", "pendiente");
						}
					}
					
					
					
					linea.addProperty("estado", la.getProcesado());
					linea.addProperty("resultado", r.getNombre());
					linea.addProperty("cuota", r.getCuota().toString());
					linea.addProperty("local", e.getLocal().getNome());
					linea.addProperty("visitante", e.getVisitante().getNome());
					fecha = DateUtils.WITH_HOUR_FORMAT.format(e.getFecha());
					linea.addProperty("fecha", fecha);
					
					array.add(linea);
				}
				
				response.setContentType("application/json;charset=ISO-8859-1");
				response.getOutputStream().write(array.toString().getBytes());
				
			} catch (DataException ex) {
				logger.warn(ex.getMessage(),ex);
			}
			
			
		} else {
			logger.error("Action desconocida");
			logger.debug("Erro 404 - IP : "+ request.getRemoteAddr() +" - URI ");
			target = ViewPaths.ERROR_404;
			redirect =  true;
		}
		
		if((!Actions.FIND_DETAIL.equalsIgnoreCase(action))) {
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
