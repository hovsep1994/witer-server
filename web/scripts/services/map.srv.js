/**
 * @author shahenpoghosyan
 */

function MapService(geocoder) {

    this.codeAddress = codeAddress;
    this.setMapCenter = setMapCenter;
    this.setMarkerPostion = setMarkerPostion;
    this.createMarker = createMarker;
    this.getLocation = getLocation;
    this.addMarkerPositionChnageListener = addMarkerPositionChnageListener;

    function codeAddress(country, city, address, done) {
        if (!country) return;

        var combined = country;

        if (city) {
            combined = city + ", " + combined;
        }

        if (address) {
            combined = address + ", " + combined;
        }

        geocoder.geocode({'address': combined}, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {

                return done(null, results[0].geometry.location);

            }

            return done({
                message: "Geocoder return error. !"
            });
        });
    }

    function setMapCenter(map, location) {
        map.setCenter(new google.maps.LatLng(location.lat, location.lng));
    }

    function setMarkerPostion(marker, location) {
        marker.setPosition(location);
    }

    function addMarkerPositionChnageListener(marker, listener) {
        marker.addListener("mouseup", function () {
            getLocation(marker, listener);
        });
    }

    function createMarker(location, map) {
        return new google.maps.Marker({
            map: map,
            position: location,
            draggable: true
        });
    }

    function getLocation(marker, done) {
        var location = {
            marker: marker
        };
        geocoder.geocode({'location': marker.position}, function (results, status) {
            if (status === google.maps.GeocoderStatus.OK) {
                var components = results[0].address_components;
                components.forEach(function(component) {
                    if(component.types.indexOf("country") >= 0) {
                        location.countryCode = component.short_name;
                        location.country = component.long_name;
                    } else if(component.types.indexOf("locality") >= 0) {
                        location.city = component.long_name;
                    } else if (component.types.indexOf("route") >= 0) {
                        location.route = component.short_name;
                    } else if (component.types.indexOf("street_number") >= 0) {
                        location.streetNumber = component.long_name;
                    }
                });
                location.street = location.streetNumber + " " + location.route;

                return done(null, location);
            }

            return done({
                message: "Geocoder return error. !"
            });
        });
    }
}