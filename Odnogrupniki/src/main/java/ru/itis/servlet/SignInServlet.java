package ru.itis.servlet;

import ru.itis.dto.AuthResponse;
import ru.itis.dto.SignInRequest;
import ru.itis.dto.SignUpRequest;
import ru.itis.filter.AuthFilter;
import ru.itis.mapper.impl.UserMapperImpl;
import ru.itis.repository.impl.UserRepositoryImpl;
import ru.itis.service.UserService;
import ru.itis.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl(new UserRepositoryImpl(), new UserMapperImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignInRequest signUpRequest = SignInRequest.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();

        AuthResponse authResponse = userService.signIn(signUpRequest);
        if(authResponse.getStatus() == 0) {
            HttpSession session = req.getSession(true);
            session.setAttribute(AuthFilter.AUTHORIZATION, true);
            session.setAttribute("userName", authResponse.getUser().getNickname());
            resp.sendRedirect("/main");
        } else {
            resp.sendRedirect("/error?err=" + authResponse.getStatusDesc());
        }

    }

}
