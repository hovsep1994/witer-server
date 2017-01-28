/**
 * @author shahenpoghosyan
 */

var app = angular.module('app', ['ngRoute']);

app.constant('BASE_URL', '');
app.constant('COOKIE_TOKEN', 'ckey');

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/menu/:menuId', {
            templateUrl: '/business/menu',
            controller: "editMenuCtrl"
        }).
        otherwise({
            redirectTo: '/landing'
        });
}]);

app.service('helper', function () {
    return new HelperService();
});

app.service('userService', ['$http', 'helper', 'BASE_URL', 'COOKIE_TOKEN', function ($http, helper, BASE_URL, COOKIE_TOKEN) {
    return new UserService(helper, $http, BASE_URL, COOKIE_TOKEN);
}]);

app.service('mapService', function () {
    return new MapService(new google.maps.Geocoder());
});

app.service('categoryService', ['$http', 'BASE_URL', function ($http, BASE_URL) {
    return new CategoryService($http, BASE_URL);
}]);

app.service('productService', ['$http', 'BASE_URL', function ($http, BASE_URL) {
    return new ProductService($http, BASE_URL);
}]);

app.service('venueService', ['$http', 'BASE_URL', function ($http, BASE_URL) {
    return new VenueService($http, BASE_URL);
}]);

app.service('menuService', ['$http', 'BASE_URL', function ($http, BASE_URL) {
    return new MenuService($http, BASE_URL);
}]);

app.directive('customOnChange', function() {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var onChangeHandler = scope.$eval(attrs.customOnChange);
            element.bind('change', function(event) {
                onChangeHandler(event, attrs.id);
            });
        }
    };
});


app.run(['$http', 'helper', 'COOKIE_TOKEN', function ($http, helper, COOKIE_TOKEN) {
    var token = helper.getCookie(COOKIE_TOKEN);
    if (token) {
        $http.defaults.headers.common.Authorization = token;
    }
}]);