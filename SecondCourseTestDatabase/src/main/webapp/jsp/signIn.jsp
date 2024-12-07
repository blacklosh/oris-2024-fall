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
    <title>Sign in page</title>
</head>
<body>

<div class="main" style="width: 100%; height: 100%; background-color: aquamarine">
    <h1>Welcome to my resource:)</h1>
    <div style="display: flex; justify-content: center; flex-wrap: nowrap">
        <div style="width: 100px; background-color: orange">
            <h2>Please enter your data:</h2>
        </div>
        <div style="width: 60%; background-color: blue">
            <form action="/signIn" method="post">
                <label for="email">Enter your email:</label>
                <input id="email" name="email" type="email" placeholder="fedor@main.ru" required/>
                <br/>
                <label for="password">Enter your password:</label>
                <input id="password" name="password" type="password" required/>
                <br/>
                <input type="submit" value="Sign In"/>
            </form>
        </div>
    </div>

</div>
</body>
</html>
