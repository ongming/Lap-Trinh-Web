<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập hệ thống</title>

<!-- Font Awesome để có icon đẹp -->
<link rel="stylesheet"href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<style>
body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	background: linear-gradient(135deg, #0f0f0f, #1c1c1c, #2a2a2a);
	margin: 0;
	padding: 0;
	color: #f1c40f;
}

/* Hộp login */
.login-box {
	width: 380px;
	margin: 120px auto;
	background: rgba(28, 28, 28, 0.9);
	padding: 40px;
	border-radius: 15px;
	backdrop-filter: blur(8px);
	border: 1px solid rgba(241, 196, 15, 0.4);
	box-shadow: 0 0 25px rgba(241, 196, 15, 0.5);
}

/* Tiêu đề */
.login-box h2 {
	text-align: center;
	margin-bottom: 25px;
	color: #f1c40f;
	letter-spacing: 1px;
}

/* Nhóm input có icon */
.input-group {
	position: relative;
	margin: 15px 0;
}

.input-group i {
	position: absolute;
	left: 12px;
	top: 50%;
	transform: translateY(-50%);
	color: #f1c40f;
}

/* Input */
.input-group input {
	width: 85%;
	padding: 14px 14px 14px 40px; /* chừa chỗ cho icon */
	margin: 20px auto; /* căn giữa ngang */
	display: block; /* để margin auto có hiệu lực */
	border: none;
	border-bottom: 2px solid #f1c40f;
	background: transparent;
	color: #f1c40f;
	font-size: 15px;
	transition: all 0.3s ease;
	outline: none;
}

.input-group input:focus {
	border-bottom: 2px solid #f39c12;
	box-shadow: 0 2px 8px rgba(241, 196, 15, 0.4);
}

/* Button */
.login-box button {
	width: 100%;
	padding: 14px;
	background: linear-gradient(135deg, #f1c40f, #d4ac0d);
	color: #111;
	font-weight: bold;
	border: none;
	border-radius: 8px;
	cursor: pointer;
	transition: 0.4s;
	box-shadow: 0 4px 15px rgba(241, 196, 15, 0.4);
}

.login-box button:hover {
	background: linear-gradient(135deg, #f39c12, #f1c40f);
	transform: translateY(-2px);
}

/* Thông báo */
.alert {
        font-weight: bold; /* làm chữ đậm */
        color: red;        /* bạn có thể thêm màu để nổi bật hơn */
        text-align: center;     /* căn giữa ngang */
        margin-top: 20px;       /* tạo khoảng cách trên */
    }
</style>
</head>
<body>
	<div class="login-box">
		<h2>Đăng nhập</h2>

		<form id="loginForm" action="${pageContext.request.contextPath}/login"
			method="post">

			<div class="input-group">
				<i class="fa fa-user"></i> <input type="text" name="username"
					placeholder="Tên đăng nhập" required>
			</div>

			<div class="input-group">
				<i class="fa fa-lock"></i> <input type="password" name="password"
					placeholder="Mật khẩu" required>
			</div>

			<c:if test="${param.error == 'permission'}">
				<p class="alert">Bạn không có quyền truy cập trang này.</p>
			</c:if>
			<p class="alert">${alert}</p>
			
			<button type="submit">Đăng nhập</button>
		</form>
	</div>

	<!-- Script clear textbox -->
	<script>
        window.onload = function() {
            let inputs = document.querySelectorAll("#loginForm input[type='text'], #loginForm input[type='password']");
            inputs.forEach(input => input.value = "");
        }
    </script>
</body>
</html>
