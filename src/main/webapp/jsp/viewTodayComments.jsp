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

<div class="container-fluid">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8 comments-section">
            <c:choose>
            <c:when test="${comments.size()==0}">
            There is no comments today
            </c:when>
            <c:otherwise>
            <c:forEach items="${comments}" var="commentItem">
            <article class="box comment-box">
                <a href="controller?command=viewProfile&login=${commentItem.userLogin}" class="comment-user-link"> ${commentItem.userLogin}</a>
                <span class="user-comment-date"> ${commentItem.commentDate}</span>
                <c:choose>
                    <c:when test="${sessionScope.role == admin}">
                        <a href="controller?command=deleteComment&commentId=${commentItem.commentId}&bookId=${commentItem.bookId}" class="delete-comment-cross">
                            <img src="img/cross.png" alt="delete">
                        </a>
                    </c:when>
                </c:choose>
                <p>${commentItem.commentText}</p>
            </article>
            </c:forEach>
            </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-2"></div>
    </div>
</body>
</html>
