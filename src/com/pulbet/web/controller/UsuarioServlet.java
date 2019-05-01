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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.cj.util.StringUtils;
import com.pulbet.web.config.ConfigurationManager;
import com.pulbet.web.config.ConfigurationParameterNames;
import com.pulbet.web.model.Carrito;
import com.pulbet.web.model.LineaCarrito;
import com.pulbet.web.util.CookieManager;
import com.pulbet.web.util.HttpUtils;
import com.pulbet.web.util.LocaleManager;
import com.pulbet.web.util.ParameterUtils;
import com.pulbet.web.util.SessionAttributeNames;
import com.pulbet.web.util.SessionManager;
import com.pulbet.web.util.ValidationUtils;
import com.pulbet.web.util.WebConstants;
import com.pulbet.web.util.WebUtils;
import com.pvv.pulbet.exceptions.DataException;
import com.pvv.pulbet.exceptions.DuplicateInstanceException;
import com.pvv.pulbet.exceptions.InstanceNotFoundException;
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
import com.pvv.pulbet.util.PasswordEncryptionUtil;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
					ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)); 

	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
					ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT)); 

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

		if(logger.isDebugEnabled()) {
			logger.debug(ParameterUtils.print(request.getParameterMap()));
		}
		
		String action = request.getParameter(ParameterNames.ACTION);

		Errors errors = new Errors();

		String target = null;
		boolean redirect = false;

		Locale userLocale = (Locale) SessionManager.get(request, WebConstants.USER_LOCALE);
		String idioma = LocaleManager.getIdioma(userLocale.toString());

		Map<String, String[]> mapa = new HashMap<String, String[]>(request.getParameterMap());
		String url = "";

		if(Actions.LOGIN.equalsIgnoreCase(action)) {

			//Recuperamos parametros
			String email = request.getParameter(ParameterNames.LOGIN_EMAIL);
			String password = request.getParameter(ParameterNames.PASSWORD);

			//Validamos paramtros
			password = ValidationUtils.passwordValidator(password,errors,ParameterNames.PASSWORD, true);
			email = ValidationUtils.emailValidator(email,errors,ParameterNames.REG_EMAIL, true);

			Usuario u = null;

			if(!errors.hasErrors()) {
				try {
					u = usuarioService.login(email, password);
				} catch (DataException e) {
					logger.warn(e.getMessage(),e);
				}
			}

			if (u == null) {
				errors.addError(ParameterNames.ACTION,ErrorCodes.AUTHENTICATION_ERROR);	
				errors.addError(ParameterNames.PASSWORD, ErrorCodes.NOT_CORRECT);
			}

			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Autenticacion fallida: {}", errors);
				}
				url = HttpUtils.createLinkToSelf(null, mapa);
				request.setAttribute(AttributeNames.ERRORS, errors);
				request.setAttribute(ParameterNames.URL, url);
				target = ViewPaths.LOGIN;				
			} else {				
				SessionManager.set(request, SessionAttributeNames.USER, u);		
				target = "";
				redirect = true;
			}
		}else if( Actions.LOGOUT.equalsIgnoreCase(action)) {

			Carrito c = (Carrito) SessionManager.get(request, SessionAttributeNames.CARRITO);
			List<LineaCarrito> lineas = new ArrayList<LineaCarrito>();
			c.setLineas(lineas);

			SessionManager.set(request, SessionAttributeNames.USER, null);
			SessionManager.set(request, SessionAttributeNames.CARRITO, c);

			target = "";
			redirect = true;

		} else if( Actions.CLOSE_ACCOUNT.equalsIgnoreCase(action)) {

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

			try {

				if(u.getBanco()>0.0) {
					errors.addError(ParameterNames.ACTION, ErrorCodes.TOO_MUCH_MONEY);
					request.setAttribute(AttributeNames.ERRORS, errors);
					target = ViewPaths.RETIRAR;
				}else {
					usuarioService.delete(u.getIdUsuario());
					SessionManager.set(request, SessionAttributeNames.USER, null);
					target = ViewPaths.HOME;
					redirect = true;
				}


			} catch (InstanceNotFoundException e) {
				logger.warn(e.getMessage(),e);
			} catch (DataException e) {
				logger.warn(e.getMessage(),e);
			}




		} else if( Actions.REMEMBERME.equalsIgnoreCase(action)) {

			String email = request.getParameter(ParameterNames.EMAIL);
			String checked = request.getParameter(ParameterNames.CHECKED);

			Boolean check = Boolean.valueOf(checked);

			Cookie cookie = CookieManager.getCookie(request, WebConstants.REMEMBERME);

			if(check){
				if(!StringUtils.isEmptyOrWhitespaceOnly(email)) {
					CookieManager.addCookie(response, WebConstants.REMEMBERME, email, "/", 365*24*60*60);
				}
			} else {
				if (cookie != null) {
					CookieManager.removeCookie(response, WebConstants.REMEMBERME, "/");
				}
			}


		}else if (Actions.PRE_REGISTRO.equalsIgnoreCase(action)){

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
			String repeatPassword = request.getParameter(ParameterNames.REPEAT_PASSWORD);
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
			password = ValidationUtils.passwordValidator(password,errors,ParameterNames.PASSWORD, true);
			repeatPassword = ValidationUtils.passwordValidator(repeatPassword,errors,ParameterNames.REPEAT_PASSWORD, true);
			
			if(!errors.hasErrors()) {
			if(password.equals(repeatPassword)) {
				u.setPassword(password);
			} else {
				errors.addError(ParameterNames.PASSWORD, ErrorCodes.NOT_EQUALS);
			}
			}
			
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
					logger.warn(e.getMessage(),e);
				} catch (MailException e) {
					logger.warn(e.getMessage(),e);
				} catch (DataException e) {
					logger.warn(e.getMessage(),e);
				}

			}

			if(u == null) {
				errors.addError(ParameterNames.ACTION,ErrorCodes.REGISTER_ERROR);	
			}

			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Fallo en el registro: {}", errors);
				}
				url = HttpUtils.createLinkToSelf(null, mapa);
				request.setAttribute(AttributeNames.ERRORS, errors);
				request.setAttribute(ParameterNames.URL, url);				
				target = "/usuario?"+ParameterNames.ACTION+"="+Actions.PRE_REGISTRO;				
			} else {				
				SessionManager.set(request, SessionAttributeNames.USER, u);		
				target = "";	
				redirect = true;
			}


		} 	else if (Actions.CHANGE_LOCALE.equalsIgnoreCase(action)) {
			
			String localeName = request.getParameter(ParameterNames.LOCALE);
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

			if((mapa.get(ParameterNames.URL)!=null) && !(mapa.get(ParameterNames.URL)[0].isEmpty())) {
				url = HttpUtils.createCallbackURL(request, mapa.get(ParameterNames.URL)[0]);
				
				if((mapa.get(ParameterNames.PAGE)!=null) && !(mapa.get(ParameterNames.PAGE)[0].isEmpty())) {
					url = HttpUtils.addParameterToURL(url, ParameterNames.PAGE,mapa.get(ParameterNames.PAGE)[0] , true);
				}
				target = url;
				response.sendRedirect(target);
			} else {
				response.sendRedirect(request.getHeader("referer"));
			}


		}else if (Actions.HISTORY.equalsIgnoreCase(action)){

			String fecha = request.getParameter(ParameterNames.FECHA);
			
			String pageValue = null;
			Integer page = null;
			if(mapa.get(ParameterNames.PAGE)!=null) {
				pageValue = mapa.get(ParameterNames.PAGE)[0];	
				page = Integer.valueOf(pageValue);
			}

			mapa.remove(ParameterNames.PAGE);

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

			Results<Apuesta> results = null;
			ApuestaCriteria apuestaTipo = new ApuestaCriteria();
			apuestaTipo.setIdUsuario(u.getIdUsuario());

			Calendar now = Calendar.getInstance();
			if(!(StringUtils.isEmptyOrWhitespaceOnly(fecha))) {
				
				if (fecha.equals("1")) {
					now.add(Calendar.DAY_OF_YEAR, -1);
					Date undia = now.getTime();
					apuestaTipo.setDesde(undia);
				} else if (fecha.equals("2")) {
					now.add(Calendar.DAY_OF_YEAR, -2);
					Date dosdias = now.getTime();
					apuestaTipo.setDesde(dosdias);
				} else {
					String desde = request.getParameter(ParameterNames.FECHA_DESDE);
					String hasta = request.getParameter(ParameterNames.FECHA_HASTA);
					
					Date fechauno = ValidationUtils.dateValidator(desde,errors,ParameterNames.FECHA_DESDE, false);
					if(fechauno!=null) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(fechauno); 
						calendar.add(Calendar.DAY_OF_YEAR, 1);  
						fechauno = calendar.getTime(); 
						apuestaTipo.setDesde(fechauno);
					}
					Date fechados = ValidationUtils.dateValidator(hasta,errors,ParameterNames.FECHA_HASTA, false);
					if(fechados!=null) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(fechados); 
						calendar.add(Calendar.DAY_OF_YEAR, 2);  
						fechados = calendar.getTime(); 
						apuestaTipo.setHasta(fechados);
					}
				}
					
				
			}

			

			try {

				page = WebUtils.
						getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
				

				results = apuestaService.findByCriteria(apuestaTipo,true,(page-1)*pageSize+1,pageSize);

				// Resultados de la busqueda (siempre preparar comodos para renderizar)
				request.setAttribute(AttributeNames.RESULTADOS, results.getPage());
				request.setAttribute(AttributeNames.TOTAL, results.getTotal());
				//Para comprobar se 
				request.setAttribute(AttributeNames.APUESTAS, results);

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
				target = ViewPaths.HISTORY;


			} catch (DataException e) {
				logger.warn(e.getMessage(),e);
			}

			


		} else if (Actions.OPENBETS.equalsIgnoreCase(action)){

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

			Results<Apuesta> apuestas = null;

			ApuestaCriteria apuestaTipo = new ApuestaCriteria();
			apuestaTipo.setIdUsuario(u.getIdUsuario());

			try {

				apuestas = apuestaService.findByCriteria(apuestaTipo,false, 1, 100);				

			} catch (DataException e) {
				logger.warn(e.getMessage(),e);
			}

			request.setAttribute(AttributeNames.APUESTAS, apuestas.getPage());
			target = ViewPaths.OPENBETS;

		}else if (Actions.PRE_EDIT.equalsIgnoreCase(action)){

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

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

					Provincia p = provinciaService.findById(u.getDireccion().getIdProvincia());

					provincias = provinciaService.findByPais(p.getIdPais());

					request.setAttribute(AttributeNames.PAIS, p.getIdPais());
					request.setAttribute(AttributeNames.PAISES, paises);
					request.setAttribute(AttributeNames.PROVINCIAS, provincias);

				}

			} catch (DataException e) {
				logger.warn(e.getMessage(),e);
			}

			if(id==null) {
				target = ViewPaths.EDITPROFILE;
			}

		} else if (Actions.EDITPROFILE.equalsIgnoreCase(action)){

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
			Usuario changes = new Usuario();
			changes.setIdUsuario(u.getIdUsuario());
			Direccion d =  u.getDireccion();
			//Recupero parametros

			String nombre = request.getParameter(ParameterNames.NOMBRE); 
			String email = request.getParameter(ParameterNames.REG_EMAIL);
			String apelido1 = request.getParameter(ParameterNames.APELIDO1);
			String apelido2 = request.getParameter(ParameterNames.APELIDO2);
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
			changes.setNome(ValidationUtils.namesOnlyLettersValidator(nombre,errors,ParameterNames.NOMBRE, true));
			changes.setEmail(ValidationUtils.emailValidator(email,errors,ParameterNames.REG_EMAIL, true));
			changes.setApelido1(ValidationUtils.namesOnlyLettersValidator(apelido1,errors,ParameterNames.APELIDO1, true));
			changes.setApelido2(ValidationUtils.namesOnlyLettersValidator(apelido2,errors,ParameterNames.APELIDO2, true));
			changes.setTelefono(ValidationUtils.stringValidator(telefono,errors,ParameterNames.TELEFONO, true));
			changes.setNomeUsuario(ValidationUtils.namesWithNumbersValidator(nomeUsuario,errors,ParameterNames.NOME_USUARIO, true));
			changes.setDNI(ValidationUtils.nifValidator(dni,errors,ParameterNames.DNI, true));

			Date fechanac = ValidationUtils.dateValidator(fnac,errors,ParameterNames.FECHA_NACIMIENTO, true);
			if(fechanac!=null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechanac); 
				calendar.add(Calendar.DAY_OF_YEAR, 1);  
				fechanac = calendar.getTime(); 
				changes.setFechaNacimiento(fechanac);
			}

			d.setCiudad(ValidationUtils.namesOnlyLettersValidator(cidade,errors,ParameterNames.CIDADE, true));
			d.setIdProvincia(ValidationUtils.longValidator(provincia,errors,ParameterNames.PROVINCIA, true));
			d.setCalle(ValidationUtils.namesOnlyLettersValidator(calle,errors,ParameterNames.CALLE, true));
			d.setNumero(ValidationUtils.intValidator(numero,errors,ParameterNames.NUMERO, true));
			d.setCodPostal(ValidationUtils.intValidator(codPostal,errors,ParameterNames.COD_POSTAL, true));
			d.setPiso(ValidationUtils.intValidator(piso,errors,ParameterNames.PISO, false));
			d.setLetra(ValidationUtils.letterValidator(letra,errors,ParameterNames.LETRA, false));

			changes.setDireccion(d);

			Usuario usuario = null;

			if(!errors.hasErrors()) {

				try {
					usuarioService.update(changes);
					usuarioService.editDireccion(d, changes);

					usuario = usuarioService.findById(changes.getIdUsuario());

				} catch (DuplicateInstanceException e) {
					logger.warn(e.getMessage(),e);
				} catch (DataException e) {
					logger.warn(e.getMessage(),e);
				}

			}

			if(usuario == null) {
				errors.addError(ParameterNames.ACTION,ErrorCodes.EDIT_PROFILE_ERROR);	
			}


			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Fallo en la edicion: {}", errors);
				}
				url = HttpUtils.createLinkToSelf(null, mapa);
				request.setAttribute(AttributeNames.ERRORS, errors);
				request.setAttribute(ParameterNames.URL, url);;				
				target = "/usuario?"+ParameterNames.ACTION+"="+Actions.PRE_EDIT;				
			} else {				

				SessionManager.set(request, SessionAttributeNames.USER, usuario);		
				target = "";
				redirect = true;
			}


		}else if (Actions.CHANGE_PASSWORD.equalsIgnoreCase(action)){

			Usuario usuario = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
			Usuario change = new Usuario();
			//Recupero parametros

			String password = request.getParameter(ParameterNames.PASSWORD); 
			String newPassword = request.getParameter(ParameterNames.NEW_PASSWORD); 
			String repeatPassword = request.getParameter(ParameterNames.REPEAT_PASSWORD);

			//Valido parametros
			password = ValidationUtils.passwordValidator(password, errors, ParameterNames.PASSWORD, true);
			newPassword = ValidationUtils.passwordValidator(newPassword, errors, ParameterNames.NEW_PASSWORD, true);
			repeatPassword = ValidationUtils.passwordValidator(repeatPassword, errors, ParameterNames.REPEAT_PASSWORD, true);


			logger.debug(password);
			logger.debug(newPassword);
			logger.debug(repeatPassword);
			Usuario u = null;

			if(!errors.hasErrors()) {

				if(newPassword.equals(repeatPassword)) {

					if(PasswordEncryptionUtil.checkPassword(password, usuario.getPassword())) {
						change.setIdUsuario(usuario.getIdUsuario());
						change.setPassword(newPassword);

						try {
							usuarioService.update(change);

							u = usuarioService.findById(change.getIdUsuario());
						} catch (DataException e) {
							logger.warn(e.getMessage(),e);
						}
					} else {
						errors.addError(ParameterNames.PASSWORD, ErrorCodes.NOT_CORRECT);
					}

				} else {
					errors.addError(ParameterNames.PASSWORD, ErrorCodes.NOT_EQUALS);
				}
			}

			if(u == null) {
				errors.addError(ParameterNames.ACTION,ErrorCodes.EDIT_PROFILE_ERROR);	
			}

			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Fallo en la edicion: {}", errors);
				}
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.EDITPROFILE;				
			} else {				
				SessionManager.set(request, SessionAttributeNames.USER, u);		
				target = ViewPaths.HOME;				
			}


		} else if (Actions.INGRESAR.equalsIgnoreCase(action)){

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

			String iban = request.getParameter(ParameterNames.IBAN);
			String cantidad = request.getParameter(ParameterNames.CANTIDAD);

			//validamos
			Double cantidadd = ValidationUtils.doubleValidator(cantidad, errors, ParameterNames.CANTIDAD, true);
			iban = ValidationUtils.ibanValidator(iban, errors, ParameterNames.IBAN, true);

			try {

				if(!errors.hasErrors()) {
					bancoService.ingresar(u.getIdUsuario(), cantidadd);
					u = usuarioService.findById(u.getIdUsuario());
					SessionManager.set(request, SessionAttributeNames.USER, u);
					target = ViewPaths.HOME;
					redirect = true;
				} else {

					request.setAttribute(AttributeNames.ERRORS, errors);
					target = ViewPaths.INGRESAR;
				}

			} catch (DataException e) {
				logger.warn(e.getMessage(),e);
			}

		}else if (Actions.RETIRAR.equalsIgnoreCase(action)){

			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

			String iban = request.getParameter(ParameterNames.IBAN);
			String cantidad = request.getParameter(ParameterNames.CANTIDAD);

			//validamos
			Double cantidadd = ValidationUtils.doubleValidator(cantidad, errors, ParameterNames.CANTIDAD, true);
			iban = ValidationUtils.ibanValidator(iban, errors, ParameterNames.IBAN, true);

			if(!errors.hasErrors()) {

				if(cantidadd > u.getBanco()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Añadido fallo, el saldo no es suficiente");
					}
					errors.addError(ParameterNames.CANTIDAD, ErrorCodes.NOT_ENOUGH_MONEY);
				}

			} 


			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Fallo en retirar: {}", errors);
				}
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.RETIRAR;				
			} else {		
				try {

					bancoService.retirar(u.getIdUsuario(), cantidadd);

					u = usuarioService.findById(u.getIdUsuario());

				} catch (DataException e) {
					logger.warn(e.getMessage(),e);
				}

				SessionManager.set(request, SessionAttributeNames.USER, u);

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
