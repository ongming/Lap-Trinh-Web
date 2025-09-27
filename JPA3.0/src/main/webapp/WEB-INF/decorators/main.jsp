<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title><sitemesh:write property="title"/></title>

  <!-- Bootstrap 5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>

  <!-- chèn <head> của trang con (meta, css riêng, v.v.) -->
  <sitemesh:write property="head"/>
</head>
<body>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg bg-body-tertiary border-bottom">
    <div class="container">
      <a class="navbar-brand fw-semibold" href="<c:url value='/'/>">JPA App</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#nav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="nav">
        <ul class="navbar-nav me-auto">
          <li class="nav-item"><a class="nav-link" href="<c:url value='/user/home'/>">Home</a></li>
          <li class="nav-item"><a class="nav-link" href="<c:url value='/category/setting'/>">Categories</a></li>
        </ul>
        <ul class="navbar-nav">
          <c:choose>
            <c:when test="${not empty sessionScope.user}">
              <li class="nav-item">
                <a class="nav-link" href="<c:url value='/profile'/>">
                  ${sessionScope.user.fullName != null ? sessionScope.user.fullName : sessionScope.user.userName}
                </a>
              </li>
              <li class="nav-item"><a class="nav-link" href="<c:url value='/logout'/>">Logout</a></li>
            </c:when>
            <c:otherwise>
              <li class="nav-item"><a class="nav-link" href="<c:url value='/login'/>">Login</a></li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>
    </div>
  </nav>

  <!-- Content -->
  <main class="container py-4">
    <sitemesh:write property="body"/>
  </main>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
	