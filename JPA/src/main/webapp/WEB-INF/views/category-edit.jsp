<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Danh sách Category</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
body {
	font-family: Arial, sans-serif;
	background: #111; /* nền đen */
	color: #f1c40f; /* chữ vàng */
	text-align: center;
}

table {
	margin: 30px auto;
	border-collapse: separate; /* quan trọng để bo góc */
	border-spacing: 0; /* bỏ khoảng cách giữa các ô */
	width: 80%;
	background: #222;
	color: #f1c40f;
	border: 1px solid rgba(241, 196, 15, 0.4);
	box-shadow: 0 0 25px rgba(241, 196, 15, 0.5);
}

th, td {
	padding: 12px 20px;
	text-align: center;
	border-bottom: 1px solid rgba(241, 196, 15, 0.3);
	/* chỉ bo dưới, đẹp hơn */
}

.icon-img {
	width: 120px; /* thay đổi chiều rộng */
	height: auto; /* auto để giữ đúng tỉ lệ */
	border-radius: 10px; /* bo góc nếu muốn */
}

th {
	background: #111;
	font-size: 16px;
}

.edit-btn {
	padding: 14px 20px;
	background: linear-gradient(135deg, #f1c40f, #d4ac0d);
	color: #111;
	font-weight: bold;
	border: none;
	border-radius: 8px;
	cursor: pointer;
	transition: 0.4s;
	box-shadow: 0 4px 15px rgba(241, 196, 15, 0.4);
}

.edit-btn:hover {
	background: linear-gradient(135deg, #f39c12, #f1c40f);
	transform: translateY(-2px);
}

tr:last-child td {
	border-bottom: none; /* bỏ đường dưới cùng */
}

tr:hover {
	background-color: rgba(241, 196, 15, 0.1); /* highlight khi hover */
	transition: 0.3s;
}

img {
	width: 50px;
	height: 50px;
	object-fit: cover;
	border-radius: 8px; /* bo góc ảnh */
	box-shadow: 0 0 8px rgba(241, 196, 15, 0.5);
}

img:hover {
	transform: scale(1.1); /* phóng to 10% */
	box-shadow: 0 8px 15px rgba(241, 196, 15, 0.6); /* đổ bóng vàng */
}

th, td {
	padding: 12px;
	border: 1px solid #f1c40f;
	text-align: center;
}

th {
	background: #000;
}

img {
	width: 50px;
	height: 50px;
	object-fit: cover;
	border-radius: 5px;
}
</style>
</head>
<body>
	<h2>Danh sách Category</h2>
	<p>Người dùng hiện tại: ${user.fullName}</p>
	<p>
		Vai trò:
		<c:choose>
			<c:when test="${user.roleid == 0}">User</c:when>
			<c:when test="${user.roleid == 1}">Admin</c:when>
			<c:when test="${user.roleid == 2}">Manage</c:when>
		</c:choose>
	</p>

	<div style="width: 80%; margin: 0 auto;">
		<div style="width: 80%; margin: 0 auto; display: flex; gap: 20px;">
			<form action="${pageContext.request.contextPath}/category/create">
				<button type="submit" class="edit-btn">
					<i class="fa fa-plus"></i> Thêm
				</button>
			</form>
		</div>

		<table>
			<tr>
				<th>Tên Category</th>
				<th>Ảnh</th>
				<th>Sửa, Xóa</th>
			</tr>
			<c:forEach var="cat" items="${categories}">
				<tr>
					<td>${cat.cate_name}</td>
					<td><img
						src="${pageContext.request.contextPath}/Uploads/${cat.icon}"
						class="icon-img" alt="icon" width="100" height="100"></td>
					<td>
						<form action="${pageContext.request.contextPath}/category/setting"
							method="get" style="display: inline-block;">
							<input type="hidden" name="id" value="${cat.cate_id}" />
							<button type="submit" class="edit-btn">
								<i class="fa fa-pen"></i> Sửa
							</button>
						</form>

						<form action="${pageContext.request.contextPath}/category/delete"
							method="post" style="display: inline-block; margin-left: 10px;">
							<input type="hidden" name="id" value="${cat.cate_id}" />
							<button type="submit" class="edit-btn"
								style="background-color: #e74c3c;">
								<i class="fa fa-trash"></i> Xóa
							</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
		<h3>Chỉnh,Sửa thông tin Category</h3>
		<form>
			<input type="text" name="cate_name" placeholder="Tên Category"
				required style="padding: 8px; margin-right: 10px;">

			<!-- input file ẩn -->
			<input type="file" name="icon" id="fileInput" accept="image/*"
				required style="display: none;" onchange="previewImage(event)">

			<!-- nút chọn ảnh -->
			<button type="button" class="edit-btn"
				onclick="document.getElementById('fileInput').click();">
				<i class="fa fa-upload"></i> Chọn ảnh
			</button>

			<!-- preview ảnh -->
			<div style="margin-top: 10px;">
				<img id="preview" src="#" alt="Preview"
					style="display: none; width: 100px; height: 100px; object-fit: cover; border-radius: 5px;">
			</div>
		</form>

		<script>
			function previewImage(event) {
				const preview = document.getElementById('preview');
				const file = event.target.files[0];
				if (file) {
					const reader = new FileReader();
					reader.onload = function(e) {
						preview.src = e.target.result;
						preview.style.display = 'block';
					}
					reader.readAsDataURL(file);
				}
			}
		</script>

	</div>

</body>
</html>
