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

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // check role
        if (uri.contains("/user/") && user.getRoleid() != 0) {
            response.sendRedirect(request.getContextPath() + "/login?error=permission");
            return;
        }
        if (uri.contains("/manager/") && user.getRoleid() != 2) {
            response.sendRedirect(request.getContextPath() + "/login?error=permission");
            return;
        }
        if (uri.contains("/admin/") && user.getRoleid() != 1) {
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
