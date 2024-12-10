<%@ page import="ru.itis.dto.ChatDto" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.itis.dto.MessageDto" %>
<%@ page import="ru.itis.dto.UserDataResponse" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 04.12.2024
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
                    <td><a href="/chats">Мессенджер</a> </td>
                </tr>
                <tr>
                    <td><a href="/logout">Выход</a></td>
                </tr>
            </table>
        </td>
        <td style="width: 60%; background-color: red;">
            <!-- основной блок -->
            <h1>Чат <%=((ChatDto) request.getAttribute("chat")).getTitle()%>:</h1>

            <div id="messages">
                <% for (MessageDto message : (List<MessageDto>) request.getAttribute("messages")) { %>

                <%=message.getAuthorNickname()%>: <%=message.getText()%><br/>

                <% } %>
            </div>

            <form>
                <input id="message" name="message"/>
                <input id='button' type="submit" value="Отправить сообщение"/>
            </form>
        </td>
        <td style="width: 10%; background-color: black">
            <!-- Я ещё не придумал -->
        </td>
    </tr>
</table>

<script>
    console.log("Script started");
    var url = new URL(window.location.href)
    var ws = new WebSocket("ws://" + url.host +"/ws")
    ws.onopen = function () {
        console.log("Ws connected");
    }
    ws.onerror = function () {
        console.log("Ws error");
    }
    ws.onmessage = function (message) {
        console.log("Получил сообщение " + message);
        var json = JSON.parse(message.data)
        if(json.chatId === <%=((ChatDto) request.getAttribute("chat")).getId()%>) {
            var messageBlock = document.getElementById("messages");
            messageBlock.innerHTML += json.userName + ": " + json.message + "<br/>";
        }
    }
    document.getElementById("button").onclick = (e) => {
        e.preventDefault();
        var messageText = document.getElementById("message").value;
        console.log("Нажали кнопку, дали текст " + messageText);
        document.getElementById("message").value = ""

        var sendJson = {
            "message": messageText,
            "chatId": <%=((ChatDto) request.getAttribute("chat")).getId()%>,
            "userName": "<%=((UserDataResponse) request.getAttribute("user")).getNickname()%>"
        }

        var sendJsonString = JSON.stringify(sendJson);
        console.log("Отправляю сообщение " + sendJson);
        ws.send(sendJsonString);
    }
</script>

</body>
</html>
