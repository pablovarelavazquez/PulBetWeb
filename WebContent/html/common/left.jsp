<%@page import="com.pulbet.web.controller.Actions"%>
<div id="sports">

	<c:if test="${not empty deportes}">
		<ul>
			<c:forEach items="${deportes}" var="d">
				<li><a href="">${d.getNome()}</a></li>
			</c:forEach>
		</ul>
	</c:if>

</div>