<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<c:set var="YES" value="Y"/>
<c:set var="NO" value="N"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.login" var="login"/>
<fmt:message bundle="${loc}" key="locale.logout" var="logout"/>
<fmt:message bundle="${loc}" key="locale.registration" var="registration"/>
<fmt:message bundle="${loc}" key="locale.welcomeText" var="welcomeText"/>
<body>
<%@include file="header.jsp"%>

<div class="container-fluid">
    <div class="row book-item-info">
          <div class="col-md-2"></div>
          <div class="col-md-4 col-lg-4 col-xl-4 tm-news-item-img-container">
            <img src="${book.imageUrl}" alt="Image" class="img-fluid tm-news-item-img">
              <c:choose>
                  <c:when test="${avgRating == 0}">There is no reviews yet.</c:when>
                  <c:otherwise><span class="rating book-rating">${avgRating}</span></c:otherwise>
              </c:choose>
          </div>
          <div class="col-md-4 col-lg-4 col-xl-4 tm-news-container">
              <h2 class="tm-news-title dark-gray-text">
                  <div class="book-item-title-author">
                      <c:forEach items="${book.authors}" var="author" varStatus="authorLoop">
                         <a href="controller?command=viewAuthor&authorId=${author.authorId}" class="author-link">${author.fullName}</a> ${!authorLoop.last ? ', ' : ''}
                      </c:forEach>
                  </div>
                  <br>"${book.title}"</h2>
              <p><span class="dark-text field-title">Год издания: </span>${book.publishingYear}</p>
              <p><span class="dark-text field-title">Жанры: </span>
                  <c:forEach items="${book.genres}" var="genre" varStatus="genreLoop">
                      <a href="controller?command=findBooksBySingleGenre&genre=${genre.genreId}">${genre.genreName}</a> ${!genreLoop.last ? ', ' : ''}
                  </c:forEach>
              </p>
              <p class="tm-news-text"><span class="dark-text field-title">Описание: </span>${book.description}</p>


              <c:choose>
                  <c:when test="${sessionScope.role == userRole}">
                      <form name="leaveComment" action="controller" method="POST" >
                          <input type="hidden" name="command" value="leaveComment"/>
                          <input type="hidden" name="bookId" value="${book.bookId}"/>
                          <input type="hidden" name="userId" value="${sessionScope.user.userId}"/>
                          <input type="hidden" name="page" value="${pageContext.request.requestURI}"/>
                          <textarea class="form-control comment-area" rows="3" name ="commentText" required></textarea>
                          <input type="submit" class="btn btn-primary button alt leave-comment-btn" value="Leave"/>
                      </form>
                  </c:when>
                  <c:when test="${sessionScope.role== banned}">
                      <div class="ban-text">You cannot leave comments or marks because you're banned.</div>
                  </c:when>
                  <c:when test="${sessionScope.role == admin}"></c:when>
                  <c:otherwise>
                      To leave comment, please,<a href="controller?command=loginPage&page=${pageContext.request.requestURI}">enter</a> the system
                  </c:otherwise>
              </c:choose>
          </div>
          <div class="col-md-2">
              <div class="row">
                  <c:choose>
                      <c:when test="${sessionScope.role == userRole}">
                          <c:choose>
                            <c:when test="${rating==0}">
                              <form action="controller" method="POST">
                                  <input type="hidden" name="command" value="leaveRating"/>
                                  <input type="hidden" name="bookId" value="${book.bookId}"/>
                                  <input type="hidden" name="userId" value="${sessionScope.userId}"/>
                                  <span class="star-cb-group">
                                      <input type="radio" id="rating-5" name="rating" value="5" /><label for="rating-5">5</label>
                                      <input type="radio" id="rating-4" name="rating" value="4" /><label for="rating-4">4</label>
                                      <input type="radio" id="rating-3" name="rating" value="3" /><label for="rating-3">3</label>
                                      <input type="radio" id="rating-2" name="rating" value="2" /><label for="rating-2">2</label>
                                      <input type="radio" id="rating-1" name="rating" value="1" /><label for="rating-1">1</label>
                                      <input type="radio" id="rating-0" name="rating" value="0" class="star-cb-clear" /><label for="rating-0">0</label>
                                  </span>
                              </form>
                            </c:when>
                            <c:otherwise>
                              <div class="rating-text">Your rating = ${rating}/5 rating</div>
                            </c:otherwise>
                          </c:choose>
                      </c:when>
                      <c:when test="${sessionScope.role == banned}">
                          You cannot leave marks.
                      </c:when>
                      <c:when test="${sessionScope.role == admin}"></c:when>
                      <c:otherwise>
                          To leave a mark, please,<a href="controller?command=loginPage&page=${pageContext.request.requestURI}" class="nav-link">enter</a> the system
                      </c:otherwise>
                  </c:choose>
              </div>

              <c:choose>
                  <c:when test="${sessionScope.role == admin}">
                      <div class="row">
                          <a href="controller?command=deleteBook&bookId=${book.bookId}" class="btn btn-default tm-normal-btn tm-gray-btn admin-book-btn">Delete</a>
                      </div>
                      <div class="row">
                          <a href="controller?command=editBookPage&bookId=${book.bookId}" class="btn btn-default tm-normal-btn tm-gray-btn admin-book-btn">Edit</a>
                      </div>
                  </c:when>
                  <c:when test="${sessionScope.role == userRole}">
                      <c:choose>
                          <c:when test="${inRead == YES}">
                              <div class="row"> In read </div>
                          </c:when>
                          <c:otherwise>
                              <div class="row">
                                  <a href="controller?command=addToReadBooks&userId=${sessionScope.userId}&bookId=${book.bookId}">To read</a>
                              </div>
                          </c:otherwise>
                      </c:choose>

                      <c:choose>
                          <c:when test="${inFavorite == YES}">
                              <div class="row">In favorite</div>
                          </c:when>
                          <c:otherwise>
                              <div class="row">
                                  <a href="controller?command=addToFavoriteBooks&userId=${sessionScope.userId}&bookId=${book.bookId}">To favorite</a>
                              </div>
                          </c:otherwise>
                      </c:choose>
                  </c:when>
              </c:choose>
          </div>
      </div>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8 comments-section">
            <c:choose>
                <c:when test="${comments.size()!=0}">
                    <div class="comments-title">
                        Comments:
                    </div>
                    <c:forEach items="${comments}" var="comment">
                        <article class="box comment-box">
                            <a href="controller?command=viewProfile&userId=${comment.key.userId}" class="comment-user-link">${comment.key.login}</a>
                            <span class="user-comment-date"> ${comment.value.commentDate}</span>
                            <c:choose>
                                <c:when test="${sessionScope.user.role == admin}">
                                    <a href="controller?command=deleteComment&commentId=${comment.value.commentId}&bookId=${book.bookId}" class="delete-comment-cross">
                                        <img src="img/cross.png" alt="delete" class="delete-comment-image">
                                    </a>
                                </c:when>
                            </c:choose>
                            <p>${comment.value.commentText}</p>
                        </article>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="comments-title">
                        There is no comments.
                    </div>

                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-2"></div>
    </div>
  </div>



<script src="js/jquery-1.11.3.min.js"></script>             <!-- jQuery (https://jquery.com/download/) -->
<script src="https://www.atlasestateagents.co.uk/javascript/tether.min.js"></script> <!-- Tether for Bootstrap, http://stackoverflow.com/questions/34567939/how-to-fix-the-error-error-bootstrap-tooltips-require-tether-http-github-h -->
<script src="js/bootstrap.min.js"></script>                 <!-- Bootstrap (http://v4-alpha.getbootstrap.com/) -->
<script src="js/jquery.singlePageNav.min.js"></script>      <!-- Single Page Nav (https://github.com/ChrisWojcik/single-page-nav) -->
<script src="js/jquery.magnific-popup.min.js"></script>     <!-- Magnific pop-up (http://dimsemenov.com/plugins/magnific-popup/) -->
<script>
$('input[type=radio]').on('change', function() {
    $(this).closest("form").submit();
});
</script>

</body>
</html>