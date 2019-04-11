<%@ page import="java.util.HashMap, java.util.Map, com.pulbet.web.util.*"%>
<%
	//Map<String, String[]> mapa =  new HashMap<String, String[]>(request.getParameterMap());
	
	//String url = "";
	
	//url = ParameterUtils.URLBuilder(url, mapa);
	
%>

<div id="languages">

[<a href="/PulBetWeb/usuario?action=locale&locale=gl-ES">GAL</a>
|<a href="/PulBetWeb/usuario?action=locale&locale=es-ES">ESP</a>
|<a href="/PulBetWeb/usuario?action=locale&locale=en-GB">ENG</a>]

<p>${sessionScope["idioma"]} - ${sessionScope["user-locale"]}</p>

</div>

