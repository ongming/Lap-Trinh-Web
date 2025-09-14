package filter;

import java.io.IOException;

import entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RoleFilter {
	 public void doFilter (ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	        HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;

	        String uri = request.getRequestURI();

	        if(uri.endsWith("/login") || uri.contains("css") || uri.contains("js")){
	            chain.doFilter(req, res);
	            return;
	        }

	        HttpSession session = request.getSession(false);
	        User user = (session != null) ? (User) session.getAttribute("user") : null;

	        if(user == null){
	            response.sendRedirect(request.getContextPath() + "/login");
	            return;
	        }

	        if(uri.contains("/user/") && user.getRoleid() != 1){
	            response.sendError(403); return;
	        }
	        if(uri.contains("/manager/") && user.getRoleid() != 2){
	            response.sendError(403); return;
	        }
	        if(uri.contains("/admin/") && user.getRoleid() != 3){
	            response.sendError(403); return;
	        }

	        chain.doFilter(req, res);
	    }
}
