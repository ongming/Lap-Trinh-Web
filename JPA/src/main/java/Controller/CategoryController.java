package Controller;

import Service.CategoryService;
import Service.impl.CategoryServiceImpl;
import entity.Category;
import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet({ "/category/create", "/category/edit", "/category/delete" })
public class CategoryController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CategoryService categoryService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		req.setAttribute("categories", categoryService.getCategoriesByUser(user));
		req.getRequestDispatcher("/WEB-INF/views/category-edit.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		String action = req.getServletPath();
		
		if (action.equals("/category/create")) {
			Category c = new Category();
			c.setCate_name(req.getParameter("cate_name"));
			c.setIcon(req.getParameter("icons"));
			categoryService.createCategory(c, user);
		} else if (action.equals("/category/setting")) {
			int id = Integer.parseInt(req.getParameter("cate_id"));
			Category c = new Category();
			c.setCate_id(id);
			c.setCate_name(req.getParameter("cate_name"));
			c.setIcon(req.getParameter("icons"));
			categoryService.updateCategory(c, user);
		}

		else if (action.equals("/category/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			categoryService.deleteCategory(id, user);
		}

//		if (user.getRoleid() == 2) {
//			resp.sendRedirect(req.getContextPath() + "/manager/home");
//		} else {
//			resp.sendRedirect(req.getContextPath() + "/user/home");
//		}
	}
}
