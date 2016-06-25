/**
 * Created by shahenpoghosyan on 10/29/15.
 */

var helper = new HelperService();
var userService = new UserService(helper);


var app = angular.module('app', []);
app.controller('userCtrl', function ($scope, $http) {

    $scope.user = userService.getUser;

    $scope.signUp = function(user) {
        userService.signUp(user, function(err, user) {
            if(err) return alert(err); //todo

            $scope.user = user;

        });
    };
    $scope.signIn = function(user) {
        userService.signIn(user, function(err, user) {
            if(err) return alert(err); //todo

            $scope.user = user;
        });
    };

    $scope.signOut = userService.signOut;

});
