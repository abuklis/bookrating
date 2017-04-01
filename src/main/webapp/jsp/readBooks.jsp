<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
    <h2 class="blue-text text-xs-center"> Read books : </h2>
    <a href="controller?command=viewProfile&userId=${userId}">Back to profile</a>
    <div class="row ">
        <c:choose>
            <c:when test="${books.size()!=0}">
                <c:forEach items="${books}" var="bookItem">
                    <div class="col-md-4 margin-bottom-sm-3">
                        <div class="tm-3-col-box gray-bg book-item">
                            <div class="">
                                <img src="${bookItem.imageUrl}" alt="Image" class="img-fluid"> <a href="controller?command=deleteBookFromRead&bookId=${bookItem.bookId}" class="button alt">XXX</a>
                            </div>

                            <div class="book-item-title-author">
                                <c:forEach items="${bookItem.authors}" var="author" varStatus="authorLoop">
                                    <a href="controller?command=viewAuthor&authorId=${author.authorId}">${author.fullName}</a> ${!authorLoop.last ? ', ' : ''}
                                </c:forEach>
                                - ${bookItem.title}
                            </div>

                            <div>
                                <p class="tm-description-text desc-short-text">${bookItem.description}</p>
                                <div class="book-item-btn-more">
                                    <a href="controller?command=viewSingle&bookId=${bookItem.bookId}"  class="btn btn-default tm-normal-btn tm-gray-btn">More</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                There is no <books class=""></books>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>