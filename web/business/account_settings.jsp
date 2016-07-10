<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 3/13/16
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>MenuKit For Bussiness</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
        integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/commons.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/business/commons.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/business/business-admin.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/business/account_settings.css">

</head>
<body ng-app="app">
<%@ include file="business_header_loged_in.jsp" %>
<div class="container admin-content">
  <div class="company-header">
    <h2>Account Settings</h2>
  </div>


  <div class="account-settings container">
    <div class="row">
      <div class="col-lg-6">
        <h4>CONTACT INFORMATION</h4>
        <h5>Your contact information is private.</h5>
      </div>
      <div class="col-lg-6">
        <label for="name">Full Name</label>
        <input id="name" class="form-control">
        <label for="companyName">Company Name</label>
        <input id="companyName" class="form-control">
        <label for="companyPhone">Company Phone</label>
        <input id="companyPhone" class="form-control">
        <button class="btn btn-info">Update Info</button><br><br>
      </div>
    </div>
    <hr size="30"><br>
    <div class="row">
      <div class="col-lg-6">
        <h4>Email</h4>
      </div>
      <div class="col-lg-6">
        <label for="email">Email</label>
        <input id="email" class="form-control">
        <button class="btn btn-info">Change Email</button><br><br>
      </div>
    </div>
    <hr size="30"><br>
    <div class="row">
      <div class="col-lg-6">
        <h4>Password</h4>
        <h5>Minumum 6 characters</h5>
      </div>
      <div class="col-lg-6">
        <label for="currentPassword">Current Password</label>
        <input id="currentPassword" class="form-control">
        <label for="newPassword">New Password</label>
        <input id="newPassword" class="form-control">
        <label for="repeatNewPassword">Repeat New Password</label>
        <input id="repeatNewPassword" class="form-control">
        <button class="btn btn-info">Change Password</button><br><br>
      </div>
    </div>

  </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/helper-service.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/user-service.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/venue-service.js"></script>
<script src="${pageContext.request.contextPath}/scripts/controllers/user-controller.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>

<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAW_hI04roTnEO08eq4tlKgNh4okOdMSTE&callback=initMap">
</script>
<script src="${pageContext.request.contextPath}/scripts/controllers/venue-controller.js"></script>
</html>
