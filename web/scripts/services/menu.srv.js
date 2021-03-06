/**
 * @author shahenpoghosyan
 */

function MenuService($http, host) {

    var menuUrl = host + "/api/menus/";

    this.create = create;
    this.update = update;
    this.findAll = findAll;
    this.findById = findById;

    function create(menu, done) {
        if(!done) {
            done = function() { }
        }

        $http.post(menuUrl, menu).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    function update(menuId, menu, done) {
        if(!done) {
            done = function() { }
        }

        $http.put(menuUrl + menuId, menu).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    function findAll(done) {
        if(!done) {
            done = function() { }
        }
        $http.get(menuUrl).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    function findById(menuId, done) {
        $http.get(menuUrl + menuId).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    return this;
}