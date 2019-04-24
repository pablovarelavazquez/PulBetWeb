package com.pulbet.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pulbet.web.controller.AttributeNames;
import com.pulbet.web.controller.ErrorCodes;
import com.pulbet.web.controller.Errors;
import com.pulbet.web.controller.ParameterNames;
import com.pulbet.web.controller.ViewPaths;
import com.pulbet.web.util.SessionAttributeNames;
import com.pulbet.web.util.SessionManager;

public class AuthenticationFilter implements Filter {
	
	private static Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter() {
    }

	public void doFilter(ServletRequest request, 
						 ServletResponse response, 
						 FilterChain chain) throws IOException, ServletException {				
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Errors errors = new Errors();
		
		if ((SessionManager.get(httpRequest, SessionAttributeNames.USER)==null)) {			
			// Usuario no autenticado
			logger.info("Usuario debe autenticarse");
			
			errors.addError(ParameterNames.ACTION, ErrorCodes.NOT_LOGGED);
			
			request.setAttribute(AttributeNames.ERRORS, errors);
			request.getRequestDispatcher(ViewPaths.LOGIN).forward(request, response);
			
			
		} else {
			// Usuario autenticado o accion no protegida
			chain.doFilter(request, response);
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
		
	}
	

}
