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
    <style>
        #map {
            height: 400px;
            width: 400px;
        }
    </style>
    <script type="text/javascript">


    </script>
</head>
<body>
<%
    ApplicationContext context = (ApplicationContext) pageContext.getServletContext().getAttribute("springContext");
    LocationsJDBCTemplate locationJDBCTemplate = (LocationsJDBCTemplate) context.getBean("locationJDBCTemplate");
%>
<form name="venue">

    <label>Name: </label>
    <input class="reg_input" name="venueName" type="text"/> <br>
    <label>Email: </label>
    <input class="reg_input" name="ownerEmail" type="email"/> <br>
    <label>Password: </label>
    <input class="reg_input" name="password" type="password"/> <br>
    <label>Repeat password: </label>
    <input class="reg_input" name="repeatPassword" type="password"> <br>
    <label>Country: </label>
    <select id="country" class="reg_input" name="venueCountry">
        <option value="" disabled selected>Select Country</option>
        <% for (Country country : locationJDBCTemplate.getAllCountries()) {
            System.out.println(country); %>
        <option value="<%=country.getCode()%>"><%=country.getName()%>
        </option>
        <% } %>
    </select> <br>
    <label>City: </label>
    <input id="city" class="reg_input" list="cities_data" name="city"/>
    <datalist id="cities_data">
    </datalist>
    <br>
    <label>Address: </label>
    <input id="address" class="reg_input" name="address"/> <br>
    <input class="reg_input" name="venueSubmit" type="submit">

    <div id="map">
    </div>

</form>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="./scripts/reg_venue.js" type="text/javascript"></script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAW_hI04roTnEO08eq4tlKgNh4okOdMSTE&callback=initMap">
</script>
</body>
</html>
