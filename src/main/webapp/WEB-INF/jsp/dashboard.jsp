<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %> 
<%@ page import="com.excilys.formation.pagination.Page"%>
<%@ page import="com.excilys.formation.dto.ComputerDto"%>
</head>
<body>
	<jsp:useBean id="pageComputer" scope="application"
		type="com.excilys.formation.pagination.Page<ComputerDto>" />
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${pageComputer.totalElement} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
						<c:if test="${not empty filter}">value="${filter}"</c:if>
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
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
						<th id="computerName" onclick="$.fn.columnSort('computerName');">Computer name</th>
						<th id="introduced" onclick="$.fn.columnSort('introduced');">Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th id="discontinued" onclick="$.fn.columnSort('discontinued');">Discontinued date</th>
						<!-- Table header for Company -->
						<th id="companyName" onclick="$.fn.columnSort('companyName');">Company</th>
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
				<my:link currentPage="${pageComputer.page}" numberPages="${pageComputer.nbPages}" currentLimit="${pageComputer.elemByPage}"/>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<my:link currentPage="${pageComputer.page}" totalElement="${pageComputer.totalElement}" limit="10"/>
				<my:link currentPage="${pageComputer.page}" totalElement="${pageComputer.totalElement}" limit="50"/>
				<my:link currentPage="${pageComputer.page}" totalElement="${pageComputer.totalElement}" limit="100"/>
			</div>
		</div>
	</footer>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/dashboard.js"></script>
</body>
</html>