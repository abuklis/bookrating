<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>BookRating</title>
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
        <%--<button class="navbar-toggler hidden-md-up" type="button" data-toggle="collapse" data-target="#tmNavbar">--%>
        <%--&#9776;--%>
        <%--</button>--%>

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
    </nav>
</div>

<div class="container-fluid">
    <div class="row gray-bg">

        <section id="tm-section-4" class="tm-section">
            <div class="tm-container">

                <h2 class="blue-text text-xs-center">Add book</h2>

                <c:choose>
                    <c:when test="${not empty errorMessage}">
                        <div class="row text-xs-center error-text">Such book already exists.</div>
                    </c:when>
                </c:choose>

                <form name="addBookForm" action="controller" method="POST" enctype="multipart/form-data" class="tm-contact-form">
                    <input type="hidden" name="command" value="addBook"/>
                    <input type="hidden" name="page" value="${pageContext.request.requestURI}"/>

                    <div class="form-group row">
                        <div class="col-md-1"></div>
                        <div class="col-md-2 single_view"><label for="author">Author: </label></div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="author" name="author"/>
                        </div>
                        <div class="col-md-3"></div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-1"></div>
                        <div class="col-md-2 single_view"><label for="title">Title:</label></div>
                        <div class="col-md-6"><input type="text" class="form-control" id="title" name="title" required/></div>
                        <div class="col-md-3"></div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-1"></div>
                        <div class="col-md-2 single_view"> <label for="description">Description:</label></div>
                        <div class="col-md-6"><textarea class="form-control" rows="3" id="description" name ="description" required></textarea></div>
                        <div class="col-md-3"></div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-1"></div>
                        <div class="col-md-2 single_view"><label for="publishingYear">Publishing Year:</label></div>
                        <div class="col-md-6"><input type="text" class="form-control" id="publishingYear" name="publishingYear" required/></div>
                        <div class="col-md-3"></div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-1"></div>
                        <div class="col-md-2 single_view"><label for="imageUrl">Image:</label></div>
                        <div class="col-md-6">
                            <input type="file" id="imageUrl" name="imageUrl" accept="image/*">
                            <p class="help-block">${news_image_help_text}</p>
                        </div>
                        <div class="col-md-3"></div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-12 single_view">
                            Genres:
                        </div>
                        <c:forEach items="${genres}" var="genreItem">
                            <div class="col-md-3">
                                <input type="checkbox" value="${genreItem.genreId}" name="genre" id="${genreItem.genreId}"/>
                                <label for="${genreItem.genreId}">${genreItem.genreName}</label>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="row">
                        <div class="col-md-5"></div>
                        <div class="col-md-2"><input type="submit" class="btn btn-primary button alt" value="Submit"/></div>
                        <div class="col-md-5"></div>
                    </div>
                </form>

            </div>
        </section>
    </div>

</div> <!-- container-fluid -->
</body>
</html>