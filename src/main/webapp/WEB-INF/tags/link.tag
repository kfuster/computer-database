<%@ tag body-content="empty"%>
<%@ attribute name="currentPage" required="true"%>
<%@ attribute name="numberPages" required="false"%>
<%@ attribute name="currentLimit" required="false"%>
<%@ attribute name="totalElement" required="false"%>
<%@ attribute name="limit" required="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${currentPage != 0 && limit == null}">
	<%@ variable name-given="i"%>
	<c:set var="i" value="${currentPage-1}" />
	<c:if test="${i >= 1}">
		<li><a href="?page=${i}&limit=${currentLimit}"
		aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
	</a></li>
	</c:if>
	<%@ variable name-given="x"%>
	<c:set var="x" value="${currentPage-2}" />
	<c:if test="${x<1}">
		<c:set var="x" value="1" />
	</c:if>
	<c:if test="${x>1}">
		<li><a href="?page=1&limit=${currentLimit}">1</a></li>
		<li><a href="#">...</a></li>
	</c:if>
	<c:forEach begin="${x}" end="${currentPage-1}" varStatus="loop">
		<c:if test="${loop.current > 0 && loop.current < currentPage}">
			<li><a href="?page=${loop.current}&limit=${currentLimit}">${loop.current}</a></li>
		</c:if>
	</c:forEach>
	<li><a href="#"><b>${currentPage}</b></a></li>
	<c:if test="${ numberPages != 0}">
		<c:forEach begin="${currentPage +1}" end="${currentPage+2}"
			varStatus="loop">
			<c:if test="${loop.current <= numberPages}">
				<li><a href="?page=${loop.current}&limit=${currentLimit}">${loop.current}</a></li>
				<c:set var="x" value="${loop.current}" />
			</c:if>
		</c:forEach>
	</c:if>
	<c:set var="i" value="${currentPage+1}" />
	<c:if test="${x < numberPages}">
		<li><a href="#">...</a></li>
		<li><a href="?page=${numberPages}&limit=${currentLimit}">${numberPages}</a></li>
		<li><a href="?page=${x+1}&limit=${currentLimit}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
	</a></li>
	</c:if>
	
</c:if>
<c:if test="${limit != null && limit != 0 }">
	<%@ variable name-given="j"%>
	<c:set var="pageToGo" value="${currentPage}"/>
	<c:if test="${pageToGo > ((totalElement + limit - 1) / limit)}">
		<c:set var="pageToGo" value="${((totalElement + limit - 1) / limit)}" />
	</c:if>
	<fmt:formatNumber var="i" value="${pageToGo - (pageToGo % 1 == 0 ? 0 : 0.5)}" 
    type="number" pattern="#" />
	<button type="button" class="btn btn-default"
		onclick="location.href='?page=${i}&limit=${limit}'">${limit}</button>
</c:if>