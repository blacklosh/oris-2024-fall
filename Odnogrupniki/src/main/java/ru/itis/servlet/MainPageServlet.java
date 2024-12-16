package ru.itis.servlet;

import ru.itis.dto.UserDataResponse;
import ru.itis.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataResponse user = (UserDataResponse) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        req.setAttribute("userName", user.getNickname());
        req.getRequestDispatcher("jsp/main.jsp").forward(req, resp);
    }
}
