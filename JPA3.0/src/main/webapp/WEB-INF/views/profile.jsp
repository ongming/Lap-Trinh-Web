<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Profile</title>
</head>
<body>
<div class="row">
  <div class="col-lg-6 mx-auto">
    <div class="card shadow-sm">
      <div class="card-header">Cập nhật hồ sơ</div>
      <div class="card-body">
        <c:if test="${not empty msg}">
          <div class="alert alert-success">${msg}</div>
        </c:if>
        <c:if test="${not empty err}">
          <div class="alert alert-danger">${err}</div>
        </c:if>

        <form method="post" action="<c:url value='/profile'/>" enctype="multipart/form-data">
          <div class="mb-3">
            <label class="form-label">Full name</label>
            <input class="form-control" name="fullName"
                   value="${sessionScope.user.fullName}" required/>
          </div>
          <div class="mb-3">
            <label class="form-label">Phone</label>
            <input class="form-control" name="phone"
                   value="${sessionScope.user.phone}" pattern="^[0-9+()\\-\\s]{6,20}$"/>
          </div>
          <div class="mb-3">
            <label class="form-label">Avatar (JPG/PNG, ≤ 2MB)</label>
            <input class="form-control" type="file" name="avatar" accept="image/*"/>
          </div>

          <div class="d-flex align-items-center gap-3 mb-3">
            <img src="<c:url value='${sessionScope.user.avatar}'/>"
                 onerror="this.src='<c:url value=\"/assets/img/no-avatar.png\"/>'"
                 class="rounded-circle" width="72" height="72" alt="avatar"/>
            <span class="text-muted">Ảnh hiện tại</span>
          </div>

          <button class="btn btn-primary">Lưu thay đổi</button>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
