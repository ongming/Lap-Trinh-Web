package Controller;

import Service.VideoService;
import Service.CategoryService;
import entity.Video;
import entity.Category;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.Page;

import java.io.IOException;

@WebServlet({"/admin/videos", "/admin/videos/create", "/admin/videos/edit", "/admin/videos/delete"})
public class AdminVideoController extends HttpServlet {
    private VideoService service;
    private CategoryService cateService;


    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/videos")) {
            String q = req.getParameter("q");
            int page = parseInt(req.getParameter("page"), 1);
            int size = parseInt(req.getParameter("size"), 10);
            Page<Video> data = service.search(q, page, size);
            req.setAttribute("data", data); req.setAttribute("q", q);
            req.getRequestDispatcher("/WEB-INF/views/admin/video-list.jsp").forward(req, resp);
        } else if (uri.endsWith("/create")) {
            req.setAttribute("model", new Video());
            req.setAttribute("categories", cateService.search(null,1,100).items);
            req.getRequestDispatcher("/WEB-INF/views/admin/video-form.jsp").forward(req, resp);
        } else if (uri.endsWith("/edit")) {
            Long id = Long.valueOf(req.getParameter("id"));
            req.setAttribute("model", service.find(id));
            req.setAttribute("categories", cateService.search(null,1,100).items);
            req.getRequestDispatcher("/WEB-INF/views/admin/video-form.jsp").forward(req, resp);
        } else if (uri.endsWith("/delete")) {
            Long id = Long.valueOf(req.getParameter("id"));
            service.delete(id);
            resp.sendRedirect(req.getContextPath()+"/admin/videos");
        }
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String url = req.getParameter("url");
        String cateStr = req.getParameter("categoryId");
        Long categoryId = (cateStr == null || cateStr.isBlank()) ? null : Long.valueOf(cateStr);

        if (uri.endsWith("/create")) {
            Video v = new Video();
            v.setTitle(title); v.setDescription(description); v.setUrl(url);
            if (categoryId != null){ Category c = new Category(); c.setId(categoryId); v.setCategory(c); }
            User me = (User) req.getSession().getAttribute("user");  // đã login
            v.setUploader(me);
            service.save(v);
        } else if (uri.endsWith("/edit")) {
            Long id = Long.valueOf(req.getParameter("id"));
            Video v = service.find(id);
            v.setTitle(title); v.setDescription(description); v.setUrl(url);
            if (categoryId != null){ Category c = new Category(); c.setId(categoryId); v.setCategory(c); }
            else v.setCategory(null);
            service.update(v);
        }
        resp.sendRedirect(req.getContextPath()+"/admin/videos");
    }

    private int parseInt(String s, int d){ try { return Integer.parseInt(s);} catch(Exception e){ return d; } }
}
