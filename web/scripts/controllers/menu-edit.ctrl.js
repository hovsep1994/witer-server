/**
 * @author shahenpoghosyan
 */


var app = angular.module('app');
app.controller('editMenuCtrl', ['$scope', 'menuService', 'categoryService', 'productService',
    function ($scope, menuService, categoryService, productService) {

        var self = $scope;

        self.addCategory = addCategory;
        self.getProductClass = getProductClass;
        self.selectCategory = selectCategory;
        self.checkAndUpdateProduct = checkAndUpdateProduct;
        self.addPrice = addPrice;
        self.image_changed = image_changed;

        self.menu = {};
        self.category = {};
        self.newProduct = {
            prices: [{}],
            priceType: 'single',
            image: "/styles/resources/business/admin/image-icon.png"
        };

        menuService.findById(getMenuId(), function (err, menu) {
            if (err) return console.log(err);

            self.menu = menu;
            if (!menu.categories || !menu.categories.length) {
                self.menu.categories = [{
                    name: "drinks",
                    tags: "juice, bear",
                    menuId: self.menu.id,
                    language: self.menu.mainLanguage
                }];
            }
            self.menu.categories[0].active = true;
            self.menu.categories.forEach(function(category) {
                category.products = (category.products || []).concat({
                    prices: [{}],
                    priceType: 'single',
                    image: "/styles/resources/business/admin/image-icon.png"
                });
            })
        });


        function getMenuId() {
            var tokens = location.href.split("/");
            if (tokens[tokens.length - 1])
                return tokens[tokens.length - 1];
            return tokens[tokens.length - 2];
        }

        function addCategory(category) {
            categoryService.create(convertToCategoryModel(category), function (err, category) {
                if (err) return console.log(err);

                self.menu.categories.push(category)
            });
        }

        function checkAndUpdateProduct(product) {
            if (checkProduct(product)) {
                productService.create(convertToProductModel(self.newProduct, self.category, self.menu));
                alert(product);
            }
        }

        function addPrice(product) {
            product.prices = product.prices.concat({});
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

        function convertToProductModel(product, category, menu) {
            var p = {
                name: product.name,
                tags: (product.tags || '').split(','),
                description: product.description,
                categoryId: category.id,
                language: menu.mainLanguage
            };
            if(product.prototype == 'single') {
                p.prices = [{
                    name: "",
                    price: product.price
                }];
            } else {
                p.prices = product.prices.filter(function(p) {
                    return p.name;
                });
            }
            return p;
        }

        function getProductClass(index) {
            return index % 2 == 0 ? 'product-left' : 'product-right';
        }

        function selectCategory(category) {
            self.menu.categories.forEach(function (c) {
                c.active = false;
            });
            category.active = true;
            self.category = category;
        }

        function checkProduct(product) {
            return product.name;
        }


        self.range = function(min, max, step) {
            step = step || 1;
            var input = [];
            for (var i = min; i <= max; i += step) {
                input.push(i);
            }
            return input;
        };

    }]);
