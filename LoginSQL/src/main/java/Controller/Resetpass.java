package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import Service.UserService;
import Service.impl.UserServiceImpl;

/**
 * Servlet implementation class Resetpass
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/ResetPassWord")
public class Resetpass extends HttpServlet {
	private static final long serialVersionUID = 1L;

  
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Hiển thị form đăng ký
		req.getRequestDispatcher("/Resetpass.jsp").forward(req, resp);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String username = req.getParameter("username");
		String newpassword = req.getParameter("newPassword");
		String confirmPassword = req.getParameter("confirmPassword");
		String email = req.getParameter("email");
		
		UserService service = new UserServiceImpl();
		String alertMsg = "";
		if(service.checkMatchPass(newpassword,confirmPassword)) {
			alertMsg = "Mật khẩu không trùng khớp!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("/ResetPassWord").forward(req, resp);
			return;
		}
		boolean isSuccess = service.resetPass(username, newpassword, email);
		if(isSuccess) {
			resp.sendRedirect(req.getContextPath() + "/login1");
		}
		else {
			alertMsg ="username hoặc email không trùng khớp!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("/ResetPassWord").forward(req, resp);
			return;
		}
	}

}
