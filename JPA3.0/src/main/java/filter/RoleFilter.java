package filter;

import java.io.IOException;

import entity.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")   // lọc tất cả request
public class RoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nếu cần khởi tạo
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();

        // bỏ qua login + static resources
        if (uri.endsWith("/login") || uri.contains("css") || uri.contains("js") || uri.contains("images")) {
            chain.doFilter(req, res);
            return;
        }
        
        String path = ((HttpServletRequest) req).getRequestURI().substring(((HttpServletRequest) req).getContextPath().length());

        boolean isStatic = path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/assets/") || path.startsWith("/Uploads/");
        boolean isAuthFree = path.equals("/login") || path.startsWith("/auth/");

        if (isStatic || isAuthFree) { chain.doFilter(req, res); return; }

        User user = (User) ((HttpServletRequest) req).getSession().getAttribute("user");
        if (user == null) {
            ((HttpServletResponse) res).sendRedirect(((HttpServletRequest) req).getContextPath() + "/login");
            return;
        }

        // Cho phép user tự cập nhật profile của chính mình
        if (path.equals("/profile")) { chain.doFilter(req, res); return; }

        // ... kiểm tra role cho /admin/*, /manager/* như bạn đang có
        chain.doFilter(req, res);

        
        HttpSession session = request.getSession(false);
        User user1 = (session != null) ? (User) session.getAttribute("user") : null;

        if (user1 == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // check role
        if (uri.contains("/user/") && user1.getRoleid() != 0) {
            response.sendRedirect(request.getContextPath() + "/login?error=permission");
            return;
        }
        if (uri.contains("/manager/") && user1.getRoleid() != 2) {
            response.sendRedirect(request.getContextPath() + "/login?error=permission");
            return;
        }
        if (uri.contains("/admin/") && user1.getRoleid() != 1) {
            response.sendRedirect(request.getContextPath() + "/login?error=permission");
            return;
        }


        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        // cleanup nếu cần
    }
}
