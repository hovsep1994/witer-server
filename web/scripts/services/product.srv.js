/**
 * @author shahenpoghosyan
 */


function ProductService($http, host) {

    var productUrl = host + "/api/products/";

    this.create = create;
    this.update = update;

    function create(product, done) {
        alert(JSON.stringify(product));
        if(!done) {
            done = function() { }
        }

        $http.post(productUrl, product).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    function update(productId, product, done) {
        if(!done) {
            done = function() { }
        }

        $http.put(productUrl + productId, product).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    return this;
}