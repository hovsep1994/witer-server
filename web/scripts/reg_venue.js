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
    $('#city').on("input", function (e) {
        function searchCities(countryCode, query) {
            $.get("/cities", {
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
        if($('#city').val().length == 1) {
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
                if(!marker) {
                    marker = new google.maps.Marker({
                        map: map,
                        position: results[0].geometry.location,
                        draggable: true
                    });
                }
                marker.setPosition(results[0].geometry.location);
            } else {
                //todo remove alert
                alert("Geocode was not successful for the following reason: " + status);
                if(marker) {
                    marker.setMap(null);
                }
            }
        });
    }
});