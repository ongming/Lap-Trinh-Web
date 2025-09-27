<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><head><title>Categories</title></head><body>
<div class="d-flex justify-content-between align-items-center mb-3">
  <form class="d-flex" method="get" action="">
    <input class="form-control me-2" type="search" name="q" value="${q}" placeholder="Search name..."/>
    <button class="btn btn-outline-primary">Search</button>
  </form>
  <a class="btn btn-primary" href="<c:url value='/admin/categories/create'/>">+ New</a>
</div>
<table class="table table-hover">
  <thead><tr><th>ID</th><th>Name</th><th>Icon</th><th></th></tr></thead>
  <tbody>
  <c:forEach var="it" items="${data.items}">
    <tr>
      <td>${it.id}</td><td>${it.cateName}</td><td>${it.icon}</td>
      <td class="text-end">
        <a class="btn btn-sm btn-secondary" href="<c:url value='/admin/categories/edit?id=${it.id}'/>">Edit</a>
        <a class="btn btn-sm btn-danger" href="<c:url value='/admin/categories/delete?id=${it.id}'/>">Delete</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<c:if test="${data.total > data.size}">
<nav><ul class="pagination">
  <c:forEach var="i" begin="1" end="${data.totalPages}">
    <li class="page-item ${i==data.page? 'active':''}">
      <a class="page-link" href="?q=${q}&page=${i}&size=${data.size}">${i}</a>
    </li>
  </c:forEach>
</ul></nav></c:if>
</body></html>
