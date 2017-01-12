<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:forEach items="${pageComputer.elements}" var="computer">
	<tr>
		<td class="editMode">
			<input type="checkbox" id="computerId" name="cb" class="cb" value="${computer.id}">
		</td>
		<td>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<a id="editLink" href="editComputer?id=${computer.id}" onclick="">
			</sec:authorize>
            	${computer.name }
           	<sec:authorize access="hasRole('ROLE_ADMIN')">
           		</a>
       		</sec:authorize>
        </td>
        <td>
        	<c:if test="${computer.introduced != null }">
        		${computer.introduced}
        	</c:if>
        </td>
        <td>
        	<c:if test="${computer.discontinued != null }">
        		${computer.discontinued}
        	</c:if>
        </td>
        <td>
        	<c:if test="${computer.companyName != null }">
        		${computer.companyName}
        	</c:if>
        </td>
	</tr>
</c:forEach>