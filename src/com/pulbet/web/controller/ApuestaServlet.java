package com.pulbet.web.controller;

import java.io.IOException;
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

import com.pulbet.web.model.Carrito;
import com.pulbet.web.model.LineaCarrito;
import com.pulbet.web.util.LocaleManager;
import com.pulbet.web.util.ParameterUtils;
import com.pulbet.web.util.SessionAttributeNames;
import com.pulbet.web.util.SessionManager;
import com.pulbet.web.util.WebConstants;
import com.pvv.pulbet.exceptions.DataException;
import com.pvv.pulbet.exceptions.DuplicateInstanceException;
import com.pvv.pulbet.model.Apuesta;
import com.pvv.pulbet.model.LineaApuesta;
import com.pvv.pulbet.model.Usuario;
import com.pvv.pulbet.service.ApuestaService;
import com.pvv.pulbet.service.BancoService;
import com.pvv.pulbet.service.UsuarioService;
import com.pvv.pulbet.service.impl.ApuestaServiceImpl;
import com.pvv.pulbet.service.impl.BancoServiceImpl;
import com.pvv.pulbet.service.impl.UsuarioServiceImpl;

@WebServlet("/apuesta")
public class ApuestaServlet extends HttpServlet {

	
	private ApuestaService apuestaService = null;
	private BancoService bancoService = null;
	private UsuarioService usuarioService = null;
	private static Logger logger = LogManager.getLogger(ApuestaServlet.class);
	
    public ApuestaServlet() {
    	apuestaService = new ApuestaServiceImpl();
    	bancoService = new BancoServiceImpl();
    	usuarioService = new UsuarioServiceImpl();
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
			
			int count = 1;
			double ganancias = 1.0;
			String importeValue = request.getParameter(ParameterNames.IMPORTE);
			Double importe = Double.valueOf(importeValue);
			//String ganancias = request.getParameter(ParameterNames.GANANCIAS);
			
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
					e.printStackTrace();
				} catch (DataException e) {
					e.printStackTrace();
				}
				
				//mail de apuesta realizada
				
				//Esto valeira o carrito, non sei se e o mais axeitado.
				//c.setLineas(lineasCarrito);
				
				SessionManager.set(request, SessionAttributeNames.USER, u);
				redirect = true;

			} else if (u == null){ 
				errors.addError(ParameterNames.APUESTA, ErrorCodes.NOT_LOGGED);
				request.setAttribute(AttributeNames.ERRORS, errors);			
				
				if(logger.isDebugEnabled()) {
					logger.debug("Error creando apuesta");
				}
			} else if (u.getBanco()<importe) {
				errors.addError(ParameterNames.APUESTA, ErrorCodes.NOT_ENOUGH_MONEY);
				request.setAttribute(AttributeNames.ERRORS, errors);
				if(logger.isDebugEnabled()) {
					logger.debug("Error creando apuesta");
				}
			}
						
			target = ViewPaths.HOME;
			
			
			
		} else {
			logger.error("Action desconocida");
			logger.debug("Erro 404 - IP : "+ request.getRemoteAddr() +" - URI ");
			target = ViewPaths.ERROR_404;
			redirect =  true;
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
