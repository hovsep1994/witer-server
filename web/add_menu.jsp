<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="static com.waiter.server.utils.ServletUtils.*" %>
<%@ page import="com.waiter.server.repository.MenuDAO" %>
<%@ page import="com.waiter.server.repository.CompanyDAO" %>
<%@ page import="com.waiter.server.repository.sql.MenuRepository" %>
<%@ page import="com.waiter.server.commons.entities.Company" %>
<%@ page import="com.waiter.server.commons.entities.Menu" %>
<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 11/9/15
  Time: 11:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Menu</title>
    <link rel="stylesheet" type="text/css" href="/styles/home.css">
</head>
<body>

<%
    ApplicationContext context = (ApplicationContext) pageContext.getServletContext().getAttribute("springContext");
    MenuDAO menuDAO = (MenuRepository) context.getBean("menuJDBCTemplate");
    CompanyDAO companyDAO = (CompanyDAO) context.getBean("companyJDBCTemplate");

    Cookie authCookie = getCookie("ckey", request);
    Company company = companyDAO.authenticate(authCookie.getValue());
%>

<form id="add_menu_form" name="add_menu_form">
    <label for="menuName">Menu Name: </label>
    <input id="menuName" name="menuName" class="reg_input" type="text"/>&nbsp;&nbsp;&nbsp;
    <input type="submit">
</form>
<br>

<form id="add_group_form" name="add_group_form">
    <label for="groupName">Group Name: </label>
    <input id="groupName" name="groupName" class="reg_input" type="text"/><br>
    <label for="groupName">Group Menu: </label>
    <select id="groupMenu" class="reg_input" name="venueMenu">
        <option value="" disabled selected>Select Menu</option>
        <% for (Menu menu : menuDAO.getCompanyMenus(company.getId())) { %>
        <option value="<%=menu.getId()%>"><%=menu.getName()%>
        </option>
        <% } %>
    </select><br>
    <label for="groupImage">Image: </label>
    <input id="groupImage" class="reg_input" type="file">
    <input type="submit">
</form>
<br>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="scripts/add_menu.js" type="text/javascript"></script>

</body>
</html>
