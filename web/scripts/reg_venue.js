/**
 * Created by shahenpoghosyan on 10/24/15.
 */
$(document).ready(function () {
    $('#city').on("input", function (e) {
        function searchCities(countryCode, query) {
            $.get("/cities", {
                countryCode: countryCode,
                query: query,
                limit: 10
            }, function (data) {
                if ($('#country option:selected').val()) {
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
            searchCities($('#country option:selected').val(),
                $('#city').val() );
    });

});