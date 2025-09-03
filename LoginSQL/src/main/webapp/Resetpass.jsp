<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quên mật khẩu</title>
<style>
body {
    font-family: Arial, sans-serif;
    background: #f1f1f1;
}

.reset-box {
    width: 400px;
    margin: 100px auto;
    background: #fff;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0px 0px 10px #ccc;
}

.reset-box h2 {
    text-align: center;
    margin-bottom: 20px;
}

.reset-box input {
    width: 100%;
    padding: 10px;
    margin: 8px 0;
    border: 1px solid #ddd;
    border-radius: 5px;
}

.reset-box button {
    width: 100%;
    padding: 10px;
    background: #007BFF;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.reset-box button:hover {
    background: #0056b3;
}

.back-link {
    text-align: center;
    margin-top: 15px;
}

.back-link a {
    color: #007BFF;
    text-decoration: none;
}

.back-link a:hover {
    text-decoration: underline;
}

.alert {
    color: red;
    text-align: center;
}
</style>
</head>
<body>
    <div class="reset-box">
        <h2>Đặt lại mật khẩu</h2>

        <form action="${pageContext.request.contextPath}/ResetPassWord" method="post">
            <input type="email" name="email" placeholder="Email" required>
            <input type="text" name="username" placeholder="Tên đăng nhập" required>
            <input type="password" name="newPassword" placeholder="Mật khẩu mới" required>
            <input type="password" name="confirmPassword" placeholder="Nhập lại mật khẩu mới" required>
            <button type="submit">Xác nhận đổi mật khẩu</button>
        </form>

        <div class="back-link">
            <a href="${pageContext.request.contextPath}/Login.jsp">Quay lại đăng nhập</a>
        </div>

        <p class="alert">${alert}</p>
    </div>
</body>
</html>
