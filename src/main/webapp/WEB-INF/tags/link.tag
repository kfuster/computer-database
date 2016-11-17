<%@ tag body-content="empty"%>
<%@ attribute name="currentPage" required="true"%>
<%@ attribute name="numberPages" required="false"%>
<%@ attribute name="currentLimit" required="false"%>
<%@ attribute name="limit" required="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${currentPage != 0 && limit == null}">
	<%@ variable name-given="i"%>
	<c:set var="i" value="${currentPage-1}" />
	<c:if test="${i < 1}">
		<c:set var="i" value="1" />
	</c:if>
	<li><a href="?page=${i}&limit=${currentLimit}"
		aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
	</a></li>
	<%@ variable name-given="x"%>
	<c:set var="x" value="${currentPage-2}" />
	<c:if test="${x<1}">
		<c:set var="x" value="1" />
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
			<c:if test="${loop.current < numberPages}">
				<li><a href="?page=${loop.current}&limit=${currentLimit}">${loop.current}</a></li>
			</c:if>
		</c:forEach>
	</c:if>
	<c:set var="i" value="${currentPage+1}" />
	<c:if test="${i > numberPages}">
		<c:set var="i" value="${numberPages}" />
	</c:if>
	<li><a href="?page=${i}&limit=${currentLimit}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
	</a></li>
</c:if>
<c:if test="${limit != null && limit != 0 }">
	<button type="button" class="btn btn-default"
		onclick="location.href='?page=${currentPage}&limit=${limit}'">${limit}</button>
</c:if>