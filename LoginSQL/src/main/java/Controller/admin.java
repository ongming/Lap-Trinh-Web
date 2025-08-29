package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import Models.User;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/admin/home")
public class admin extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html;charset=UTF-8");

		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("account") != null) {
			User user = (User) session.getAttribute("account");

			// Chỉ cho phép admin

			resp.getWriter().println("<html><body>");
			resp.getWriter().println("<h2>Chào mừng Admin: " + user.getUserName() + "</h2>");
			resp.getWriter().println("<p>Bạn đã đăng nhập với quyền quản trị.</p>");
			resp.getWriter().println("<a href='" + req.getContextPath() + "/Login.jsp'>Đăng xuất</a>");
			resp.getWriter().println("</body></html>");
		}
	}
}
