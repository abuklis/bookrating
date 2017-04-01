<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.login" var="login"/>
    <fmt:message bundle="${loc}" key="locale.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="locale.registration" var="registration"/>
    <fmt:message bundle="${loc}" key="locale.welcomeText" var="welcomeText"/>
    <fmt:message bundle="${loc}" key="locale.bookrating" var="bookrating"/>
    <fmt:message bundle="${loc}" key="locale.haveNotAccount" var="noAccountText"/>
    <fmt:message bundle="${loc}" key="locale.signup" var="signup"/>
    <fmt:message bundle="${loc}" key="locale.loginErrorText" var="loginErrorText"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${bookrating}</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:100,300,400">
    <link rel="stylesheet" href="font-awesome-4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/magnific-popup.css">
    <link rel="stylesheet" href="css/style.css">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<c:set var="user" value="user"/>
<c:set var="admin" value="admin"/>
<body>

<div class="tm-navbar-container-dark">
    <nav class="navbar navbar-full bg-inverse">
        <a class="home-link" href="controller?command=viewAllBooks">${bookrating}</a>
    </nav>
</div>

<div class="container-fluid">
    <div class="row gray-bg">
        <section id="tm-section-4" class="tm-section">
            <div class="tm-container">
                <c:choose>
                    <c:when test="${not empty errorMessage}">
                        <div class="row text-xs-center error-text">${loginErrorText}.</div>
                    </c:when>
                </c:choose>
                <div class="col-md-4 col-lg-4 col-xl-4"></div>

                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 col-xl-4">
                    <h2 class="blue-text text-xs-center">${login}</h2>
                    <form name="loginForm" action="controller" method="POST" class="tm-contact-form">
                        <input type="hidden" name="command" value="login"/>
                        <div class="form-group">
                            <input type="text" id="login" name="login" class="form-control" placeholder="${login}" required/>
                        </div>
                        <div class="form-group">
                            <input type="password" id="password" name="password" class="form-control" placeholder="Password"  required/>
                        </div>
                        <div class="col-md-12">
                            <button type="submit" class="btn tm-light-blue-bordered-btn pull-xs-right login-button">${login}</button>
                        </div>
                        <div class="row register-text">
                            ${noAccountText} <a href="controller?command=registerPage" class="register-link">${signup}!</a>
                        </div>
                    </form>
                </div> <!-- col -->
                <div class="col-md-4 col-lg-4 col-xl-4"></div>
            </div>
        </section>
    </div>
</div> <!-- container-fluid -->
</body>
</html>