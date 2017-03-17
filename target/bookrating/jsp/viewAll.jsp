<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

        <fmt:setLocale value="${sessionScope.locale}"/>
        <fmt:setBundle basename="locale" var="loc"/>
        <fmt:message bundle="${loc}" key="locale.login" var="login"/>
        <fmt:message bundle="${loc}" key="locale.logout" var="logout"/>
        <fmt:message bundle="${loc}" key="locale.registration" var="registration"/>
        <fmt:message bundle="${loc}" key="locale.welcomeText" var="welcomeText"/>
    </head>
    <body>
    <%@include file="header.jsp"%>

    <div class="container-fluid book-list">
        <div class="row popular">
                <div>Most popular books: </div>
                <c:forEach items="${popularBooks}" var="popularBook">
                    <div class="col-md-4 margin-bottom-sm-3">
                        <div class="tm-3-col-box gray-bg book-item">
                            <img src="${popularBook.key.imageUrl}" alt="Image" class="img-fluid book-image">
                            <div class="book-item-title-author">
                                <c:forEach items="${popularBook.key.authors}" var="author" varStatus="authorLoop">
                                    <a href="#">${author.fullName}</a> ${!authorLoop.last ? ', ' : ''}
                                </c:forEach> - ${popularBook.key.title}
                            </div>
                            <div class="">
                                <div class="book-item-btn-more">
                                    <a href="controller?command=viewSingle&bookId=${popularBook.key.bookId}"  class="btn btn-default tm-normal-btn tm-gray-btn">More</a>
                                </div>
                                rating = ${popularBook.value}
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

        <div class="row ">
                <c:forEach items="${books}" var="bookItem">
                    <div class="col-md-4 margin-bottom-sm-3">
                        <div class="tm-3-col-box gray-bg book-item">
                            <img src="${bookItem.imageUrl}" alt="Image" class="img-fluid book-image">
                            <div class="book-item-title-author">
                                <c:forEach items="${bookItem.authors}" var="author" varStatus="authorLoop">
                                    <a href="#">${author.fullName}</a> ${!authorLoop.last ? ', ' : ''}
                                </c:forEach> - ${bookItem.title}
                            </div>
                            <p class="tm-description-text desc-short-text">${bookItem.description}</p>
                            <div class="book-item-btn-more">
                                <a href="controller?command=viewSingle&bookId=${bookItem.bookId}"  class="btn btn-default tm-normal-btn tm-gray-btn">More</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>


        <c:if test="${currentPageNumber != 1}">
            <td><a href="controller?command=viewAllBooks&page=${currentPageNumber - 1}">Previous</a></td>
        </c:if>

        <%--For displaying Page numbers.
        The when condition does not display a link for the current page--%>
        <table border="1" cellpadding="5" cellspacing="5">
            <tr>
                <c:forEach begin="1" end="${pagesAmount}" var="i">
                    <c:choose>
                        <c:when test="${currentPageNumber eq i}">
                            <td>${i}</td>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </tr>
        </table>

        <%--For displaying Next link --%>
        <c:if test="${currentPageNumber lt pagesAmount}">
            <td><a href="controller?command=viewAllBooks&page=${currentPageNumber + 1}">Next</a></td>
        </c:if>
    </div> <!-- container-fluid -->
        <script src="js/jquery-1.11.3.min.js"></script>             <!-- jQuery (https://jquery.com/download/) -->
        <script src="https://www.atlasestateagents.co.uk/javascript/tether.min.js"></script> <!-- Tether for Bootstrap, http://stackoverflow.com/questions/34567939/how-to-fix-the-error-error-bootstrap-tooltips-require-tether-http-github-h -->
        <script src="js/bootstrap.min.js"></script>                 <!-- Bootstrap (http://v4-alpha.getbootstrap.com/) -->
        <script src="js/jquery.singlePageNav.min.js"></script>      <!-- Single Page Nav (https://github.com/ChrisWojcik/single-page-nav) -->
        <script src="js/jquery.magnific-popup.min.js"></script>     <!-- Magnific pop-up (http://dimsemenov.com/plugins/magnific-popup/) -->
    </body>
</html>