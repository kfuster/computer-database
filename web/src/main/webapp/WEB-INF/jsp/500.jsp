<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="../computer-database/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="../computer-database/resources/css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="../computer-database/resources/css/main.css" rel="stylesheet" media="screen">
	<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
			<span style="float: right"> <img style="cursor: pointer" src="../computer-database/resources/images/flag-en.png" onclick="$.fn.changeLanguage('en')"/>  <img style="cursor: pointer" src="../computer-database/resources/images/flag-fr.png" onclick="$.fn.changeLanguage('fr')"/></span>
		</div>
	</header>

	<section id="main">
		<div class="container">	
			<div class="alert alert-danger">
				<spring:message code="error.500" /> <br />
			</div>
		</div>
	</section>

	<script src="../computer-database/resources/js/jquery.min.js"></script>
	<script src="../computer-database/resources/js/bootstrap.min.js"></script>
	<script src="../computer-database/resources/js/dashboard.js"></script>

</body>
</html>