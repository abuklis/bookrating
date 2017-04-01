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
    <link rel="stylesheet" href="css/style.css">
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
