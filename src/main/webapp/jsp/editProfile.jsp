<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <link rel="stylesheet" href="css/templatemo-style.css">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <%@include file="header.jsp"%>
    <div class="container-fluid">
        <div class="row gray-bg">

            <section id="tm-section-4" class="tm-section">
                <div class="tm-container">

                    <h2 class="blue-text text-xs-center">Edit profile</h2>

                    <form name="editProfile" action="controller" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="userId" value="${user.userId}"/>
                        <input type="hidden" name="command" value="editProfile"/>
                        <input type="hidden" name="oldImage" value="${user.avatar}"/>

                        <div class="form-group row">
                            <div class="col-md-1"></div>
                            <div class="col-md-2 single_view"><label for="name">Name:</label></div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" id="name" name="name" required value="${user.name}"/>
                            </div>
                            <div class="col-md-3"></div>
                        </div>
                        <div class="form-group row">
                            <div class="col-md-1"></div>
                            <div class="col-md-2 single_view"><label for="age">Age:</label></div>
                            <div class="col-md-6">
                                <input type="number" class="form-control" id="age" name="age" value="${user.age}"/>
                            </div>
                            <div class="col-md-3"></div>
                        </div>

                        <div class="form-group row">
                            <div class="col-md-1"></div>
                            <div class="col-md-2 single_view"> <label for="info">About myself:</label></div>
                            <div class="col-md-6">
                                <textarea class="form-control" rows="3" id="info" name ="info">
                                 ${user.info}</textarea>
                            </div>
                            <div class="col-md-3"></div>
                        </div>

                        <div class="form-group row">
                            <div class="col-md-1"></div>
                            <div class="col-md-2 single_view"><label for="newImage">Image:</label></div>
                            <div class="col-md-6">
                                <c:choose>
                                    <c:when test="${not empty user.avatar}">
                                        <a href="#" class="image featured"><img src="${user.avatar}" alt="" name="oldImage"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="#" class="image featured"><img src="images/user.png" alt="" name="oldImage"/></a>
                                    </c:otherwise>
                                </c:choose>
                                <input type="file" name="newImage" id="newImage"/>
                                <p class="help-block">Choose an image</p>
                            </div>
                            <div class="col-md-3"></div>
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

    </div>
</body>
</html>