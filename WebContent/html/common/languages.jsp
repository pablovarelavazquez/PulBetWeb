<%@ page import="java.util.HashMap, java.util.Map, com.pulbet.web.util.*"%>


<div id="languages">

[<a href="/PulBetWeb/usuario?action=locale&locale=gl-ES">GAL</a>
|<a href="/PulBetWeb/usuario?action=locale&locale=es-ES">ESP</a>
|<a href="/PulBetWeb/usuario?action=locale&locale=en-GB">ENG</a>]

<img id="userimage" src="<%=request.getContextPath()%>/images/gal-flag.png">

<p>${sessionScope["user-locale"]}</p>

</div>

