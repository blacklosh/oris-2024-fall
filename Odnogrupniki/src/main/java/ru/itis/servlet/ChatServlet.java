package ru.itis.servlet;

import lombok.extern.slf4j.Slf4j;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    private ChatsService chatsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        chatsService = (ChatsService) config.getServletContext().getAttribute("chatsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataResponse user = (UserDataResponse) req.getSession().getAttribute("user");

        Long chatId;
        try {
            chatId = Long.parseLong(req.getParameter("chat"));
        } catch (NumberFormatException e) {
            resp.sendRedirect("/error?err=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
            return;
        }

        if(!chatsService.isUserInChat(user.getId(), chatId)) {
            resp.sendRedirect("/error?err=" + URLEncoder.encode("Не твой чат!", StandardCharsets.UTF_8));
            return;
        }

        req.setAttribute("user", user);
        req.setAttribute("messages", chatsService.findAllMessagesInChat(chatId));
        req.setAttribute("chat", chatsService.findChatById(chatId).get());

        req.getRequestDispatcher("jsp/chat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataResponse user = (UserDataResponse) req.getSession().getAttribute("user");

        String message = req.getParameter("message");
        Long chatId;
        try {
            chatId = Long.parseLong(req.getParameter("chat"));
        } catch (NumberFormatException e) {
            resp.sendRedirect("/error?err=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
            return;
        }

        if(!chatsService.isUserInChat(user.getId(), chatId)) {
            resp.sendRedirect("/error?err=" + URLEncoder.encode("Не твой чат!", StandardCharsets.UTF_8));
            return;
        }

        chatsService.sendNewMessage(MessageSendingDto.builder()
                .text(message)
                .chatId(chatId)
                .userId(user.getId())
                .build());
        resp.sendRedirect("/chat?chat="+chatId);
    }
}
