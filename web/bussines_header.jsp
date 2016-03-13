<%--
  @auhtor shahen.poghosyan
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%%>
<nav ng-controller="userCtrl" class="navbar navbar-fixed-top navbar_custom">
    <div class="container-fluid">
        <a class="brand" href="#">
            <img src="${pageContext.request.contextPath}/styles/resources/commons/logo.png" width="55px"
                 style="margin-top: -10px; margin-right: -5px;">
            Menu <span style="color:#ffcc00">Kit</span>
        </a>
        <span ng-if="!user" data-toggle="modal" data-target="#signInModal" class="navbar_bussiness">Sign In</span>
    </div>
    <div id="signInModal" class="modal fade" role="dialog">
        <form>
            <div class="modal-dialog signin_dialog" style="width: 400px">
                <div class="modal-content signin_modal_content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h5 class="modal-title">Sign in to MenuKit <span style="color:#2f8ea1;">Business</span></h5>
                        <hr width="150px" color="#333333">
                    </div>
                    <div class="modal-body">
                        <input type="text" class="form-control" placeholder="Login" id="login" ng-model="user.login"><br>
                        <input type="text" class="form-control" placeholder="Password" id="password"
                               ng-model="user.password">

                        <div><a href="#">Forgot password?</a></div>
                    </div>
                    <div class="modal-footer">
                        <input type="submit" class="btn btn-info signup_button" value="SIGN IN" ng-click="signIn(user)"><br>
                    </div>
                </div>
            </div>
        </form>
    </div>
</nav>
