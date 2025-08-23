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
 * Servlet implementation class HelloServlet
 */
@WebServlet(urlPatterns = { "/helloworld" })
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		resp.setContentType("text/html");
		PrintWriter printWriter = resp.getWriter();
		HttpSession session = req.getSession(false);
		String nameFromSession = (String) session.getAttribute("name");
		String name = "";
		// Nhận cookie
		Cookie[] cookie = req.getCookies();
		for (Cookie c : cookie) {
			if (c.getName().equals("username")) {
				name = c.getValue();
			}
		}
		if (name.equals("")) {
			// chuyển sang trang LoginServlet
			resp.sendRedirect("/HelloServlet/Login.html");
		}
		// hiển thị lên trang bằng đối tượng PrintWriter()
		printWriter.println("Xin chao " + name + "<br>");
		printWriter.println("session cua ban la: " + nameFromSession);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
