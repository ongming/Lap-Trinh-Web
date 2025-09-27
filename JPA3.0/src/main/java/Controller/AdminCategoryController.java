package Controller;

import Service.CategoryService;
import entity.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.Page;

import java.io.IOException;

@WebServlet({ "/admin/categories", "/admin/categories/create", "/admin/categories/edit", "/admin/categories/delete" })
public class AdminCategoryController extends HttpServlet {
	private CategoryService service;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		if (uri.endsWith("/categories")) {
			String q = req.getParameter("q");
			int page = parseInt(req.getParameter("page"), 1);
			int size = parseInt(req.getParameter("size"), 10);
			Page<Category> data = service.search(q, page, size);
			req.setAttribute("data", data);
			req.setAttribute("q", q);
			req.getRequestDispatcher("/WEB-INF/views/admin/category-list.jsp").forward(req, resp);
		} else if (uri.endsWith("/create")) {
			req.setAttribute("model", new Category());
			req.getRequestDispatcher("/WEB-INF/views/admin/category-form.jsp").forward(req, resp);
		} else if (uri.endsWith("/edit")) {
			Long id = Long.valueOf(req.getParameter("id"));
			req.setAttribute("model", service.find(id));
			req.getRequestDispatcher("/WEB-INF/views/admin/category-form.jsp").forward(req, resp);
		} else if (uri.endsWith("/delete")) {
			Long id = Long.valueOf(req.getParameter("id"));
			service.delete(id);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String uri = req.getRequestURI();
		String name = req.getParameter("cateName");
		String icon = req.getParameter("icon");

		if (uri.endsWith("/create")) {
			Category c = new Category();
			c.setCate_name(name);
			c.setIcon(icon);
			service.save(c);
		} else if (uri.endsWith("/edit")) {
			Long id = Long.valueOf(req.getParameter("id"));
			Category c = service.find(id);
			c.setCate_name(name);
			c.setIcon(icon);
			service.update(c);
		}
		resp.sendRedirect(req.getContextPath() + "/admin/categories");
	}

	private int parseInt(String s, int d) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return d;
		}
	}
}
