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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="./scripts/reg_venue.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="./styles/reg_venus.css">
</head>
<body>
<%
    ApplicationContext context = (ApplicationContext) pageContext.getServletContext().getAttribute("springContext");
    LocationsJDBCTemplate locationJDBCTemplate = (LocationsJDBCTemplate) context.getBean("locationJDBCTemplate");
%>
<form name="venue" >
    <input class="reg_input" name="venueName" type="text" style="margin-bottom: 3px; display: block" /> <br>
    <input class="reg_input" name="ownerEmail" type="email" /> <br>
    <input class="reg_input" name="password" type="password" /> <br>
    <input class="reg_input" name="repeatPassword" type="password"> <br>
    <select id="country" class="reg_input" name = "venueCountry" >
        <option value="" disabled selected>Select Country</option>
        <% for (Country country : locationJDBCTemplate.getAllCountries()) {
            System.out.println(country); %>
        <option value="<%=country.getCode()%>" ><%=country.getName()%></option>
        <% } %>
    </select>
    <input id="city" class="reg_input" list="cities_data" name="city" />
    <datalist id="cities_data" >
        <option>Armenia</option>
    </datalist>
    <input class="reg_input" name="venueSubmit" type="submit">
</form>
</body>
</html>
