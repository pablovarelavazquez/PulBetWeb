package com.pulbet.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class ErrorServlet
 */
@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
	
	private static Logger logger = LogManager.getLogger(ErrorServlet.class);
	
    public ErrorServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int action = response.getStatus();
		
		if (404 == action) {
			logger.debug("Error "+action +" accediendo a " + request.getRequestURL()+ " desde IP: "+ request.getRemoteAddr());
			response.sendRedirect(request.getContextPath()+ViewPaths.ERROR_404);			
		}else if (500 == action) {
			logger.debug("Error "+action +" accediendo a " + request.getRequestURL()+ " desde IP: "+ request.getRemoteAddr());
			response.sendRedirect(request.getContextPath()+ViewPaths.ERROR_500);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
