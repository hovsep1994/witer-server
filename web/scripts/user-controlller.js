/**
 * Created by shahenpoghosyan on 10/29/15.
 */

var host = "..";
var keyCookie = "ckey";

var app = angular.module('app', []);
app.controller('userCtrl', function ($scope, $http) {
    $scope.user = getUser();

    $scope.signUp = function (user) {
        alert(JSON.stringify(user));
        $http.post(host + "/api/users/signup", user).then(function (response) {
            alert(JSON.stringify(response));
            if (response.data.success) {
                saveUser(response.data.response);
            }
        });
    };

    $scope.signIn = function (user) {
        alert(JSON.stringify(user));
        $http({
            method: 'POST',
            url: host + "/api/users/signin",
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function (obj) {
                var str = [];
                for (var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: user
        }).then(function (response) {
            if (response.data.success) {
                saveUser(response.data.response);
            }
        });
    };

    $scope.signOut = function () {
        removeUser();
    };

    function saveUser(user) {
        var date = new Date();
        date.setDate(new Date().getDate() + 60);
        setCookie(keyCookie, user.token, date);
        window.location.href = "/";

        localStorage.setItem("user", user);
        $scope.user = user;
    }

    function getUser() {
        if (!getCookie(keyCookie)) {
            return null;
        }
        return localStorage.getItem("user");
    }

    function removeUser() {
        localStorage.removeItem("user");
        deleteCookie(keyCookie);
    }

});

function setCookie(name, value, expireDate) {
    document.cookie = name + "=" + value + ";expires=" + expireDate.toUTCString() + ";path=/"
}

function getCookie(name) {
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length == 2) return parts.pop().split(";").shift();
}

function deleteCookie(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}
