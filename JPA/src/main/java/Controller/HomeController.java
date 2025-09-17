package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import Dao.impl.CategoryDaoImpl;
import Dao.impl.UserDaoImpl;
import entity.User;

/**
 * Servlet implementation class HomeController
 */
@WebServlet({ "/user/home", "/manager/home", "/admin/home" })
public class HomeController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CategoryDaoImpl categoryDao = new CategoryDaoImpl();
	private UserDaoImpl userDao = new UserDaoImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		if (user.getRoleid() == 0 || user.getRoleid() == 1) {
			req.setAttribute("categories", categoryDao.findAll());
			req.getRequestDispatcher("/WEB-INF/views/category-form.jsp").forward(req, resp);
		} else if (user.getRoleid() == 2) {
			req.setAttribute("categories", categoryDao.findByUser(user));
			req.getRequestDispatcher("/WEB-INF/views/category-form.jsp").forward(req, resp);
		}
	}
}