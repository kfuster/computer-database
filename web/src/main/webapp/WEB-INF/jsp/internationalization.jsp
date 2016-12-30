<%@page contentType="text/javascript" pageEncoding="UTF-8"
%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
var messages = [];

<c:forEach var="key" items="${keys}">
	messages["<spring:message text='${key}' />"] = "<spring:message code='${key}'/>";
	<c:if test="${key==\"form.date.regex\"}">
		messages["<spring:message text='${key}' />"] = <spring:message code='${key}'/>;
	</c:if>
</c:forEach>