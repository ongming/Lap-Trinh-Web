<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tạo tài khoản mới</title>
<style>
body {
    font-family: Arial, sans-serif;
    background: #f1f1f1;
}
.register-box {
    width: 400px;
    margin: 50px auto;
    background: #fff;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0px 0px 10px #ccc;
}
.register-box h2 {
    text-align: center;
    margin-bottom: 20px;
}
.register-box input {
    width: 100%;
    padding: 10px;
    margin: 8px 0;
    border: 1px solid #ddd;
    border-radius: 5px;
}
.register-box button {
    width: 100%;
    padding: 12px;
    background: #007BFF;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}
.register-box button:hover {
    background: #0056b3;
}
.login-link {
    text-align: center;
    margin-top: 15px;
}
.login-link a {
    color: #007BFF;
    text-decoration: none;
}
.login-link a:hover {
    text-decoration: underline;
}
.alert {
    color: red;
    text-align: center;
}
</style>
</head>
<body>
    <div class="register-box">
        <h2>Tạo tài khoản mới</h2>
        <form action="${pageContext.request.contextPath}/register" method="post">
            <input type="text" name="username" placeholder="Tên đăng nhập" required>
            <input type="text" name="fullname" placeholder="Họ tên" required>
            <input type="email" name="email" placeholder="Nhập Email" required>
            <input type="text" name="phone" placeholder="Số điện thoại" required>
            <input type="password" name="password" placeholder="Mật khẩu" required>
            <input type="password" name="confirmPassword" placeholder="Nhập lại mật khẩu" required>
            <button type="submit">Tạo tài khoản</button>
        </form>
        
        <p class="alert">${alert}</p>

        <div class="login-link">
            Nếu bạn đã có tài khoản? <a href="${pageContext.request.contextPath}/Login.jsp">Đăng nhập</a>
        </div>
    </div>
</body>
</html>
