/**
 * @author shahenpoghosyan
 */


var app = angular.module('app');
app.controller('editMenuCtrl', ['$scope', 'menuService', 'categoryService', function ($scope, menuService, categoryService) {

    var self = $scope;

    self.addCategory = addCategory;
    self.getProductClass = getProductClass;
    self.selectCategory = selectCategory;
    self.checkAndUpdateProduct = checkAndUpdateProduct;
    self.image_changed = image_changed;

    self.menu = { };
    self.category = {};
    self.newProduct = {};

    menuService.findById(getMenuId(), function (err, menu) {
        if (err) return console.log(err);

        self.menu = menu;
        if(!menu.categories || !menu.categories.length) {
            self.menu.categories = [{
                name: "drinks",
                tags: "juice, bear",
                menuId: self.menu.id,
                language: self.menu.mainLanguage
            }];
        }
        self.menu.categories[0].active = true;
    });


    function getMenuId() {
        var tokens = location.href.split("/");
        if (tokens[tokens.length - 1])
            return tokens[tokens.length - 1];
        return tokens[tokens.length - 2];
    }

    function addCategory(category) {
        categoryService.create(convertToCategoryModel(category), function(err, category) {
            if(err) return console.log(err);

            self.menu.categories.push(category)
        });
    }

    function checkAndUpdateProduct(product) {
        if(checkProduct(product)) {
            alert(product);
        }
    }

    function image_changed(element) {
        var photofile = element.files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            self.$apply(function () {
                self.category.imageDisplay = e.target.result;
                self.category.image = photofile;
            });
        };
        reader.readAsDataURL(photofile);
    }


    function convertToCategoryModel(category) {
        return {
            name: category.name,
            tags: category.tags.split(","),
            image: category.image,
            menuId: self.menu.id,
            language: self.menu.mainLanguage
        }
    }

    function getProductClass(index) {
        return index % 2 == 0 ? 'product-left' : 'product-right';
    }

    function selectCategory(category) {
        self.menu.categories.forEach(function(c) {
            c.active = false;
        });
        category.active = true;
    }

    function checkProduct(product) {
        return product.name;
    }

}]);
