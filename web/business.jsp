<%--
  @author shahen.poghosyan
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MenuKit For Bussiness</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="styles/commons.css">
    <link rel="stylesheet" type="text/css" href="styles/business.css">
</head>
<body ng-app="app">
<%@ include file="bussines_header.jsp"%>
<div class="signup">
    Grow your business with us!
    <div ng-controller="userCtrl"class="container signup-form">
        <form>
            <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 reg_input">
                    <input placeholder="name" type="text" class="form-control" id="name" ng-model="user.name">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 reg_input">
                    <input placeholder="email" type="text" class="form-control" id="email" ng-model="user.email">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 reg_input">
                    <input placeholder="password" type="password" class="form-control" id="password" ng-model="user.password">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 reg_input">
                    <input placeholder="Company" type="text" class="form-control" id="user.companyName">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 reg_input">
                    <input placeholder="Company Phone" type="text" class="form-control" id="user.companyPhone">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 reg_input">
                    <input type="submit" class="btn btn-info signup_button" value="SIGN UP" ng-click="signUp(user)">
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="scripts/reg_company.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>
</html>
