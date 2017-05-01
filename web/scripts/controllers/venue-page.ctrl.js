/**
 * @author shahenpoghosyan
 */
var host = "";
var app = angular.module('app');

app.controller('venuePageCtrl', ['$scope', '$http', 'venueService', 'mapService', function ($scope, $http, venueService, mapService) {
    var self = $scope;

    var venueId = getVenueId();

    venueService.findVenueWithMenu(venueId, function (err, venue) {
        self.venue = venue;
        venue.logo += "/r300";
        self.venue.menu.categories.forEach(function (category) {
            category.products.forEach(function (product) {
                product.image += "/r200";
            })
        });
        console.log('valod ', venue);
    });


    function getVenueId() {
        var tokens = location.pathname.split("/");
        return tokens[2];
    }
}]);
