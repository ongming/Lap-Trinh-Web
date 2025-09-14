package Controller;

import Service.CategoryService;
import Service.impl.CategoryServiceImpl;
import entity.Category;
import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet({"/category/create", "/category/edit", "/category/delete"})
public class CategoryController extends HttpServlet {
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        if (action.equals("/category/create")) {
            req.getRequestDispatcher("/WEB-INF/views/category-form.jsp").forward(req, resp);
        } else if (action.equals("/category/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Category c = categoryService.getAllCategories().stream()
                    .filter(cat -> cat.getCate_id().equals(id))
                    .findFirst().orElse(null);

            req.setAttribute("category", c);
            req.getRequestDispatcher("/WEB-INF/views/category-edit.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String action = req.getServletPath();

        if (action.equals("/category/create")) {
            Category c = new Category();
            c.setCate_name(req.getParameter("cate_name"));
            c.setIcons(req.getParameter("icons"));
            categoryService.createCategory(c, user);
        } else if (action.equals("/category/edit")) {
            int id = Integer.parseInt(req.getParameter("cate_id"));
            Category c = new Category();
            c.setCate_id(id);
            c.setCate_name(req.getParameter("cate_name"));
            c.setIcons(req.getParameter("icons"));
            categoryService.updateCategory(c, user);
        } else if (action.equals("/category/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            categoryService.deleteCategory(id, user);
        }

        if (user.getRoleid() == 2) {
            resp.sendRedirect(req.getContextPath() + "/manager/home");
        } else {
            resp.sendRedirect(req.getContextPath() + "/user/home");
        }
    }
}
