/**
 * @author shahenpoghosyan
 */


function CategoryService($http, host) {

    var categoryUrl = host + "/api/categories/";

    this.create = create;
    this.update = update;
    this.remove = remove;

    function create(category, done) {
        alert(JSON.stringify(category));
        if (!done) {
            done = function () {}
        }

        $http.post(categoryUrl, category).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            var createdCategory = response.data.response;
            createdCategory.image = category.image;
            if(createdCategory.image) {
                updateCategoryImage(createdCategory, function (err, url) {
                    if (err) return done(err);

                    createdCategory.image = url;
                    done(null, createdCategory)
                });
            } else {
                done(null, createdCategory);
            }
        });
    }

    function update(categoryId, category, done) {
        if (!done) {
            done = function () {}
        }

        $http.put(categoryUrl + categoryId, category).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            var createdCategory = response.data.response;
            createdCategory.image = category.image;
            if(createdCategory.image) {
                updateCategoryImage(createdCategory, function (err, url) {
                    if (err) return done(err);

                    createdCategory.image = url;
                    done(null, createdCategory)
                });
            } else {
                done(null, createdCategory);
            }
        });
    }

    function remove(id, done) {
        if (!done) {
            done = function () {};
        }
        $http.delete(categoryUrl + id).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });

    }

    function updateCategoryImage(category, done) {
        var fd = new FormData();
        fd.append('file', category.image);
        $http.post(categoryUrl + category.id + "/image", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    return this;
}