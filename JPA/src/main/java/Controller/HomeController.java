package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import Dao.impl.CategoryDAO;
import entity.User;

/**
 * Servlet implementation class HomeController
 */
@WebServlet({ "/user/home", "/manager/home", "/admin/home" })
public class HomeController extends HttpServlet {
	private CategoryDAO categoryDao = new CategoryDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		if (user.getRoleid() == 1 || user.getRoleid() == 3) {
			req.setAttribute("categories", categoryDao.findAll());
			req.getRequestDispatcher("/WEB-INF/views/user-home.jsp").forward(req, resp);
		} else if (user.getRoleid() == 2) {
			req.setAttribute("categories", categoryDao.findByUser(user));
			req.getRequestDispatcher("/WEB-INF/views/manager-home.jsp").forward(req, resp);
		}
	}
}