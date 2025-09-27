package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import Dao.UserDao;          // dao của bạn
import entity.User;         // entity hiện tại
import utils.JPAUtil;        // nếu bạn có helper quản lý EMF

@WebServlet("/profile")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1MB
    maxFileSize = 2L * 1024 * 1024,       // 2MB
    maxRequestSize = 3L * 1024 * 1024     // 3MB
)
public class ProfileController extends HttpServlet {

    private UserDao userDao;

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // chỉ cho xem khi đã đăng nhập
        User sessionUser = (User) req.getSession().getAttribute("user");
        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User sessionUser = (User) req.getSession().getAttribute("user");
        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String fullName = req.getParameter("fullName");
        String phone    = req.getParameter("phone");
        Part avatarPart = req.getPart("avatar"); // có thể null nếu không chọn

        String avatarRelPath = null;
        if (avatarPart != null && avatarPart.getSize() > 0) {
            // Kiểm tra MIME đơn giản
            String ct = avatarPart.getContentType();
            if (ct == null || !(ct.equals("image/jpeg") || ct.equals("image/png"))) {
                req.setAttribute("err", "Chỉ chấp nhận JPG/PNG.");
                req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
                return;
            }

            // Tạo thư mục /Uploads/avatars (nằm cùng app - lưu ý khi redeploy)
            String uploadDir = req.getServletContext().getRealPath("/Uploads/avatars");
            new File(uploadDir).mkdirs();

            // Tạo tên file an toàn
            String ext = ct.equals("image/png") ? ".png" : ".jpg";
            String fileName = "ava_" + UUID.randomUUID() + ext;

            // Ghi file
            File dest = new File(uploadDir, fileName);
            avatarPart.write(dest.getAbsolutePath());

            // (Tuỳ chọn) chuẩn hoá quyền file/resize, v.v.
            try { Files.setPosixFilePermissions(dest.toPath(),
                 java.util.Set.of(java.nio.file.attribute.PosixFilePermission.OWNER_READ,
                                  java.nio.file.attribute.PosixFilePermission.OWNER_WRITE)); } catch (Exception ignore) {}

            // Lưu đường dẫn tương đối để hiển thị qua <c:url>
            avatarRelPath = "/Uploads/avatars/" + fileName;
        }

        try {
            // cập nhật DB
            userDao.updateProfile(sessionUser.getId(), fullName, phone, avatarRelPath);

            // cập nhật lại session (để navbar/ảnh đổi ngay)
            if (fullName != null) sessionUser.setFullName(fullName);
            if (phone != null)    sessionUser.setPhone(phone);
            if (avatarRelPath != null) sessionUser.setAvatar(avatarRelPath);
            req.getSession().setAttribute("user", sessionUser);

            req.setAttribute("msg", "Cập nhật thành công.");
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("err", "Có lỗi khi cập nhật hồ sơ.");
        }
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }
}
