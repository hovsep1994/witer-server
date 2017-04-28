/**
 * Created by shahenpoghosyan on 10/29/15.
 */

var app = angular.module('app');
app.controller('userCtrl', ['$scope', '$http', 'userService', function ($scope, $http, userService) {

    var user = userService.getUser();
    if(user) {
        $scope.user = userService.getUser();
    }

    $scope.signUp = function(user) {
        userService.signUp(user, function(err, user) {
            if(err) return alert(JSON.stringify(err)); //todo

            $scope.user = user;
            window.location.href = "/business";

        });
    };
    $scope.signIn = function(user) {
        userService.signIn(user, function(err, user) {
            if(err) return alert(JSON.stringify(err)); //todo

            $scope.user = user;
            window.location.href = "/business/";
        });
    };

    $scope.signOut = function() {
        userService.signOut();
        window.location.href = "/business/landing";
    };

}]);
