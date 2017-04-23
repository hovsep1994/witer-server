<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 3/5/16
  Time: 5:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav ng-controller="userCtrl" class="navbar navbar-fixed-top navbar_custom">
    <div class="container">
        <a class="brand brand-fixed" href="/business/">
            <img src="${pageContext.request.contextPath}/styles/resources/commons/logo.png" width="55px"
                 style="margin-top: -10px; margin-right: -5px;">
            Menu Kit <span style="color:#3cbad3">Business</span>
        </a>

        <div class="navbar-account">
            <span class="dropdown-toggle" style="cursor: pointer" data-toggle="dropdown">
                ${user.name} &nbsp;&nbsp;<span class="caret"></span>
            </span> <br>
            <ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="menu1">
                <%--<li ng-init="setting_src = '/styles/resources/business/account-settings.png'"--%>
                    <%--ng-mouseenter="setting_src='/styles/resources/business/account-settings-active.png'"--%>
                    <%--ng-mouseleave="setting_src = '/styles/resources/business/account-settings.png'">--%>
                    <%--<a href="#">--%>
                        <%--<img src="{{setting_src}}" style="margin-right: 10px"/>Settings--%>
                    <%--</a>--%>
                <%--</li>--%>
                <li ng-init="logout_src = '/styles/resources/business/account-logout.png'"
                    ng-mouseenter="logout_src='/styles/resources/business/account-logout-active.png'"
                    ng-mouseleave="logout_src = '/styles/resources/business/account-logout.png'">
                    <a href="#" ng-click="signOut()">
                        <img src="{{logout_src}}" style="margin-right: 10px"/>
                        Log out
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
