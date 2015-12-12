<%@ page import="com.waiter.server.commons.entities.Country" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.waiter.server.repository.sql.LocationsRepository" %>
<%@ page import="com.waiter.server.repository.MenuDAO" %>
<%@ page import="com.waiter.server.repository.sql.MenuRepository" %>
<%@ page import="com.waiter.server.commons.entities.Menu" %>
<%@ page import="static com.waiter.server.utils.ServletUtils.*" %>
<%@ page import="com.waiter.server.repository.UserDAO" %>
<%@ page import="com.waiter.server.commons.entities.User" %>

<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 10/29/15
  Time: 12:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style>
        #map {
            height: 400px;
            width: 400px;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="/styles/reg_venus.css">
</head>
<body>

<%
    ApplicationContext context = (ApplicationContext) pageContext.getServletContext().getAttribute("springContext");
    LocationsRepository locationJDBCTemplate = (LocationsRepository) context.getBean("locationJDBCTemplate");
    MenuDAO menuDAO = (MenuRepository) context.getBean("menuJDBCTemplate");
    UserDAO userDAO = (UserDAO) context.getBean("userJDBCTemplate");

    Cookie authCookie = getCookie("ckey", request);
    User user = userDAO.authenticate(authCookie.getValue());
%>

<form id="add_venue_form" name="company_reg">
    <label>Menu: </label>
    <select id="menu" class="reg_input" name="venueMenu">
        <option value="" disabled selected>Select Menu</option>
        <% for (Menu menu : menuDAO.getCompanyMenus(user.getCompany().getId())) { %>
        <option value="<%=menu.getId()%>"><%=menu.getName()%>
        </option>
        <% } %>
    </select> Or <a href="/menus/add">create a new one. </a> <br>

    <label for="country">Country: </label>
    <select id="country" class="reg_input" name="venueCountry">
        <option value="" disabled selected>Select Country</option>
        <% for (Country country : locationJDBCTemplate.getAllCountries()) { %>
        <option value="<%=country.getCode()%>"><%=country.getName()%>
        </option>
        <% } %>
    </select><br>

    <label for="city">City: </label>
    <input id="city" class="reg_input" list="cities_data" name="city"/>
    <datalist id="cities_data"></datalist><br>

    <label for="address">Address: </label>
    <input id="address" class="reg_input" name="address"/><br>

    <div id="map"></div><br>
    <input class="reg_input" name="venueSubmit" type="submit" value="Submit">
</form>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="../scripts/add_venue.js" type="text/javascript"></script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAW_hI04roTnEO08eq4tlKgNh4okOdMSTE&callback=initMap">
</script>

</body>
</html>
