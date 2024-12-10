package ru.itis.servlet;

import lombok.extern.slf4j.Slf4j;
import ru.itis.dto.UserDataResponse;
import ru.itis.service.ChatsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet("/chats")
public class AllChatsServlet extends HttpServlet {

    private ChatsService chatsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        chatsService = (ChatsService) config.getServletContext().getAttribute("chatsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataResponse user = (UserDataResponse) req.getSession().getAttribute("user");

        req.setAttribute("chats", chatsService.findAllChatsByUserId(user.getId()));
        req.getRequestDispatcher("jsp/chats.jsp").forward(req, resp);
    }
}
