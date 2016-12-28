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
<link href="../resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../resources/css/main.css" rel="stylesheet" media="screen">

<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.excilys.formation.pagination.Page"%>
<%@ page import="com.excilys.formation.dto.ComputerDto"%>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application -
				Computer Database </a>
				<span style="float: right"> <img src="../resources/images/flag-en.png" onclick="$.fn.changeLanguage('en')"/>  <img src="../resources/images/flag-fr.png" onclick="$.fn.changeLanguage('fr')"/></span>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${pageComputer.totalElements} <spring:message code="info.found"/></h1>
			<c:if test="${deleted != null}">
				<p style="color:red"><spring:message code="message.deleted"/></p>
			</c:if>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
						<c:if test="${not empty filter}">value="${filter}"</c:if>
							class="form-control" placeholder="<spring:message code="placeholder.search"/>" /> <input
							type="submit" id="searchsubmit" value="<spring:message code="button.filter"/>"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="addComputer.title" /></a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><spring:message code="button.edit"/></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="deleteComputer" method="POST">
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
						<th id="computerName" onclick="$.fn.columnSort('computerName');"><spring:message code="form.computerName"/></th>
						<th id="introduced" onclick="$.fn.columnSort('introduced');"><spring:message code="form.computerIntroduced"/></th>
						<!-- Table header for Discontinued Date -->
						<th id="discontinued" onclick="$.fn.columnSort('discontinued');"><spring:message code="form.computerDiscontinued"/></th>
						<!-- Table header for Company -->
						<th id="companyName" onclick="$.fn.columnSort('companyName');"><spring:message code="form.company"/></th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
				<my:pagination/>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<my:link currentPage="${pageComputer.page}" numberPages="${pageComputer.totalPages}" currentLimit="${pageComputer.elementsByPage}"/>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<my:link currentPage="${pageComputer.page}" totalElement="${pageComputer.totalElements}" limit="10"/>
				<my:link currentPage="${pageComputer.page}" totalElement="${pageComputer.totalElements}" limit="50"/>
				<my:link currentPage="${pageComputer.page}" totalElement="${pageComputer.totalElements}" limit="100"/>
			</div>
		</div>
	</footer>
	<script src="../resources/js/jquery.min.js"></script>
	<script src="../resources/js/bootstrap.min.js"></script>
	<script src="/internationalization.js"></script>
	<script src="../resources/js/dashboard.js"></script>
</body>
</html>