/**
 * @author shahenpoghosyan
 */

var host = "";
var defaultImage = host + '/styles/resources/business/admin/venue-image.png';
var app = angular.module('app');
app.controller('venueCtrl', ['$scope', '$http', 'venueService', 'mapService', function ($scope, $http, venueService, mapService) {

    var self = $scope; //todo change this

    self.remove = remove;
    self.update = update;
    self.initRemove = initRemove;
    self.initEditVenue = initEditVenue;
    self.findUserVenues = findUserVenues;

    self.venues = [];
    self.editVenue = {
        imageDisplay: defaultImage
    };

    self.mapEdit = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 70, lng: 0},
        zoom: 2
    });

    findUserVenues();

    function findUserVenues() {
        venueService.findUserVenues(function (err, venues) {
            if (err) return console.log(err);
            self.venues = venues.map(function (v, i) {
                if (!i) {
                    v.active = true;
                }
                return v;
            }).slice(0, 10);
            google.maps.event.addDomListener(window, "load", function() {
                self.venues.forEach(function(v, i) {
                    v.displayMap = new google.maps.Map(document.getElementById('map' + v.id), {
                        center: {lat: 70, lng: 0},
                        zoom: 10
                    });
                    $('#venue_link' + v.id).on('shown.bs.tab', function (e) {
                        google.maps.event.trigger(v.displayMap, "resize");
                        updateMap(v.location, v.displayMap);
                    });
                    if(!i) {
                        updateMap(v.location, v.displayMap);
                    }
                });
            });
        });
    }

    function update(venue) {
        function process(cb) {
            if (venue.new) {
                return venueService.create(venue, cb);
            }
            return venueService.update(venue.id, venue, cb);
        }

        process(function () {
            $('#editVenueModal').modal('toggle');
        });
    }

    function remove() {
        if (self.deleteVenueId) {
            venueService.remove(self.deleteVenueId, function (err) {
                if (err) return console.log(err);

                $('#deleteVenueModal').modal('toggle');
                self.venues = self.venues.filter(function (v) {
                    return v.id != self.deleteVenueId;
                });
            });
        }
    }

    function initRemove(id) {
        self.deleteVenueId = id;
    }

    function initEditVenue(newVenue, venue) {
        if (newVenue) {
            self.editVenue.new = true;
        } else {
            self.editVenue = venue;
        }
        self.editVenue.map = self.mapEdit;
        $("#editVenueModal").on("shown.bs.modal", function () {
            google.maps.event.trigger(self.mapEdit, "resize");
        });
    }

    self.image_changed = function (element) {
        var photofile = element.files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            self.$apply(function () {
                self.editVenue.imageDisplay = e.target.result;
                self.editVenue.image = photofile;
            });
        };
        reader.readAsDataURL(photofile);
    };

    self.onEditVenueChange = function () {
        var location = self.editVenue.location;
        updateMap(location, self.mapEdit);
    };

    function updateMap(location, map) {
        if (location) {
            mapService.codeAddress(location.country, location.city, location.street, function (err, loc) {
                if (err) return console.log(err);
                setZoom(location, map);

                if (!location.marker) {
                    location.marker = mapService.createMarker(loc, map);
                } else {
                    mapService.setMarkerPostion(location.marker, loc);
                }
                mapService.addMarkerPositionChnageListener(location.marker, function (err, location) {
                    if (err) return console.log(err);
                    location.zip = self.editVenue.location.zip;

                    self.editVenue.location = location;
                    $scope.$apply()
                });

                mapService.setMapCenter(map, {
                    lat: location.marker.position.lat(),
                    lng: location.marker.position.lng()
                });

            });
        }
    }

    self.closeModal = function () {
        $('#modal').modal('toggle');
    };

    function setZoom(location, map) {
        if (location.country) {
            map.setZoom(4);
        }
        if (location.city) {
            map.setZoom(10);
        }
        if (location.city) {
            map.setZoom(16);
        }
    }

}]);