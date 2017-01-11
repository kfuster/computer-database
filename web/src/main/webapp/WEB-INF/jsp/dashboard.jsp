<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">

<!-- Bootstrap -->
<link href="../resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="../resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="../resources/css/main.css" rel="stylesheet" media="screen">
<jsp:useBean id="pageComputer" scope="request"
	type="com.excilys.formation.pagination.Page" />
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a> <span style="float: right"> <img style="cursor: pointer"
				src="../computer-database/resources/images/flag-en.png"
				onclick="$.fn.changeLanguage('en')" /> <img style="cursor: pointer"
				src="../computer-database/resources/images/flag-fr.png"
				onclick="$.fn.changeLanguage('fr')" /></span>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${pageComputer.totalElements}
				<spring:message code="info.found" />
			</h1>
			<c:if test="${param.success}">
				<p style="color: red">
					<spring:message code="message.success" />
				</p>
			</c:if>
			<c:if test="${deleted != null}">
				<p style="color: red">
					<spring:message code="message.deleted" />
				</p>
			</c:if>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							<c:if test="${not empty search}">value="${search}"</c:if>
							class="form-control"
							placeholder="<spring:message code="placeholder.search"/>" /> <input
							type="submit" id="searchsubmit"
							value="<spring:message code="button.filter"/>"
							class="btn btn-primary" />
						<input type="hidden"
							   value="${pageComputer.elementsByPage}" name="limit" />
						<input type="hidden"
							   value="${order}" name="order" />
						<input type="hidden"
							   value="${column}" name="column" />
					</form>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message
								code="addComputer.title" /></a>
						<a class="btn btn-default" id="editComputer" href="#"
							onclick="$.fn.toggleEditMode();"><spring:message
								code="button.edit" /></a>
					</sec:authorize>

				</div>
			</div>
		</div>

		<form id="deleteForm" action="deleteComputer" method="post">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th id="computerName" onclick="$.fn.columnSort('computerName');"
							style="cursor: pointer"><spring:message
								code="form.computerName" />
							<c:if test="${column == 'computerName' && order == 'ASC'}">
								<div class="arrow-up" style="float: right;"></div>
							</c:if> 
							<span style="clear: both">
							<c:if test="${column == 'computerName' && order == 'DESC'}">
								<div class="arrow-down" style="float: right"></div>
							</c:if>	
								</th>
						<th id="introduced" onclick="$.fn.columnSort('introduced');"
							style="cursor: pointer"><spring:message
								code="form.computerIntroduced" />
							<c:if test="${column == 'introduced' && order == 'ASC'}">
								<div class="arrow-up" style="float: right;"></div>
							</c:if> 
								<span style="clear: both">
								<c:if test="${column == 'introduced' && order == 'DESC'}">
								<div class="arrow-down" style="float: right"></div>
							</c:if>	
								</th>
						<!-- Table header for Discontinued Date -->
						<th id="discontinued" onclick="$.fn.columnSort('discontinued');"
							style="cursor: pointer"><spring:message
								code="form.computerDiscontinued" />
								<c:if test="${column == 'discontinued' && order == 'ASC'}">
									<div class="arrow-up" style="float: right;"></div>
								</c:if>  
								<span style="clear: both">
								<c:if test="${column == 'discontinued' && order == 'DESC'}">
								<div class="arrow-down" style="float: right"></div>
							</c:if>	
								</th>
						<!-- Table header for Company -->
						<th id="companyName" onclick="$.fn.columnSort('companyName');"
							style="cursor: pointer"><spring:message code="form.company" />
							<c:if test="${column == 'companyName' && order == 'ASC'}">
								<div class="arrow-up" style="float: right;"></div>
							</c:if>  
							<span style="clear: both">
								<c:if test="${column == 'companyName' && order == 'DESC'}">
								<div class="arrow-down" style="float: right"></div>
							</c:if>	
								</th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<my:pagination />
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<div style="float: left; margin-right: 5px;">
					<form action="#" method="GET">
						Page : <input type="number" style="width: 100px;" min="1"
							max="${pageComputer.totalPages}" value="${pageComputer.page}"
							name="page" /> <input type="hidden"
							value="${pageComputer.elementsByPage}" name="limit" />
							<input type="hidden"
							value="${search}" name="search" />
							<input type="hidden"
							value="${order}" name="order" />
							<input type="hidden"
							value="${column}" name="column" />
					</form>
				</div>

				<my:link currentPage="${pageComputer.page}"
					numberPages="${pageComputer.totalPages}"
					currentLimit="${pageComputer.elementsByPage}" />
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<div style="float: left">
					<form action="#" method="GET">
						<input type="number" style="width: 100px;" min="1" name="limit"
							value="${pageComputer.elementsByPage}" /> <input type="hidden"
							value="${pageComputer.page}" name="page" />
							<input type="hidden"
							value="${search}" name="search" />
							<input type="hidden"
							value="${order}" name="order" />
							<input type="hidden"
							value="${column}" name="column" />
					</form>
				</div>
				<my:link currentPage="${pageComputer.page}"
					totalElement="${pageComputer.totalElements}" limit="10" />
				<my:link currentPage="${pageComputer.page}"
					totalElement="${pageComputer.totalElements}" limit="50" />
				<my:link currentPage="${pageComputer.page}"
					totalElement="${pageComputer.totalElements}" limit="100" />
			</div>
		</div>
	</footer>
	<script src="../resources/js/jquery.min.js"></script>
	<script src="../resources/js/bootstrap.min.js"></script>
	<script src="/internationalization.js"></script>
	<script src="../resources/js/dashboard.js"></script>
</body>
</html>