/**
 * @author shahenpoghosyan
 */

var mapEdit, mapDisplay;
function initMap() {


}

var host = "";
var defaultImage = host + '/styles/resources/business/admin/venue-image.png';
var app = angular.module('app');
app.controller('venueCtrl', ['$scope', '$http', 'venueService', 'mapService', function ($scope, $http, venueService, mapService) {

    var self = $scope; //todo change this

    //mapDisplay = new google.maps.Map(document.getElementById('map1'), {
    //    center: {lat: 70, lng: 0},
    //    zoom: 2
    //});

    mapEdit = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 70, lng: 0},
        zoom: 2
    });
    //google.maps.event.trigger(mapDisplay, "resize");

    self.venues = [];
    venueService.findUserVenues(function(err, venues) {
        if(err) return console.log(err);
        self.venues = venues.map(function(v, i) {
            if(!i) {
                v.active = true;
            }
            return v;
        });
    });

    self.editVenue = {
        imageDisplay : defaultImage
    };

    self.update = function(venue) {
        function process(cb) {
            if(venue.new) {
                return venueService.create(venue, cb);
            }
            return venueService.update(venue.id, venue);
        }
        process(function () {
            $('#editVenueModal').modal('toggle');
        })
    };

    self.remove = venueService.remove;
    self.findUserVenues = venueService.findUserVenues;
    self.initEditVenue = function (newVenue, venue) {
        if(newVenue) {
            self.editVenue.new = true;
        } else {
            self.editVenue = venue;
        }
        self.editVenue.map = mapEdit;
        $("#editVenueModal").on("shown.bs.modal", function () {
            google.maps.event.trigger(mapEdit, "resize");
        });
    };

    self.image_changed  = function(element) {
        var photofile = element.files[0];
        var reader = new FileReader();
        reader.onload = function(e) {
            self.$apply(function() {
                self.editVenue.imageDisplay = e.target.result;
                self.editVenue.image = photofile;
            });
        };
        reader.readAsDataURL(photofile);
    };

    self.onEditVenueChange = function() {
        var location = self.editVenue.location;
        if(location) {
            mapService.codeAddress(location.country, location.city, location.street, function(err, loc) {
                if(err) return console.log(err);
                setZoom(location, mapEdit);

                if(!location.marker) {
                    location.marker = mapService.createMarker(loc, mapEdit);
                } else {
                    mapService.setMarkerPostion(location.marker, loc);
                }
                mapService.addMarkerPositionChnageListener(location.marker, function(err, location) {
                    if(err) return console.log(err);
                    location.zip = self.editVenue.location.zip;

                    self.editVenue.location = location;
                    $scope.$apply()
                });

                mapService.setMapCenter(mapEdit, {
                    lat: location.marker.position.lat(),
                    lng: location.marker.position.lng()
                });

            });
        }
    };

    self.closeModal = function() {
        $('#modal').modal('toggle');
    };

    function setZoom(location, map) {
        if(location.country) {
            map.setZoom(4);
        }
        if(location.city) {
            map.setZoom(10);
        }
        if(location.city) {
            map.setZoom(16);
        }
    }

}]);