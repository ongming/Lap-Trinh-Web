package cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        PrintWriter printWriter = resp.getWriter();

        if (user.equals("trung") && pass.equals("123")) {
            // Tạo session trước khi commit response
            HttpSession session = req.getSession();
            session.setAttribute("name", user);
            session.setMaxInactiveInterval(300); // 5 phút

            // Tạo và thêm cookie
            Cookie cookie = new Cookie("username", user);
            cookie.setMaxAge(30); // 30 giây
            resp.addCookie(cookie);

            // Chuyển hướng đến trang thành công
            resp.sendRedirect("/hello/helloworld");
        } else {

            resp.sendRedirect("/hello/Login.html");
        }
        printWriter.close(); // Đóng PrintWriter (tùy chọn, container có thể tự quản lý)
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}