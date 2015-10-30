<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.waiter.server.commons.entities.Country" %>
<%@ page import="com.waiter.server.db.sql.LocationsJDBCTemplate" %>
<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 10/17/15
  Time: 6:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="./styles/reg_venus.css">
</head>
<body>
<form name="company_reg" id="reg_compaby_form">
    <label>Name: </label>
    <input id="companyName" class="reg_input" name="name" type="text"/> <br>
    <label>Email: </label>
    <input id="companyEmail" class="reg_input" name="email" type="email"/> <br>
    <label>Password: </label>
    <input id="companyPassword" class="reg_input" name="password" type="password"/> <br>
    <input type="submit" name="companySubmit" value="Submit">
</form>

<br><a href="/venues/add">Add venue</a>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="/scripts/reg_company.js        " type="text/javascript"></script>
</body>
</html>
