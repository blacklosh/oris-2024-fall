<%@ page import="ru.itis.dto.ChatDto" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.itis.dto.MessageDto" %>
<%@ page import="ru.itis.dto.UserDataResponse" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 09.11.2024
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to my resource</title>

</head>
<body>

<table border="1" style="width: 100%">
    <tr>
        <td style="width: 10%">
            <!-- Навигация -->
            <center>

            </center>
            <table border="2" style="margin-right: auto; margin-left: auto; margin-top: 0">
                <tr>
                    <td>
                        <img src="/avatar?file=<%=((UserDataResponse)request.getAttribute("user")).getAvatarId()%>" width="50" height="50"/>
                        <%=((UserDataResponse)request.getAttribute("user")).getNickname()%>
                    </td>
                </tr>
                <tr>
                    <td><a href="/">Приветствие</a></td>
                </tr>
                <tr>
                    <td><a href="/main">Главная</a></td>
                </tr>
                <tr>
                    <td>Профиль</td>
                </tr>
                <tr>
                    <td>Друзья</td>
                </tr>
                <tr>
                    <td><a href="/chats">Мессенджер</a></td>
                </tr>
                <tr>
                    <td><a href="/logout">Выход</a></td>
                </tr>
            </table>
        </td>
        <td style="width: 60%; background-color: lightgray;">
            <!-- основной блок -->
            <h1>
                <%=((ChatDto) request.getAttribute("chat")).getTitle()%>
            </h1>

            Сообщения:
            <div id="messages">
                <% for (MessageDto message : (List<MessageDto>) request.getAttribute("messages")) { %>

                <img src="/avatar?file=<%=message.getAuthorAvatarId()%>" width="50" height="50"/>
                <%=message.getAuthorNickname()%>: <%=message.getText()%> <br/>

                <% } %>
            </div>

            <form>
                <label title="Новое сообщение:">
                    <input id="message" type="text" name="message"/>
                </label>
                <input id="button" type="button" value="Отправить"/>
            </form>
        </td>
        <td style="width: 10%; background-color: black">
            <!-- Я ещё не придумал -->
        </td>
    </tr>
</table>

<script>
    console.log("Script started");
    const url1 = new URL(window.location.href);
    const websocketUrl = 'ws://' + url1.host + '/websocket';
    var ws = new WebSocket(websocketUrl);
    ws.onopen = function () {
        console.log("Connected to ws")
    }
    ws.onerror = function () {
        console.log("Error with ws")
    }
    ws.onmessage = function processMessage(message) {
        console.log("Got message " + JSON.stringify(message.data));
        var json = JSON.parse(message.data);
        if(json.chatId === <%=((ChatDto) request.getAttribute("chat")).getId()%>) {
            var messageBlock = document.getElementById('messages');
            messageBlock.innerHTML += '<img src="/avatar?file=' + json.authorAvatarId + '" width="50" height="50"/>' + json.userName + ': ' + json.message + '<br/>';
        }
    }
    document.getElementById('button').onclick = function() {
        var textFieldValue = document.getElementById('message').value;
        document.getElementById('message').value = ""
        myFunction(textFieldValue);
    };

    function myFunction(value) {
        console.log("Нажали на кнопку и передали " + value);
        var sendJs = {
            "chatId": <%=((ChatDto) request.getAttribute("chat")).getId()%>,
            "message": value,
            "userName": "<%=((UserDataResponse)request.getAttribute("user")).getNickname()%>",
            "authorAvatarId": "<%=((UserDataResponse)request.getAttribute("user")).getAvatarId()%>"
        };
        var sendJsString = JSON.stringify(sendJs);
        console.log("Отправляю " + sendJsString);
        ws.send(sendJsString);
    }
</script>

</body>
</html>
