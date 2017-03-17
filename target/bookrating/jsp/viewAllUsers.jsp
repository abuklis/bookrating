<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>BookRating</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:100,300,400">
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="font-awesome-4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/magnific-popup.css">
    <link rel="stylesheet" href="css/templatemo-style.css">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<c:set var="user" value="user"/>
<c:set var="admin" value="admin"/>
<c:set var="Y" value="Y"/>
<c:set var="N" value="N"/>
<body>
<div class="tm-navbar-container-dark">
    <nav class="navbar navbar-full bg-inverse">
        <%--<button class="navbar-toggler hidden-md-up" type="button" data-toggle="collapse" data-target="#tmNavbar">--%>
        <%--&#9776;--%>
        <%--</button>--%>
        <ul class="collapse navbar-toggleable-sm" id="tmNavbar">
            <a class="home-link" href="controller?command=viewAllBooks">BookRating</a>
            <c:choose>
                <%--For admin--%>
                <c:when test="${sessionScope.role == admin}">
                    <ul class="nav navbar-nav header-nav">
                        <li class="nav-item">
                            <a href="controller?command=addAuthorPage" class="nav-link">AddAuthor</a></li>
                        <li class="nav-item">
                            <a href="controller?command=viewAllUsers" class="nav-link">View user's info</a></li>
                        <li class="nav-item">
                            <a href="controller?command=addBookPage" class="nav-link">Add Book</a></li>
                        <li class="nav-item">
                            <a href="controller?command=viewTodayComments" class="nav-link">View today comments</a></li>
                        <li class="nav-item">
                            <a href="controller?command=logout"  class="nav-link">Logout</a>
                        </li>
                    </ul>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
            <%--For user--%>
            <c:choose>
                <c:when test="${sessionScope.role == user}">
                    <div class="header-nav" >
                        <span class="welcome-text">Hi, </span><a href="controller?command=viewProfile&login=${sessionScope.user.login}"  class="nav-link">${sessionScope.user.login}</a>
                        <a href="controller?command=logout"  class="nav-link">Logout</a>
                    </div>
                </c:when>
                <c:when test="${sessionScope.role == admin}">
                </c:when>
                <c:otherwise>
                    <ul class="nav navbar-nav header-nav">
                        <li class="nav-item">
                            <a href="controller?command=registerPage" class="nav-link">Registration</a>
                        </li>
                        <li class="nav-item">
                            <a href="controller?command=loginPage&page=${pageContext.request.requestURI}" class="nav-link">Login</a>
                        </li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</div>

<div class="container-fluid book-list">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <div class="blue-text text-xs-center">Information about users in the system: </div>
            <table class="table">
                <thead> <tr> <th>Login</th> <th>Name</th> <th>Age</th> <th>Comments number</th>
                    <th>Marks number</th> <th> Status <th> </tr> </thead>
                <tbody>
                <c:forEach items="${users}" var="singleUser">
                    <tr>
                        <th scope="row"><a href="controller?command=viewProfile&login=${singleUser.login}">${singleUser.login}</a></th>
                        <td>${singleUser.name}</td> <td>${singleUser.age}</td>
                        <td></td>
                        <td></td>
                        <td>
                            <c:choose>
                                <c:when test="${singleUser.role==Y}">
                                    <span class="status banned">banned</span>
                                    <a href="controller?command=banUser&userId=${singleUser.userId}&ban=N" class="button alt">Unlock</a>
                                </c:when>
                                <c:when test="${singleUser.role==N}">
                                    <span class="status active">active</span>
                                    <a href="controller?command=banUser&userId=${singleUser.userId}&ban=Y" class="btn">BAN</a>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-2"></div>
    </div>
</div> <!-- container-fluid -->
<script src="js/jquery-1.11.3.min.js"></script>             <!-- jQuery (https://jquery.com/download/) -->
<script src="https://www.atlasestateagents.co.uk/javascript/tether.min.js"></script> <!-- Tether for Bootstrap, http://stackoverflow.com/questions/34567939/how-to-fix-the-error-error-bootstrap-tooltips-require-tether-http-github-h -->
<script src="js/bootstrap.min.js"></script>                 <!-- Bootstrap (http://v4-alpha.getbootstrap.com/) -->
<script src="js/jquery.singlePageNav.min.js"></script>      <!-- Single Page Nav (https://github.com/ChrisWojcik/single-page-nav) -->
<script src="js/jquery.magnific-popup.min.js"></script>     <!-- Magnific pop-up (http://dimsemenov.com/plugins/magnific-popup/) -->
</body>
</html>