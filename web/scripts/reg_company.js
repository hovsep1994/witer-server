/**
 * Created by shahenpoghosyan on 10/29/15.
 */

var host = "..";

var app = angular.module('app', []);
app.controller('userCtrl', function ($scope, $http) {

    $scope.signUp = function (user) {
        alert(host + "/api/users/signup", user);
        $http.post(host + "/api/users/signup", user).then(function (response) {
            alert(JSON.stringify(response));
            if (response.data.success) {
                var date = new Date();
                date.setDate(new Date().getDate() + 60);
                setCookie("ckey", response.response.token, date);
                window.location.href = "/";
            }
        });
    };

    $scope.signIn = function (user) {
        alert(JSON.stringify(user));
        $http.post(host + "/api/users/signin", user).then(function (response) {
            if (response.success) {
                var date = new Date();
                date.setDate(new Date().getDate() + 60);
                setCookie("ckey", response.response.token, date);
                window.location.href = "/";
            }
        });
    };

    $scope.openSignInDialog = function () {
        alert("valod");
        $('#signInModal').modal('show');
    }

});


//$(document).ready(function () {
//    $("#reg_company_form").on('submit', function(e) {
//        e.preventDefault();
//        var name = $('#name').val();
//        var email = $('#email').val();
//        var password = $('#password').val();
//        var companyName = $('#companyName').val();
//        var companyPhone = $('#companyPhone').val();
//        $.post("../api/users/register", {
//            email: email,
//            name: name,
//            password: password,
//            companyName: companyName,
//            companyPhone: companyPhone
//        }, function(data) {
//            console.log(data)
//            var response = JSON.parse(data);
//            if(response.success) {
//                var date = new Date();
//                date.setDate(new Date().getDate() + 60);
//                setCookie("ckey", response.response.token, date);
//                window.location.href = "/venues/add";
//            } else {
//                alert("error: " + response.error.message)
//            }
//        })
//    });
//
//    $("#login_user_form").on('submit', function(e) {
//        e.preventDefault();
//        var email = $('#userLogin').val();
//        var password = $('#userPassword').val();
//        console.log(password);
//        $.post("../api/users/signin", {
//            login: email,
//            password: password
//        }, function(data) {
//            console.log(data)
//            var response = JSON.parse(data);
//            if(response.success) {
//                var date = new Date();
//                date.setDate(new Date().getDate() + 60);
//                setCookie("ckey", response.response.token, date);
//                window.location.href = "/venues/add";
//            } else {
//                alert("error: " + response.error.message)
//            }
//        })
//    });
//});
function setCookie(name, value, expireDate) {
    document.cookie = name + "=" + value + ";expires=" + expireDate.toUTCString() + ";path=/"
}