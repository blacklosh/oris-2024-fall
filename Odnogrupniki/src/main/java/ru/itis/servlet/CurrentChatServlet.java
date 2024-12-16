package ru.itis.servlet;

import ru.itis.dto.MessageSendingDto;
import ru.itis.dto.UserDataResponse;
import ru.itis.service.ChatsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/chat")
public class CurrentChatServlet extends HttpServlet {

    private ChatsService chatsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        chatsService = (ChatsService) config.getServletContext().getAttribute("chatsService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataResponse user = (UserDataResponse) req.getSession().getAttribute("user");
        String message = req.getParameter("message");
        Long chatId = null;
        try {
            chatId = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            req.setAttribute("err", "Incorrect chat id");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
        }
        if(!chatsService.isUserChat(user.getId(), chatId)) {
            req.setAttribute("err", "Not your chat!");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
        }
        chatsService.sendNewMessage(MessageSendingDto.builder()
                .userId(user.getId())
                .chatId(chatId)
                .text(message)
                .build());
        resp.sendRedirect("/chat?id="+chatId);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataResponse user = (UserDataResponse) req.getSession().getAttribute("user");
        Long chatId = null;
        try {
            chatId = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            req.setAttribute("err", "Incorrect chat id");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
        }
        if(!chatsService.isUserChat(user.getId(), chatId)) {
            req.setAttribute("err", "Not your chat!");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
        }
        req.setAttribute("user", user);
        req.setAttribute("chat", chatsService.findChatById(chatId).get());
        req.setAttribute("messages", chatsService.findAllMessagesInChat(chatId));
        req.getRequestDispatcher("jsp/chat.jsp").forward(req, resp);
    }
}
