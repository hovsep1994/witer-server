/**
 * @author shahenpoghosyan
 */


var app = angular.module('app');
app.controller('editMenuCtrl', ['$scope', 'menuService', 'categoryService', 'productService',
    function ($scope, menuService, categoryService, productService) {

        var self = $scope;

        self.addCategory = addCategory;
        self.updateCategory = updateCategory;
        self.newCategory = newCategory;
        self.removeCategory = removeCategory;

        self.getProductClass = getProductClass;
        self.selectCategory = selectCategory;
        self.checkAndUpdateProduct = checkAndUpdateProduct;
        self.addPrice = addPrice;
        self.removePrice = removePrice;

        self.image_changed = image_changed;

        self.menu = {};
        self.category = {};

        menuService.findById(getMenuId(), function (err, menu) {
            if (err) return console.log(err);

            self.menu = menu;
            self.menu.categories = self.menu.categories.map(function (category) {
                category = convertToCategoryCtrlModel(category);
                category.products = category.products.map(function(p) {
                    return convertToProductCtrlModel(p);
                });
                category.products = (category.products || []).concat({
                    new: true,
                    prices: [{}],
                    priceType: 'single',
                    image: "/styles/resources/business/admin/image-icon.png"
                });
                return category;
            });

            if (!menu.categories || !menu.categories.length) {
                self.menu.categories = [{
                    name: "drinks",
                    tags: "juice, bear",
                    menuId: self.menu.id,
                    language: self.menu.mainLanguage
                }];
            }
            self.category = self.menu.categories[0];
            self.category.active = true;
        });


        function getMenuId() {
            var tokens = location.href.split("/");
            if (tokens[tokens.length - 1])
                return tokens[tokens.length - 1];
            return tokens[tokens.length - 2];
        }

        function removeCategory(category) {
            categoryService.remove(category.id, function(err) {
                if(err) return console.log(err);

                self.menu.categories = self.menu.categories.filter(function(c) {
                    return c.id != category.id;
                });
                if(self.menu.categories.length) {
                    self.category = self.menu.categories[0];
                }
                $('#deleteCategoryModal').hide();
                $('.modal-backdrop').hide();
            })
        }

        function addCategory(category, done) {
            categoryService.create(convertToCategoryModel(category), function (err, category) {
                if (err) return console.log(err);

                self.menu.categories.push(category);
                done(null, category);
            });
        }

        function updateCategory(category) {
            if(category.new) {
                addCategory(category, process);
            } else {
                categoryService.update(category.id, convertToCategoryModel(category), process);
            }
            function process() {
                $('#addCategoryModal').hide();
                $('.modal-backdrop').hide();
            }
        }

        function checkAndUpdateProduct(product) {
            var productModel = convertToProductModel(product, self.category, self.menu);
            if (checkProduct(productModel)) {
                if(productModel.new) {
                    productService.create(productModel);
                } else {
                    productService.update(productModel.id, productModel);
                }
            }
        }

        function addPrice(product) {
            product.prices = product.prices.concat({});
        }

        function removePrice(product, index) {
            product.prices.splice(index, 1);
            checkAndUpdateProduct(product);
        }

        function image_changed(element, item) {
            var photofile = element.files[0];
            var reader = new FileReader();
            reader.onload = function (e) {
                self.$apply(function () {
                    item.displayImage = e.target.result;
                    item.image = photofile;
                    console.log("item: " , item)
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

        function convertToCategoryCtrlModel(category) {
            category.tags = category.tags.join(",");
            category.displayImage = category.image || "/styles/resources/business/admin/image-icon.png";
            delete category.image;
            return category;
        }

        function convertToProductModel(product, category, menu) {
            console.log("before convert: ", product);
            var p = {
                id: product.id,
                name: product.name,
                tags: (product.tags || '').split(','),
                description: product.description,
                categoryId: category.id,
                language: menu.mainLanguage
            };
            if (product.priceType == 'single') {
                p.productPrices = [product.prices[0]];
            } else {
                p.productPrices = product.prices.filter(function (p) {
                    return p.name;
                });
            }
            return p;
        }

        function convertToProductCtrlModel(p) {
            if(!p.image) {
                p.image = "/styles/resources/business/admin/image-icon.png";
            }
            p.tags = p.tags.join(",");
            if(p.productPrices.length == 1) {
                p.priceType = 'single';
            } else {
                p.priceType = 'multi'
            }
            p.prices = p.productPrices;
            delete p.productPrices;
            console.log("p: ", p);
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
            return product.name && product.productPrices.filter(function (p) {

                    return p.price;
                }).length;
        }

        function newCategory() {
            self.category = {
                new: true,
                displayImage: "/styles/resources/business/admin/image-icon.png"
            };
        }

    }]);
