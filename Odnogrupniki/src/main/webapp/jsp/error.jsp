<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 09.11.2024
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ooops! Error</title>
</head>
<body>

<h1>Error:</h1>

<center>
    <img src="https://cdn.culture.ru/images/f306845c-56ef-5ce8-8183-68a541619508" style="width: 100%; height: 50%">

    <%=request.getParameter("err")%>

    <hr>
    <a href="/">На главную</a>
</center>



</body>
</html>
