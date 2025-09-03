package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/logout")
public class LogoutController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Hủy session
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// Xóa cookie "username" nếu có
		Cookie cookie = new Cookie("username", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		resp.addCookie(cookie);

		resp.sendRedirect(req.getContextPath() + "/login1");
	}
}
