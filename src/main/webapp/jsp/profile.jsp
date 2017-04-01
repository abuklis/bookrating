<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<c:set var="YES" value="Y"/>
<c:set var="NO" value="N"/>

<body>
<%@include file="header.jsp"%>

<div>
    <!-- Main -->
    <div class="container-fluid user-profile">
        <div class="row profile-desc">
            <div class="form-group row">
                <div class="col-md-3"></div>
                <div class="col-md-6  user-column">
                    <div class="row profile-item user-title text-center">
                        <span class="user-name">${user.login}</span>
                        <c:choose>
                            <c:when test="${sessionScope.role==banned}">
                                <span class="status banned">banned</span>
                            </c:when>
                            <c:otherwise>
                                <span class="status active">active</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="row">
                        <div class="col-md-3"></div>
                        <div class="col-md-6">
                            <c:choose>
                                <c:when test="${not empty user.avatar}">
                                   <img src="${user.avatar}" alt="" class="user-image"/>
                                </c:when>
                                <c:otherwise>
                                   <img src="img/user.png" alt="" class="user-image"/>
                                </c:otherwise>
                            </c:choose>
                            <div>
                                <c:choose>
                                    <c:when test="${user.login==sessionScope.login}">
                                        <a href="controller?command=editProfilePage&userId=${sessionScope.userId}" class="btn btn-default tm-normal-btn tm-green-btn">Edit</a>
                                    </c:when>
                                    <c:otherwise>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-md-3"></div>
                    </div>

                    <div class="row profile-item">
                        <div class="col-md-2"></div>
                        <div class="col-md-2 field-title">Name:</div>
                        <div class="col-md-6">${user.name}</div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="row profile-item">
                        <div class="col-md-2"></div>
                        <div class="col-md-2 field-title">Age:</div>
                        <div class="col-md-6">${user.age}</div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="row profile-item">
                        <div class="col-md-2"></div>
                        <div class="col-md-2 field-title">About myself:</div>
                        <div class="col-md-6">${user.info}</div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="row profile-item user-links-div">
                        <div class="col-md-4">
                            <a href="controller?command=viewFavoriteBooksCommand&userId=${user.userId}" class="user-link">Избранные <span class="rating number">${favoriteBooks.size()}</span></a>
                        </div>
                        <div class="col-md-4">
                            <a href="controller?command=viewReadBooks&userId=${user.userId}" class="user-link">Прочтённые <span class="rating number">${readBooks.size()}</span></a>
                        </div>
                        <div class="col-md-4">
                            <a href="controller?command=viewUserComments&userId=${user.userId}" class="user-link">Комментарии <span class="rating number">${comments.size()}</span></a>
                        </div>
                    </div>

                </div>
                <div class="col-md-3">
                </div>

            </div>
        </div>
    </div>

</div>

<!-- Scripts -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/jquery.dropotron.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/skel-viewport.min.js"></script>
<script src="assets/js/util.js"></script>
<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
<script src="assets/js/main.js"></script>

</body>
</html>