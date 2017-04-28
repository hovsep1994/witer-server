/**
 * @author shahenpoghosyan
 */


function ProductService($http, host) {

    var productUrl = host + "/api/products/";

    this.create = create;
    this.update = update;
    this.remove = remove;

    function create(product, done) {
        if(!done) {
            done = function() { }
        }

        $http.post(productUrl, product).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);
            console.log("produc service: ", product.productPrices);

            var createdProduct = response.data.response;
            createdProduct.image = product.image;
            if(createdProduct.image) {
                updateProductImage(createdProduct, function (err, url) {
                    if (err) return done(err);

                    createdProduct.image = url;
                    done(null, createdProduct)
                });
            } else {
                done(null, createdProduct);
            }
        });
    }

    function update(productId, product, done) {
        if(!done) {
            done = function() { }
        }

        $http.put(productUrl + productId, product).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            var createdProduct = response.data.response;
            createdProduct.image = product.image;

            if(createdProduct.image) {
                updateProductImage(createdProduct, function (err, url) {
                    if (err) return done(err);

                    createdProduct.image = url;
                    done(null, createdProduct)
                });
            } else {
                done(null, createdProduct);
            }
        });
    }

    function remove(id, done) {
        if (!done) {
            done = function () {};
        }
        $http.delete(productUrl + id).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });

    }

    function updateProductImage(product, done) {
        var fd = new FormData();
        fd.append('file', product.image);
        $http.post(productUrl + product.id + "/image", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    return this;
}