<%--
  @author shahen.poghosyan
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MenuKit For Bussiness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/resources/bootstrap/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/commons.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/business.css">
</head>
<body ng-app="app">
<%@ include file="bussines_header.jsp" %>
<div class="business-main">
    <div class="business-title">
        Hi, we're <span style="color: #bd9a0d">MenuKit</span><br>
        we help people discover your restaurant<br>
        <a href="#signup" class="btn btn-info" >SIGN UP</a>
    </div>
</div>
<div class="container-fluid">
    <div class="row desc-row">
        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12 business-desc-cell business-desc-cell-left push-down">
            <div>
                <span class="desc-header">Register your restaurant</span>
                <h4 class="desc-content">
                    <span style="color: #4ee6fc;">Register</span> your restaurant in Menu Kit to
                    <span style="color: #4ee6fc;">let people discover</span> it and be able
                    to choose their preferred meal from <span style="color: #4ee6fc;">your menu</span>
                    before <span style="color: #4ee6fc;">visiting you.</span>
                </h4>
            </div>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 business-desc-cell business-desc-cell-right push-up">
            <div>
                <img src="${pageContext.request.contextPath}/styles/resources/business/desc-graphic1.png" />
            </div>
        </div>
    </div>
    <div class="row desc-row" style="background: rgba(15, 15, 18, 0.6); ">
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 business-desc-cell business-desc-cell-left">
            <div>
                <img src="${pageContext.request.contextPath}/styles/resources/business/desc-graphic2.png" />
            </div>
        </div>
        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12 business-desc-cell business-desc-cell-right">
            <div>
                <span class="desc-header">You can manage</span>
                <h4 class="desc-content">
                    You can <span style="color: #4ee6fc;">manage</span> your restaurant’s menu from
                    <span style="color: #4ee6fc;">anywhere and anytime.</span>
                </h4>
            </div>
        </div>
    </div>
    <div class="row desc-row">
        <div class="col-lg-7 col-md-7 col-sm-7 col-xs-12 business-desc-cell business-desc-cell-left push-down">
            <div>
                <span class="desc-header">By translating</span>
                <h4 class="desc-content">
                    <span style="color: #4ee6fc;">By translating </span> your
                    <span style="color: #4ee6fc;">menu </span> to different languages we make it
                    <span style="color: #4ee6fc;">achievable to everybody</span>
                </h4>
            </div>
        </div>
        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-12 business-desc-cell business-desc-cell-right push-up">
            <div class="feature-image-3">
                <img style="right: 0" src="${pageContext.request.contextPath}/styles/resources/business/desc-graphic3.png"/>
            </div>
        </div>
    </div>
</div>
<div id="signup" class="container-fluid signup">
    Grow your business with us!
    <div ng-controller="userCtrl" class="signup-form">
        <form>
            <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 reg_input">
                    <input placeholder="Name" type="text" class="form-control" id="name" ng-model="user.name">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 reg_input">
                    <input placeholder="Email" type="text" class="form-control" id="email" ng-model="user.email">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 reg_input">
                    <input placeholder="Password" type="password" class="form-control" id="password"
                           ng-model="user.password">
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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/helper.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/user.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/app.js"></script>
<script src="${pageContext.request.contextPath}/scripts/controllers/user.ctrl.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>
</html>
