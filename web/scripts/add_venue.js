/**
 * Created by shahenpoghosyan on 10/24/15.
 */

var map;
var geocoder;
var marker;
function initMap() {
    geocoder = new google.maps.Geocoder();
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 0, lng: 0},
        zoom: 1
    });
}

$(document).ready(function () {

    $("#add_venue_form").on('submit', function(e) {
        e.preventDefault();
        var country = $('#country').find('option:selected').text();
        var city = $('#city').val();
        var address = $('#address').val();
        var latitude = marker.position.lat;
        var longitude = marker.position.lng;
        var companyKey = getCookie("ckey");
        $.post("/api/venues/add", {
            country: country,
            city: city,
            address: address,
            latitude: latitude,
            longitude: longitude,
            key: companyKey
        }, function(data) {
            console.log(data)
        })
    });


    $('#city').on("input", function (e) {
        function searchCities(countryCode, query) {
            $.get("/api/cities", {
                countryCode: countryCode,
                query: query,
                limit: 10
            }, function (data) {
                if ($('#country').find('option:selected').val()) {
                    var response = JSON.parse(data);
                    if (response.success) {
                        var html = '';
                        response.response.forEach(function (city) {
                            html += "<option>" + city + "</option>"
                        });
                        $('#cities_data').html(html);
                    }
                }
            })
        }

        if ($('#city').val().length == 1) {
            searchCities($('#country').find('option:selected').val(), $('#city').val());
        }

    });

    $('#country').focusout(function () {
        codeAddress($('#country').find('option:selected').text(),
            $('#city').val(), $('#address').val());
    });

    $('#city').focusout(function () {
        codeAddress($('#country').find('option:selected').text(),
            $('#city').val(), $('#address').val());
    });

    $('#address').focusout(function () {
        codeAddress($('#country').find('option:selected').text(),
            $('#city').val(), $('#address').val());
    });

    function codeAddress(country, city, address) {
        if (!country) return;
        var combined = country;
        map.setZoom(6);
        if (city) {
            combined = city + ", " + combined;
            map.setZoom(10);
        }
        if (address) {
            combined = address + ", " + combined;
            map.setZoom(14);
        }

        console.log(combined);
        geocoder.geocode({'address': combined}, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                map.setCenter(results[0].geometry.location);
                if (!marker) {
                    marker = new google.maps.Marker({
                        map: map,
                        position: results[0].geometry.location,
                        draggable: true
                    });
                }
                marker.setPosition(results[0].geometry.location);
                marker.addListener("mouseup", function () {
                    geocoder.geocode({'location': marker.position}, function (results, status) {
                        if (status === google.maps.GeocoderStatus.OK) {
                            initFields(results);
                        }
                    });
                });
            } else if (marker) {
                marker.setMap(null);
            }

        });
    }

    function initFields(geocoderResults) {
        var components = geocoderResults[0].address_components;
        var street = '';
        var streetNumber = '';
        components.forEach(function(component) {
            if(component.types.indexOf("country") >= 0) {
                $('#country').val(component.short_name);
            } else if(component.types.indexOf("locality") >= 0) {
                $('#city').val(component.long_name);
            } else if (component.types.indexOf("route") >= 0) {
                street = component.short_name;
            } else if (component.types.indexOf("street_number") >= 0) {
                streetNumber = component.long_name;
            }
        });
        $("#address").val(streetNumber + " " + street);
    }

    function getCookie(name) {
        var value = "; " + document.cookie;
        console.log("valodik: " + document.cookie);
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) return parts.pop().split(";").shift();
    }
});