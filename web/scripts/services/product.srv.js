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

            updateProductImage(response.data.response, done);

        });
    }

    function update(productId, product, done) {
        if(!done) {
            done = function() { }
        }

        console.log("product: ", product);

        $http.put(productUrl + productId, product).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            updateProductImage(response.data.response, done);
        });
    }

    function updateProductImage(product, done) {
        if(product.image) {
            var fd = new FormData();
            fd.append('file', product.image);
            $http.post(productUrl + product.id + "/image", fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(function (response) {
                if (response.data.status !== 'success') return done(response.data.errors);

                done(null, product);
            });
        } else {
            done(null, product);
        }

    }

    return this;
}