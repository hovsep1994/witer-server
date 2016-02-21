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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="styles/home.css">
    <link rel="stylesheet" type="text/css" href="styles/commons.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="home_main">
    <div class="store_images">
        <a href="">
            <img src="styles/resources/istore.png" style="margin-right: 15px;"/>
        </a>
        <a href="">
            <img src="styles/resources/google_play.png"/>
        </a>
    </div>

</div>
<div class="container-fluid desc">
    <div class="row">
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc_cell" style="background-color:#1D1E22;">
            With our smart search tool you can quickly find your preferred food.
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc_cell" style="background-color:#18191D;">
            You can get restaurant's menu and information in any language.
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 col-xs desc_cell" style="background-color:#1D1E22;">
            With our smart search tool you can quickly find your preferred food.
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</div>
<%--<form nameTranslation="company_reg" id="reg_company_form">--%>
<%--<label>NameTranslation: </label>--%>
<%--<input id="nameTranslation" class="reg_input" nameTranslation="nameTranslation" type="text"/> <br>--%>
<%--<label>Email: </label>--%>
<%--<input id="email" class="reg_input" nameTranslation="email" type="email"/> <br>--%>
<%--<label>Password: </label>--%>
<%--<input id="password" class="reg_input" nameTranslation="password" type="password"/> <br> <br>--%>

<%--<label>Company NameTranslation: </label>--%>
<%--<input id="companyName" class="reg_input" nameTranslation="companyName" type="text"/> <br>--%>
<%--<label>Company Phone: </label>--%>
<%--<input id="companyPhone" class="reg_input" nameTranslation="companyPhone" type="text"/> <br>--%>
<%--<input type="submit" nameTranslation="companySubmit" value="Submit">--%>
<%--</form>--%>

<%--<br><a href="login">Login</a>--%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="scripts/reg_company.js" type="text/javascript"></script>
</body>
</html>
