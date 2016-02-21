<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 11/4/15
  Time: 12:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<form nameTranslation="user_login" id="login_user_form">

    <label>Email: </label>
    <input id="userLogin" class="reg_input" nameTranslation="email" type="email"/> <br>
    <label>Password: </label>
    <input id="userPassword" class="reg_input" nameTranslation="password" type="password"/> <br>

    <input type="submit" nameTranslation="userLoginSubmit" value="Submit">
</form>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/reg_company.js" type="text/javascript"></script>

</body>
</html>
