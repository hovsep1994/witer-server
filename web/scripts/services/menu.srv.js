/**
 * @author shahenpoghosyan
 */

function MenuService($http, host) {

    var menuUrl = host + "/api/menu/";

    this.create = create;

    function create(menu, done) {
        if(!done) {
            done = function() { }
        }

        $http.post(menuUrl, menu).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    return this;
}