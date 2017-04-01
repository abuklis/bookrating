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
    <fmt:message bundle="${loc}" key="locale.haveAnAccount" var="accountText"/>
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
    <script src="js/validation.js"></script>
</head>
<c:set var="user" value="user"/>
<c:set var="admin" value="admin"/>
<body>

<div class="tm-navbar-container-dark">
    <nav class="navbar navbar-full bg-inverse">
        <a class="home-link" href="controller?command=viewAllBooks">${bookrating}</a>
    </nav>

    <div class="lang">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="changeLocale"/>
            <input type="hidden" name="locale" value="ru"/>
            <input type="hidden" name="page" value="${pageContext.request.requestURI}"/>
            <button type="submit" style="border: 0; background: transparent">
                <img src="img/ru.png" class="lang-img" alt="ru" />
            </button>
        </form>
        <form action="controller" method="post">
            <input type="hidden" name="command" value="changeLocale"/>
            <input type="hidden" name="locale" value="en"/>
            <input type="hidden" name="page" value="${pageContext.request.requestURI}"/>
            <button type="submit" class="lang-btn">
                <img src="img/usa.png" class="lang-img" alt="en" />
            </button>
        </form>
    </div>
</div>

<div class="container-fluid">
    <div class="row gray-bg">

        <section id="tm-section-4" class="tm-section">
            <div class="tm-container">

                <h2 class="blue-text text-xs-center">${registration}</h2>
                <c:choose>
                    <c:when test="${not empty errorMessage}">
                        <div class="row text-xs-center error-text">User with login already exists.</div>
                    </c:when>
                </c:choose>
                <div class="col-md-3 col-lg-3 col-xl-3"></div>

                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
                    <form name="registrationForm" action="controller" method="POST" class="tm-contact-form" onsubmit="return validateForm()" >
                        <input type="hidden" name="command" value="registration"/>
                        <div class="form-group">
                            <input type="text" name="name" class="form-control reg-input" placeholder="Name" required min="2"/>
                            <div class="reg-error-message">Name should consist at least of 5 letters (or -)</div>
                        </div>
                        <div class="form-group">
                            <input type="text" name="login" class="form-control reg-input" placeholder="Login" required min="6"/>
                            <div class="reg-error-message">Login should consist of 6-10 letters, numbers</div>
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" class="form-control reg-input" placeholder="Password" required
                            min="6"/>
                            <div class="reg-error-message">
                                Password should consist at least 6
                                (min - 1 letter in uppercase, one in lower case and digit)
                            </div>
                        </div>
                        <div class="col-md-4">
                            <button type="submit" class="btn tm-light-blue-bordered-btn pull-xs-right">Submit</button>
                        </div>
                        <div class="col-md-4">
                            <div class="register-text">
                                ${accountText} <a href="controller?command=loginPage" class="register-link">${login}</a>
                            </div>
                        </div>
                    </form>
                </div> <!-- col -->
                <div class="col-md-3 col-lg-3 col-xl-3"></div>
            </div>
        </section>
    </div>
</div> <!-- container-fluid -->
</body>
</html>