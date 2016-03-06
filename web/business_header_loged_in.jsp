<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 3/5/16
  Time: 5:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav ng-controller="userCtrl" class="navbar navbar-fixed-top navbar_custom">
    <div class="container-fluid">
        <a class="brand" href="#">Menu Kit <span style="color: yellow">Bussines</span></a>

        <span class="navbar_account">
            <span class="dropdown-toggle" data-toggle="dropdown">
                ${user}&nbsp;&nbsp;<span class="caret"></span>
            </span>
            <ul class="dropdown-menu dropdown-menu-right account_dropdown" role="menu" aria-labelledby="menu1">
                <li><a href="#">Settings</a></li>
                <li><a href="#" ng-click="signOut()">Log out</a></li>
            </ul>
        </span>
    </div>
</navv>
</nav>
