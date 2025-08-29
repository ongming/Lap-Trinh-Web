package Controller;

import java.io.IOException;

import org.eclipse.tags.shaded.org.apache.bcel.classfile.Constant;

import Dao.impl.UserDaoImpl;
import Models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/register")
public class RegisterController extends HttpServlet {
	@Override
	
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
	            throws ServletException, IOException {
	        // Chỉ cần forward sang trang đăng ký
	        req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
	    }

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
	            throws ServletException, IOException {
	        // Lấy dữ liệu từ form
	        String username = req.getParameter("username");
	        String fullname = req.getParameter("fullname");
	        String email = req.getParameter("email");
	        String phone = req.getParameter("phone");
	        String password = req.getParameter("password");
	        String confirmPassword = req.getParameter("confirmPassword");

	        // Kiểm tra mật khẩu nhập lại
	        if (!password.equals(confirmPassword)) {
	            req.setAttribute("alert", "Mật khẩu nhập lại không khớp!");
	            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
	            return;
	        }

	        // Tạo user object
	        User user = new User();
	        user.setUserName(username);
	        user.setFullName(fullname);
	        user.setEmail(email);
	        user.setPhone(phone);
	        user.setPassWord(password);
	        user.setRoleid(2); // mặc định role User	
	        user.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));

	        // Lưu xuống database
	        UserDaoImpl userDao = new UserDaoImpl();
	        userDao.insert(user);

	        // Sau khi đăng ký thành công thì về trang login
	        resp.sendRedirect(req.getContextPath() + "/login.jsp");
	    }
	
}
