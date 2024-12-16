package ru.itis.servlet;

import ru.itis.dto.UserDataResponse;
import ru.itis.exception.IncorrectFileTypeException;
import ru.itis.service.FileService;
import ru.itis.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/avatar")
@MultipartConfig(fileSizeThreshold = 6291456, // 6 MB
        maxFileSize = 10485760L, // 10 MB
        maxRequestSize = 20971520L // 20 MB
)
public class FileServlet extends HttpServlet {

    private FileService fileService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        fileService = (FileService) config.getServletContext().getAttribute("fileService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileIdString = req.getParameter("file");
        UUID fileId = null;
        if (!(fileIdString == null) && !fileIdString.isBlank() && !fileIdString.equals("null")) {
            fileId = UUID.fromString(fileIdString);
        }
        fileService.downloadFile(fileId, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataResponse user = (UserDataResponse) req.getSession().getAttribute("user");

        UUID fileId = null;
        try {
            fileId = fileService.uploadFile(req.getPart("file"));
        } catch (IncorrectFileTypeException e) {
            resp.sendRedirect("/error?err=Invalid file format");
        }

        userService.setAvatar(user.getId(), fileId);
        resp.sendRedirect("/main");
    }

}
