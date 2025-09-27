package Controller;

import Service.CategoryService;
import Service.impl.CategoryServiceImpl;
import entity.Category;
import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.io.File;

@WebServlet({ "/category/create", "/category/edit", "/category/delete", "/category/setting" })
@MultipartConfig
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
			String cateName = req.getParameter("cate_name");

			// ✅ lấy file upload
			Part filePart = req.getPart("icon"); // input name="icon"
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

			// ✅ đường dẫn thực tế trên server (deploy Tomcat)
			String uploadDir = req.getServletContext().getRealPath("/Uploads");
			File uploadDirFile = new File(uploadDir);
			if (!uploadDirFile.exists())
				uploadDirFile.mkdirs();

			// lưu file vào thư mục Uploads
			filePart.write(uploadDir + File.separator + fileName);

			// tạo Category
			Category c = new Category();
			c.setCateName(cateName);
			c.setIcon(fileName); // chỉ lưu tên file
			c.setUser(user);

			categoryService.createCategory(c, user);
		} else if (action.equals("/category/setting")) {
		    int id = Integer.parseInt(req.getParameter("cate_id"));

		    // tìm category cũ trong DB (chỉ của user đang login)
		    Category c = categoryService.findByIdAndUser(id);
		    if (c != null) {
		        c.setCateName(req.getParameter("cate_name"));

		        // xử lý file upload (nếu có)
		        Part filePart = req.getPart("icon");
		        if (filePart != null && filePart.getSize() > 0) {
		            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		            String uploadDir = req.getServletContext().getRealPath("/Uploads");
		            File uploadDirFile = new File(uploadDir);
		            if (!uploadDirFile.exists()) uploadDirFile.mkdirs();

		            filePart.write(uploadDir + File.separator + fileName);

		            c.setIcon(fileName); //````` cập nhật icon mới
		        }

		        categoryService.updateCategory(c);
		    }
		}
		else if (action.equals("/category/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			String uploadDir = req.getServletContext().getRealPath("/Uploads");

			categoryService.deleteCategory(id, user, uploadDir);
		}

		// if (user.getRoleid() == 2) {
		// resp.sendRedirect(req.getContextPath() + "/manager/home");
		// } else {
		// resp.sendRedirect(req.getContextPath() + "/user/home");
		// }
		resp.sendRedirect(req.getContextPath() + "/category/edit");
	}
}
