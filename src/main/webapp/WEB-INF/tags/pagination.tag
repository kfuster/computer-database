<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="pageComputer" scope="application"
		type="com.excilys.formation.pagination.Page" />
<c:forEach items="${pageComputer.elems}" var="computer">
	<tr>
		<td class="editMode">
			<input type="checkbox" name="cb" class="cb" value="${computer.id}">
		</td>
		<td>
			<a href="editComputer?id=${computer.id}" onclick="">
            	${computer.name }
           	</a>
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