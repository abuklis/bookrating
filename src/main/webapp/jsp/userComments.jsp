<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%@include file="header.jsp"%>

<div id="page-wrapper">
    <div id="main-wrapper">
        <div class="container">
            <div class="row">
                <c:forEach items="${comments}" var="commentItem">
                    <div class="4u 12u(mobile)">
                        <article class="box comment-box">
                                ${commentItem.commentDate}
                            <c:choose>
                                <c:when test="${sessionScope.role == admin}">
                                    <a href="controller?command=deleteComment&commentId=${commentItem.commentId}&bookId=${book.bookId}" class="delete-comment-cross">
                                        <img src="<c:url value="/images/cross.png"/>" alt=""/>
                                    </a>
                                </c:when>
                            </c:choose>
                            <p>${commentItem.commentText}</p>
                        </article>
                    </div>
                </c:forEach>
            </div>
        </div>
        <a href="controller?command=viewProfile&userId=${userId}">Back to profile</a>
    </div>
</div>
</body>
</html>