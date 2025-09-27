<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><head><title>Video</title></head><body>
<form method="post" action="">
  <c:if test="${not empty model.id}"><input type="hidden" name="id" value="${model.id}"/></c:if>
  <div class="mb-3"><label class="form-label">Title</label>
    <input class="form-control" name="title" value="${model.title}" required/></div>
  <div class="mb-3"><label class="form-label">Description</label>
    <textarea class="form-control" name="description" rows="4">${model.description}</textarea></div>
  <div class="mb-3"><label class="form-label">URL</label>
    <input class="form-control" name="url" value="${model.url}" required/></div>
  <div class="mb-3"><label class="form-label">Category</label>
    <select class="form-select" name="categoryId">
      <option value="">-- none --</option>
      <c:forEach var="c" items="${categories}">
        <option value="${c.id}" ${model.category!=null && model.category.id==c.id? 'selected':''}>${c.cateName}</option>
      </c:forEach>
    </select>
  </div>
  <button class="btn btn-primary">Save</button>
  <a class="btn btn-link" href="<c:url value='/admin/videos'/>">Back</a>
</form>
</body></html>
