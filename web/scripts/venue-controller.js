/**
 * Created by shahenpoghosyan on 4/2/16.
 */

var host = "";
var venueService = new VenueService();

var app = angular.module('app');
app.controller('venueCtrl', function ($scope, $http) {
    $scope.venue = {
        image : host + '/styles/resources/business/admin/venue-image.png'
    };


    $scope.add = venueService.add;
    $scope.update = venueService.update;
    $scope.remove = venueService.remove;
    $scope.findUserVenues = venueService.findUserVenues;

    $scope.image_changed  = function(element) {
        var photofile = element.files[0];
        var reader = new FileReader();
        reader.onload = function(e) {
            $scope.$apply(function() {
                $scope.venue.image = e.target.result;
            });
        };
        reader.readAsDataURL(photofile);
    };


    $scope.closeModal = function() {
        $('#modal').modal('toggle');
    }

});