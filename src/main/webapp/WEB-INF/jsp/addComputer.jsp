<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/jquery-ui.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="com.excilys.formation.dto.CompanyDto"%>
</head>
<body>
	<jsp:useBean id="listCompanies" scope="application"
		type="java.util.List<CompanyDto>" />
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form id="addForm" action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName"
									placeholder="Computer name">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									name="introduced"
									placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									name="discontinued"
									placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<select class="form-control" id="companyId" name="companyId">
									<c:forEach items="${listCompanies}" var="companyDto">
										<option value="${companyDto.id}">${companyDto.name}</option>
									</c:forEach>
								</select> 
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/jquery-ui.min.js"></script>
	<script src="../js/jquery.validate.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script>
		$(function() {
		     $( "#introduced" ).datepicker({ dateFormat: 'yy-mm-dd',
		         changeMonth: true,
		         changeYear: true,
		         minDate: new Date(1970, 1 - 1, 1)});
		     $( "#discontinued" ).datepicker({ dateFormat: 'yy-mm-dd',
		         changeMonth: true,
		         changeYear: true});
		     $.validator.addMethod(
			    "validDate",
			    function(value, element) {
			    	if (value != "") {
			        	return value.match(/^\d{4}-0[1-9]|1[0-2]-\d{2}$/) ;
			    	}
			    	return true;
			    },
			    "Entrez la date au format yyyy-mm-dd."
			);
		    $('#addForm')
		     .validate({
		         rules : {
		        	 introduced : {
		        		 required : false,
		            	 validDate : true
		             },
		             discontinued : {
		            	 required : false,
		            	 validDate : true
		             },
		             computerName :{
		            	 required : true,
		            	 minLenght : 3
		             }
		         },
			     messages: {
			    	 computerName : "Entrez un nom",
			    	 introduced : "Entrez la date au format yyyy-mm-dd.",
			    	 discontinued : "Entrez la date au format yyyy-mm-dd."
	         	 }
		     });
		});
		function isValidDate(dateString) {
			var regEx = /^\d{4}-0[1-9]|1[0-2]-\d{2}$/;
			return dateString.match(regEx) != null;
		}
	</script>
</body>
</html>