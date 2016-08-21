/**
 * @author shahenpoghosyan
 */

var app = angular.module('app', []);

app.constant('BASE_URL', '');
app.constant('COOKIE_TOKEN', 'ckey');

app.service('helper', function() {
    return new HelperService();
});

app.service('userService', ['$http', 'helper', 'BASE_URL', 'COOKIE_TOKEN', function($http, helper, BASE_URL, COOKIE_TOKEN) {
    return new UserService(helper, $http, BASE_URL, COOKIE_TOKEN);
}]);

app.service('mapService', function() {
    return new MapService(new google.maps.Geocoder());
});

app.service('venueService', ['$http', 'BASE_URL', function($http, BASE_URL) {
    return new VenueService($http, BASE_URL);
}]);

app.service('menuService', ['$http', 'BASE_URL', function($http, BASE_URL) {
    return new MenuService($http, BASE_URL);
}]);

app.run([ '$http', 'helper', 'COOKIE_TOKEN', function($http, helper, COOKIE_TOKEN) {
    var token = helper.getCookie(COOKIE_TOKEN);
    if(token) {
        $http.defaults.headers.common.Authorization = token;
    }
}]);