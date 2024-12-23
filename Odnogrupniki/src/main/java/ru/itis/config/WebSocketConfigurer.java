package ru.itis.config;

import ru.itis.dto.UserDataResponse;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class WebSocketConfigurer extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        UserDataResponse user = (UserDataResponse) httpSession.getAttribute("user");
        sec.getUserProperties().put("userId", user.getId());
    }
}
