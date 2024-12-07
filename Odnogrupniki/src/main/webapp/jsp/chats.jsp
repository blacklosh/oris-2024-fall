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

    <% for (ChatDto chat : (List<ChatDto>) request.getAttribute("chats")) { %>

        <%=chat.getId()%>: <%=chat.getTitle()%> <br/>

    <% } %>

</body>
</html>
