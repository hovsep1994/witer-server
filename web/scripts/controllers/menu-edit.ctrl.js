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
        self.selectCategory = selectCategory;

        self.addProduct = addProduct;
        self.checkAndUpdateProduct = checkAndUpdateProduct;
        self.checkAndUpdateProductImage = checkAndUpdateProductImage;
        self.selectProduct = selectProduct;
        self.removeProduct = removeProduct;
        self.removePrice = removePrice;
        self.addPrice = addPrice;
        self.getProductClass = getProductClass;
        self.randomId = randomId;

        self.menu = {};
        self.category = {};
        self.editProduct = {};

        menuService.findById(getMenuId(), function (err, menu) {
            if (err) return console.log(err);

            self.menu = menu;
            self.menu.categories = self.menu.categories.map(function (category) {
                category = convertToCategoryCtrlModel(category);
                category.products = category.products.map(function(p) {
                    return convertToProductCtrlModel(p);
                });
                addProduct(category);
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
                    selectCategory(self.menu.categories[0]);
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
                $('#addCategoryModal').modal('toggle');
                $('.modal-backdrop').hide();
            }
        }

        function addProduct(category) {
            category.products = (category.products || []).concat({
                new: true,
                prices: [{}],
                priceType: 'single',
                displayImage: "/styles/resources/business/admin/image-icon.png"
            });
        }

        function checkAndUpdateProduct(product) {
            var productModel = convertToProductModel(product, self.category, self.menu);
            if (checkProduct(productModel)) {
                if(product.new) {
                    productService.create(productModel, function(err, p) {
                        product.id = p.id;
                        addProduct(self.category);
                    });
                } else {
                    productService.update(productModel.id, productModel);
                }
            }
        }

        function checkAndUpdateProductImage(event, id) {
            var product = self.editProduct;
            var files = event.target.files;
            var photofile = files[0];
            var reader = new FileReader();
            reader.onload = function (e) {
                self.$apply(function () {
                    product.displayImage = e.target.result;
                    product.image = photofile;

                    checkAndUpdateProduct(product);
                });
            };
            reader.readAsDataURL(photofile);
        }

        function removeProduct(product) {
            productService.remove(product.id, function(err) {
                if(err) return console.log(err);

                self.category.products = self.category.products.filter(function(p) {
                    return p.id != product.id;
                });
                //$('#deleteProductModal').hide();
                $('#deleteProductModal').modal('toggle');
                $('.modal-backdrop').hide();
            });
        }

        function addPrice(product) {
            product.prices = product.prices.concat({});
        }

        function removePrice(product, index) {
            product.prices.splice(index, 1);
            checkAndUpdateProduct(product);
        }

        function selectProduct(product) {
            self.editProduct = product;
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
            var p = {
                id: product.id,
                name: product.name,
                tags: (product.tags || '').split(','),
                description: product.description,
                image: product.image,
                categoryId: category.id,
                language: menu.mainLanguage
            };
            if (product.priceType == 'single') {
                product.prices[0].name = "";
                p.productPrices = [product.prices[0]];
            } else {
                p.productPrices = product.prices.filter(function (p) {
                    return p.name;
                });
            }
            return p;
        }

        function convertToProductCtrlModel(p) {
            p.displayImage = p.image ? p.image : "/styles/resources/business/admin/image-icon.png";
            delete p.image;

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

        function randomId(product) {
            if(!product.random) {
                product.random = "random-" + Math.floor(Math.random() * 1000) + 1;
            }
            return product.random;
        }
    }]);
