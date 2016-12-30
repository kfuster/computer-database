<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../resources/css/jquery-ui.min.css" rel="stylesheet" media="screen">
    <link href="../resources/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="../resources/css/main.css" rel="stylesheet" media="screen">
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="dashboard"> Application - Computer
            Database </a>
        <span style="float: right"> <img src="../resources/images/flag-en.png" onclick="$.fn.changeLanguage('en')"/>  <img src="../resources/images/flag-fr.png" onclick="$.fn.changeLanguage('fr')"/></span>
    </div>

</header>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1><spring:message code="register.title" /></h1>
                <c:if test="${success}">
                    <p style="color:red"><spring:message code="message.added" /></p>
                </c:if>
                <sf:form id="registerForm" action="" method="POST" modelAttribute="userDto">
                    <fieldset>
                        <sf:errors cssClass="alert alert-danger" element="div" />
                        <div class="form-group">
                            <sf:label path="username"><spring:message code="form.userName" /></sf:label>
                            <sf:input class="form-control" id="username" path="username" placeholder="Username"/>
                            <sf:errors path="username" cssClass="alert alert-danger" element="div" />
                        </div>
                        <div class="form-group">
                            <sf:label path="password"><spring:message code="form.userPassword" /></sf:label>
                            <sf:input class="form-control" id="password"	path="password" placeholder="Password"/>
                            <sf:errors path="password" cssClass="alert alert-danger" element="div" />
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="<spring:message code="button.createAccount" />" class="btn btn-primary">
                        or <a href="dashboard" class="btn btn-default"><spring:message code="button.cancel" /></a>
                    </div>
                </sf:form>
            </div>
        </div>
    </div>
</section>
<script src="../resources/js/jquery.min.js"></script>
<script src="../resources/js/jquery-ui.min.js"></script>
<script src="../resources/js/jquery.validate.min.js"></script>
<script src="../resources/js/bootstrap.min.js"></script>
<script src="/internationalization.js"></script>
</body>
</html>