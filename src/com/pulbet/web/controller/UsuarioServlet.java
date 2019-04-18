package com.pulbet.web.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pulbet.web.model.Carrito;
import com.pulbet.web.model.LineaCarrito;
import com.pulbet.web.util.CookieManager;
import com.pulbet.web.util.LocaleManager;
import com.pulbet.web.util.ParameterUtils;
import com.pulbet.web.util.SessionAttributeNames;
import com.pulbet.web.util.SessionManager;
import com.pulbet.web.util.ValidationUtils;
import com.pulbet.web.util.WebConstants;
import com.pvv.pulbet.exceptions.DataException;
import com.pvv.pulbet.exceptions.DuplicateInstanceException;
import com.pvv.pulbet.exceptions.MailException;
import com.pvv.pulbet.model.Apuesta;
import com.pvv.pulbet.model.Direccion;
import com.pvv.pulbet.model.Pais;
import com.pvv.pulbet.model.Provincia;
import com.pvv.pulbet.model.Usuario;
import com.pvv.pulbet.service.ApuestaCriteria;
import com.pvv.pulbet.service.ApuestaService;
import com.pvv.pulbet.service.BancoService;
import com.pvv.pulbet.service.PaisService;
import com.pvv.pulbet.service.ProvinciaService;
import com.pvv.pulbet.service.Results;
import com.pvv.pulbet.service.UsuarioService;
import com.pvv.pulbet.service.impl.ApuestaServiceImpl;
import com.pvv.pulbet.service.impl.BancoServiceImpl;
import com.pvv.pulbet.service.impl.PaisServiceImpl;
import com.pvv.pulbet.service.impl.ProvinciaServiceImpl;
import com.pvv.pulbet.service.impl.UsuarioServiceImpl;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(UsuarioServlet.class);
	private UsuarioService usuarioService = null;
	private ApuestaService apuestaService = null;
	private BancoService bancoService = null;
	private ProvinciaService provinciaService = null;
	private PaisService paisService = null;

	public UsuarioServlet() {
		super();
		usuarioService = new UsuarioServiceImpl();
		apuestaService = new ApuestaServiceImpl();
		bancoService = new BancoServiceImpl();
		provinciaService = new ProvinciaServiceImpl();
		paisService = new PaisServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter(ParameterNames.ACTION);

		Errors errors = new Errors();

		String target = null;
		boolean redirect = false;

		Locale userLocale = (Locale) SessionManager.get(request, WebConstants.USER_LOCALE);
		String idioma = LocaleManager.getIdioma(userLocale.toString());

		Map<String, String[]> mapa = new HashMap<String, String[]>(request.getParameterMap());
		String url = "";

		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}

		if(Actions.LOGIN.equalsIgnoreCase(action)) {

			//Recuperamos parametros
			String email = request.getParameter(ParameterNames.LOGIN_EMAIL);
			String password = request.getParameter(ParameterNames.PASSWORD);

			//Validamos paramtros


			Usuario u = null;

			if(!errors.hasErrors()) {
				try {
					u = usuarioService.login(email, password);
				} catch (DataException e) {
					e.printStackTrace();
				}
			}

			if (u == null) {
				errors.addError(ParameterNames.ACTION,ErrorCodes.AUTHENTICATION_ERROR);	
			}

			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Autenticacion fallida: {}", errors);
				}
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.HOME;				
			} else {				
				SessionManager.set(request, SessionAttributeNames.USER, u);		
				target = ViewPaths.HOME;				
			}
		}else if( Actions.LOGOUT.equalsIgnoreCase(action)) {

			Carrito c = (Carrito) SessionManager.get(request, SessionAttributeNames.CARRITO);
			List<LineaCarrito> lineas = new ArrayList<LineaCarrito>();
			c.setLineas(lineas);

			SessionManager.set(request, SessionAttributeNames.USER, null);
			SessionManager.set(request, SessionAttributeNames.CARRITO, c);

			target = "";
			redirect = true;

		} else if (Actions.PRE_REGISTRO.equalsIgnoreCase(action)){

			String idValue = request.getParameter(ParameterNames.ID);
			Integer id = null;
			if(idValue!=null) {
			 id = Integer.valueOf(idValue);
			}
			List<Provincia> provincias = null;
			
 			try {
				List<Pais> paises = paisService.findAll(idioma);

				if(id!=null) {
					provincias = provinciaService.findByPais(id);
					JsonObject provincia = null;
					JsonArray array = new JsonArray();
					for (Provincia p: provincias) {
						 provincia = new JsonObject();
						 provincia.addProperty("id", p.getIdProvincia());
						 provincia.addProperty("nome", p.getNome());
						 array.add(provincia);
					}	
					response.setContentType("application/json;charset=ISO-8859-1");
					response.getOutputStream().write(array.toString().getBytes());

					
				} else {
					
					provincias = provinciaService.findByPais(34);
					request.setAttribute(AttributeNames.PAISES, paises);
					request.setAttribute(AttributeNames.PROVINCIAS, provincias);
					
				}



			} catch (DataException e) {
				logger.warn(e.getMessage(),e);
			}
			
			if(id==null) {
			target = ViewPaths.REGISTRO;
			}

		} else if (Actions.REGISTRO.equalsIgnoreCase(action)){

			//Recupero parametros
			String nombre = request.getParameter(ParameterNames.NOMBRE); 
			String email = request.getParameter(ParameterNames.REG_EMAIL);
			String apelido1 = request.getParameter(ParameterNames.APELIDO1);
			String apelido2 = request.getParameter(ParameterNames.APELIDO2);
			String password = request.getParameter(ParameterNames.PASSWORD);
			String fnac = request.getParameter(ParameterNames.FECHA_NACIMIENTO);
			String telefono = request.getParameter(ParameterNames.TELEFONO);
			String nomeUsuario = request.getParameter(ParameterNames.NOME_USUARIO);
			String dni = request.getParameter(ParameterNames.DNI);

			String cidade = request.getParameter(ParameterNames.CIDADE);
			String provincia = request.getParameter(ParameterNames.PROVINCIA);
			String calle = request.getParameter(ParameterNames.CALLE);
			String numero = request.getParameter(ParameterNames.NUMERO);
			String codPostal = request.getParameter(ParameterNames.COD_POSTAL);
			String piso = request.getParameter(ParameterNames.PISO);
			String letra = request.getParameter(ParameterNames.LETRA);

			Usuario u =  new Usuario();
			Direccion d =  new Direccion();

			//Valido parametros
			u.setNome(ValidationUtils.namesOnlyLettersValidator(nombre,errors,ParameterNames.NOMBRE, true));
			u.setEmail(ValidationUtils.emailValidator(email,errors,ParameterNames.REG_EMAIL, true));
			u.setApelido1(ValidationUtils.namesOnlyLettersValidator(apelido1,errors,ParameterNames.APELIDO1, true));
			u.setApelido2(ValidationUtils.namesOnlyLettersValidator(apelido2,errors,ParameterNames.APELIDO2, true));
			u.setPassword(ValidationUtils.passwordValidator(password,errors,ParameterNames.PASSWORD, true));
			u.setTelefono(ValidationUtils.stringValidator(telefono,errors,ParameterNames.TELEFONO, true));
			u.setNomeUsuario(ValidationUtils.namesWithNumbersValidator(nomeUsuario,errors,ParameterNames.NOME_USUARIO, true));
			u.setDNI(ValidationUtils.nifValidator(dni,errors,ParameterNames.DNI, true));
			
			Date fechanac = ValidationUtils.dateValidator(fnac,errors,ParameterNames.FECHA_NACIMIENTO, true);
			if(fechanac!=null) {
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(fechanac); 
		    calendar.add(Calendar.DAY_OF_YEAR, 1);  
		    fechanac = calendar.getTime(); 
		    u.setFechaNacimiento(fechanac);
			}

			d.setCiudad(ValidationUtils.namesOnlyLettersValidator(cidade,errors,ParameterNames.CIDADE, true));
			d.setIdProvincia(ValidationUtils.longValidator(provincia,errors,ParameterNames.PROVINCIA, true));
			d.setCalle(ValidationUtils.namesOnlyLettersValidator(calle,errors,ParameterNames.CALLE, true));
			d.setNumero(ValidationUtils.intValidator(numero,errors,ParameterNames.NUMERO, true));
			d.setCodPostal(ValidationUtils.intValidator(codPostal,errors,ParameterNames.COD_POSTAL, true));
			d.setPiso(ValidationUtils.intValidator(piso,errors,ParameterNames.PISO, false));
			d.setLetra(ValidationUtils.letterValidator(letra,errors,ParameterNames.LETRA, false));

			u.setDireccion(d);


			if(!errors.hasErrors()) {

				try {
					u = usuarioService.create(u);

				} catch (DuplicateInstanceException e) {
					e.printStackTrace();
				} catch (MailException e) {
					e.printStackTrace();
				} catch (DataException e) {
					e.printStackTrace();
				}

			}

			if(u == null) {
				errors.addError(ParameterNames.ACTION,ErrorCodes.REGISTER_ERROR);	
			}

			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Fallo en el registro: {}", errors);
				}
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.REGISTRO;				
			} else {				
				SessionManager.set(request, SessionAttributeNames.USER, u);		
				target = ViewPaths.HOME;				
			}


		} 	else if (Actions.CHANGE_LOCALE.equalsIgnoreCase(action)) {

			url = ParameterUtils.URLBuilder(url, mapa);

			String localeName = request.getParameter(ParameterNames.LOCALE);
			// Recordar que hay que validar... lo que nos envian, incluso en algo como esto.
			// Buscamos entre los Locale soportados por la web:
			List<Locale> results = LocaleManager.getMatchedLocales(localeName);
			Locale newLocale = null;
			if (results.size()>0) {
				newLocale = results.get(0);
			} else {
				logger.warn("Request locale not supported: "+localeName);
				newLocale = LocaleManager.getDefault();
			}

			SessionManager.set(request, WebConstants.USER_LOCALE, newLocale);
			CookieManager.addCookie(response, WebConstants.USER_LOCALE, newLocale.toString(), "/", 365*24*60*60);

			if (logger.isDebugEnabled()) {
				logger.debug("Locale changed to "+newLocale);
			}

			response.sendRedirect(request.getHeader("referer"));


		}else if (Actions.HISTORY.equalsIgnoreCase(action)){

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

			Results<Apuesta> apuestas = null;

			ApuestaCriteria apuestaTipo = new ApuestaCriteria();
			apuestaTipo.setIdUsuario(u.getIdUsuario());

			try {

				apuestas = apuestaService.findHistorial(apuestaTipo, 1, 10);

			} catch (DataException e) {
				e.printStackTrace();
			}

			request.setAttribute(AttributeNames.APUESTAS, apuestas);
			target = ViewPaths.HISTORY;

		}else if (Actions.OPENBETS.equalsIgnoreCase(action)){

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

			Results<Apuesta> apuestas = null;

			ApuestaCriteria apuestaTipo = new ApuestaCriteria();
			apuestaTipo.setIdUsuario(u.getIdUsuario());

			try {

				apuestas = apuestaService.findOpenBets(apuestaTipo, 1, 10);

			} catch (DataException e) {
				e.printStackTrace();
			}

			request.setAttribute(AttributeNames.APUESTAS, apuestas);
			target = ViewPaths.OPENBETS;

		} else if (Actions.EDITPROFILE.equalsIgnoreCase(action)){

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
			Direccion d =  u.getDireccion();
			//Recupero parametros

			String nombre = request.getParameter(ParameterNames.NOMBRE); 
			String email = request.getParameter(ParameterNames.REG_EMAIL);
			String apelido1 = request.getParameter(ParameterNames.APELIDO1);
			String apelido2 = request.getParameter(ParameterNames.APELIDO2);
			String password = request.getParameter(ParameterNames.PASSWORD);
			String fnac = request.getParameter(ParameterNames.FECHA_NACIMIENTO);
			String telefono = request.getParameter(ParameterNames.TELEFONO);
			String nomeUsuario = request.getParameter(ParameterNames.NOME_USUARIO);
			String dni = request.getParameter(ParameterNames.DNI);

			String cidade = request.getParameter(ParameterNames.CIDADE);
			String provincia = request.getParameter(ParameterNames.PROVINCIA);
			String calle = request.getParameter(ParameterNames.CALLE);
			String numero = request.getParameter(ParameterNames.NUMERO);
			String codPostal = request.getParameter(ParameterNames.COD_POSTAL);
			String piso = request.getParameter(ParameterNames.PISO);
			String letra = request.getParameter(ParameterNames.LETRA);

			//Valido parametros
			u.setNome(ValidationUtils.namesOnlyLettersValidator(nombre,errors,ParameterNames.NOMBRE, true));
			u.setEmail(ValidationUtils.emailValidator(email,errors,ParameterNames.REG_EMAIL, true));
			u.setApelido1(ValidationUtils.namesOnlyLettersValidator(apelido1,errors,ParameterNames.APELIDO1, true));
			u.setApelido2(ValidationUtils.namesOnlyLettersValidator(apelido2,errors,ParameterNames.APELIDO2, true));
			u.setPassword(ValidationUtils.passwordValidator(password,errors,ParameterNames.PASSWORD, true));
			u.setTelefono(ValidationUtils.stringValidator(telefono,errors,ParameterNames.TELEFONO, true));
			u.setNomeUsuario(ValidationUtils.namesWithNumbersValidator(nomeUsuario,errors,ParameterNames.NOME_USUARIO, true));
			u.setDNI(ValidationUtils.nifValidator(dni,errors,ParameterNames.DNI, true));
			
			Date fechanac = ValidationUtils.dateValidator(fnac,errors,ParameterNames.FECHA_NACIMIENTO, true);
			if(fechanac!=null) {
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(fechanac); 
		    calendar.add(Calendar.DAY_OF_YEAR, 1);  
		    fechanac = calendar.getTime(); 
		    u.setFechaNacimiento(fechanac);
			}

			d.setCiudad(ValidationUtils.namesOnlyLettersValidator(cidade,errors,ParameterNames.CIDADE, true));
			d.setIdProvincia(ValidationUtils.longValidator(provincia,errors,ParameterNames.PROVINCIA, true));
			d.setCalle(ValidationUtils.namesOnlyLettersValidator(calle,errors,ParameterNames.CALLE, true));
			d.setNumero(ValidationUtils.intValidator(numero,errors,ParameterNames.NUMERO, true));
			d.setCodPostal(ValidationUtils.intValidator(codPostal,errors,ParameterNames.COD_POSTAL, true));
			d.setPiso(ValidationUtils.intValidator(piso,errors,ParameterNames.PISO, false));
			d.setLetra(ValidationUtils.letterValidator(letra,errors,ParameterNames.LETRA, false));

			u.setDireccion(d);

			Usuario usuario = null;

			if(!errors.hasErrors()) {

				try {
					usuarioService.update(u);
					usuarioService.editDireccion(d, u);

					usuario = usuarioService.findById(u.getIdUsuario());

				} catch (DuplicateInstanceException e) {
					e.printStackTrace();
				} catch (DataException e) {
					e.printStackTrace();
				}

			}

			if(usuario == null) {
				errors.addError(ParameterNames.ACTION,ErrorCodes.EDIT_PROFILE_ERROR);	
			}


			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Fallo en la edicion: {}", errors);
				}
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.EDITPROFILE;				
			} else {				

				SessionManager.set(request, SessionAttributeNames.USER, usuario);		
				target = ViewPaths.HOME;				
			}


		} else if (Actions.INGRESAR.equalsIgnoreCase(action)){

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

			String iban = request.getParameter(ParameterNames.IBAN);
			String cantidad = request.getParameter(ParameterNames.CANTIDAD);

			//validamos
			Double cantidadd = Double.parseDouble(cantidad);
			iban = ValidationUtils.stringValidator(iban, errors, ParameterNames.IBAN, true);

			try {
				bancoService.ingresar(u.getIdUsuario(), cantidadd);
				u = usuarioService.findById(u.getIdUsuario());
			} catch (DataException e) {
				e.printStackTrace();
			}

			SessionManager.set(request, SessionAttributeNames.USER, u);

			target = ViewPaths.HOME;
			redirect = true;

		}else if (Actions.RETIRAR.equalsIgnoreCase(action)){

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

			String iban = request.getParameter(ParameterNames.IBAN);
			String cantidad = request.getParameter(ParameterNames.CANTIDAD);

			//validamos
			Double cantidadd = Double.parseDouble(cantidad);
			iban = ValidationUtils.stringValidator(iban, errors, ParameterNames.IBAN, true);

			if(cantidadd > u.getBanco()) {
				if (logger.isDebugEnabled()) {
					logger.debug("Añadido fallo, el saldo no es suficiente");
				}
				errors.addError(ParameterNames.CANTIDAD, ErrorCodes.NOT_ENOUGH_MONEY);
			} 


			if(!errors.hasErrors()) {
				try {

					bancoService.retirar(u.getIdUsuario(), cantidadd);

					u = usuarioService.findById(u.getIdUsuario());

				} catch (DataException e) {
					e.printStackTrace();
				}

				SessionManager.set(request, SessionAttributeNames.USER, u);
			} 


			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Fallo en retirar: {}", errors);
				}
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.RETIRAR;				
			} else {				

				target = ViewPaths.HOME;	
				redirect = true;
			}

		} else 	{
			logger.error("Action desconocida");
			logger.debug("Erro 404 - IP : "+ request.getRemoteAddr() +" - URI ");
			target = ViewPaths.ERROR_404;
			redirect =  true;
		}

		if((!Actions.CHANGE_LOCALE.equalsIgnoreCase(action)) && (target!=null)) {
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
