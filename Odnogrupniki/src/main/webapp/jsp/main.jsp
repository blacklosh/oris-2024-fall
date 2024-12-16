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
                            <hr>
                            <form action="/avatar" method="post" enctype="multipart/form-data">
                                <input type="file" id="file" name="file" accept="image/jpeg"/>
                                <input type="submit" value="Изменить аватар"/>
                            </form>
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
            <td style="width: 60%; background-color: red;">
                <!-- основной блок -->
                <table border="3" style="width: 100%">
                    <tr>
                        <td><h1>Новость 1</h1></td>
                    </tr>
                    <tr>
                        <td><h1>Новость 2</h1></td>
                    </tr>
                    <tr>
                        <td><h1>Новость 3</h1></td>
                    </tr>
                    <tr>
                        <td><h1>Новость 4</h1></td>
                    </tr>
                    <tr>
                        <td><h1>Новость 5</h1></td>
                    </tr>
                    <tr>
                        <td><h1>Новость 6</h1></td>
                    </tr>
                </table>
            </td>
            <td style="width: 10%; background-color: black">
                <!-- Я ещё не придумал -->
            </td>
        </tr>
    </table>

</body>
</html>
