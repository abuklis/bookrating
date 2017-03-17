<c:set var="userRole" value="user"/>
<c:set var="banned" value="banned"/>
<c:set var="admin" value="admin"/>


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
                <c:when test="${sessionScope.role == userRole }">
                    <div class="header-nav" >
                        <span class="welcome-text">Hi, </span><a href="controller?command=viewProfile&userId=${sessionScope.userId}" class="nav-link">${sessionScope.login}</a>
                        <a href="controller?command=logout" class="nav-link">Logout</a>
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

            <div class="lang">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="changeLocale"/>
                    <input type="hidden" name="locale" value="ru"/>
                    <input type="hidden" name="page" value="${pageContext.request.requestURI}"/>
                    <button type="submit" style="border: 0; background: transparent">
                        <img src="img/ru.png" class="lang-img" alt="ru" />
                    </button>
                </form>
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="changeLocale"/>
                    <input type="hidden" name="locale" value="en"/>
                    <input type="hidden" name="page" value="${pageContext.request.requestURI}"/>
                    <button type="submit" class="lang-btn">
                        <img src="img/usa.png" class="lang-img" alt="en" />
                    </button>
                </form>
            </div>

        </ul>
    </nav>
</div>