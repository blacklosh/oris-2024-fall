<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 09.11.2024
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up page</title>
</head>
<body>

<h1>Welcome to my resource:)</h1>

<h2>Please enter your data:</h2>

<form action="/signUp" method="post">
    <label for="email">Enter your email:</label>
    <input id="email" name="email" type="email" placeholder="fedor@main.ru" required/>
    <label for="nickname">Enter your nickname:</label>
    <input id="nickname" name="nickname" placeholder="?" required/>
    <label for="password">Enter your password:</label>
    <input id="password" name="password" type="password" required/>
    <input type="submit" value="Sign Up"/>
</form>

</body>
</html>
