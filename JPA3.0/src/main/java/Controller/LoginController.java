package Controller;

import Service.UserService;
import Service.impl.UserServiceImpl;
import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(username, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            if (user.getRoleid() == 0) {
                resp.sendRedirect(req.getContextPath() + "/user/home");
            } else if (user.getRoleid() == 2) {
                resp.sendRedirect(req.getContextPath() + "/manager/home");
            } else { 
                resp.sendRedirect(req.getContextPath() + "/admin/home");
            }
        } else {
            req.setAttribute("alert", "Sai username hoặc password");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
