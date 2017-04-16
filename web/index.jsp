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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/resources/bootstrap/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="styles/home.css">
    <link rel="stylesheet" type="text/css" href="styles/commons.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="home_main">
    <div class="title">
        Discover hidden tasty world
    </div>
    <div class="subtitle">
        Explore top restaurants and their dishes around you
    </div>
    <div class="store_images">
        <a href="">
            <img src="${pageContext.request.contextPath}/styles/resources/commons/google_play.png" class="left"/>
        </a>
        <a href="">
            <img src="${pageContext.request.contextPath}/styles/resources/commons/app_store.png"/>
        </a>
    </div>
</div>
<div class="container-fluid desc">
    <div class="row">
        <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 desc_cell" style="background-color:#1D1E22;">
            You can get restaurant's menu and information in <span style="color: #74c220;">your native language.</span>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 desc_cell" style="background-color:#18191D;">
            With our <span style="color: #ffcc00;">smart search.</span> tool you can quickly find your preferred food.
        </div>
        <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 desc_cell" style="background-color:#1D1E22;">
            <span style="color: #1b9caf;">Find top rated</span> restaurants and dishes in your city.
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="scripts/controllers/user.ctrl.js" type="text/javascript"></script>
</body>
</html>