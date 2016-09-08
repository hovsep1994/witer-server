/**
 * @author shahenpoghosyan
 */


function CategoryService($http, host) {

    var categoryUrl = host + "/api/categories/";

    this.create = create;
    this.update = update;

    function create(category, done) {
        alert(JSON.stringify(category));
        if(!done) {
            done = function() { }
        }

        $http.post(categoryUrl, category).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    function update(categoryId, category, done) {
        if(!done) {
            done = function() { }
        }

        $http.put(categoryUrl + categoryId, category).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    return this;
}