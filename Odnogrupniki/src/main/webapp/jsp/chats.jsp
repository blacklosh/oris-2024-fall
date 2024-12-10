<%@ page import="ru.itis.dto.ChatDto" %>
<%@ page import="java.util.List" %><%--
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
            <h1>Твои чаты:</h1>
            <% for (ChatDto chat : (List<ChatDto>) request.getAttribute("chats")) { %>

            <%=chat.getId()%>: <a href="/chat?chat=<%=chat.getId()%>"><%=chat.getTitle()%></a><br/>

            <% } %>
        </td>
        <td style="width: 10%; background-color: black">
            <!-- Я ещё не придумал -->
        </td>
    </tr>
</table>



</body>
</html>
