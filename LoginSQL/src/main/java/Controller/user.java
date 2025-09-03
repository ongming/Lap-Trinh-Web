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
@WebServlet(urlPatterns = "/user/home")
public class user extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("text/html;charset=UTF-8");

		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("account") != null) {
			User user = (User) session.getAttribute("account");

			resp.getWriter().println("<html><body>");
			resp.getWriter().println("<h2>Xin chào User: " + user.getUserName() + "</h2>");
			resp.getWriter().println("<p>Bạn đang ở trang người dùng.</p>");
			resp.getWriter().println("<a href='" + req.getContextPath() + "/logout'>Đăng xuất</a>");
			resp.getWriter().println("</body></html>");

		}
	}
}
