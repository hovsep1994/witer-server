<%--
  @author shahen.poghosyan
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MenuKit For Bussiness</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="styles/commons.css">
    <link rel="stylesheet" type="text/css" href="styles/business.css">
</head>
<body>
<%@ include file="bussines_header.jsp" %>
<div class="signup" ng-app="app">
    Grow your business with us!
    <div ng-controller="userCtrl" ng-app="app" class="container signup-form">
        <form>
            <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-6 reg_input">
                    <input type="text" class="form-control" id="name" ng-model="user.name">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-6 reg_input">
                    <input type="text" class="form-control" id="email" ng-model="user.email">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-6 reg_input">
                    <input type="text" class="form-control" id="password" ng-model="user.password">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-6 reg_input">
                    <input type="text" class="form-control" id="user.company_name">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-6 reg_input">
                    <input type="text" class="form-control" id="user.company_phone">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-6 reg_input">
                    <input type="submit" class="btn btn-info signup_button" value="SIGN UP" style="display: block"
                           ng-click="signUp(user)">
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="scripts/reg_company.js" ></script>
</html>
