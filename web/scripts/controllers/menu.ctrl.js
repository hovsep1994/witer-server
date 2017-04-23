/**
 * @author shahenpoghosyan
 */
var app = angular.module('app');
app.controller('menuCtrl', ['$scope', 'menuService', 'venueService', function ($scope, menuService, venueService) {


    var self = $scope;

    self.update = update;
    self.initEdit = initEdit;
    self.selectMenuByIndex = selectMenuByIndex;
    self.selectLanguage = selectLanguage;
    self.selectVenue = selectVenue;
    self.selectCategory = selectCategory;

    self.editMenu = {};
    self.menus = [];

    initVenues();
    initMenus();


    function initMenus() {
        findAll(function() {
            self.activeMenu = self.menus[0];
            self.activeLanguage = self.activeMenu.languages[0];
            selectMenuByIndex(0);
        });
    }

    function findAll(done) {
        menuService.findAll(function(err, menus) {
            if(err) return console.log(err);

            self.menus = menus;
            done();
        });
    }

    function fetchByIndex(index, done) {
        menuService.findById(self.menus[index].id, function(err, menu) {
            if(err) return console.log(err);

            self.menus[index].categories = menu.categories;
            done(null, self.menus[index]);
        });
    }

    function initEdit(newMenu, menu) {
        if (newMenu) {
            self.editMenu.new = true;
        } else {
            self.editMenu = menu;
        }
    }

    function initVenues() {
        venueService.findUserVenues(function (err, venues) {
            if (err) return console.log(err);
            self.editMenu.venues = venues.filter(function (v) {
                return !v.menuId;
            });


            $(document).on('click', '.dropdown-menu', function (e) {
                e.stopPropagation();
            });

            $('#editMenuModal').on('shown.bs.modal', function (e) {
                $('.attach-venues li').on('click', function (event) {
                    if ($(this).hasClass('selected')) {
                        $(this).removeClass('selected');
                    } else {
                        $(this).toggleClass('selected');
                    }
                });
            });

        });
    }


    function update(menu) {

        var menuModel = convertToServiceModel(menu);

        function process(cb) {
            if (menu.new) {
                return menuService.create(menuModel, cb);
            }
            //return menuService.update(menuModel.id, menuModel, cb);
        }

        process(function () {
            $('#editMenuModal').modal('toggle');
        });
    }

    function selectVenue(venue) {
        venue.selected = !venue.selected;
    }

    function selectMenu(menu) {
        self.activeMenu = menu;
    }

    function selectMenuByIndex(index, done) {
        self.activeMenu = self.menus[index];
        fetchByIndex(index, function(err, menu) {
            if(err) return console.log(err);

            if(self.activeMenu.categories.length) {
                selectCategory(self.activeMenu.categories[0]);
            }
        });
    }

    function selectLanguage(language) {
        self.activeLanguage = language;
    }

    function selectCategory(category) {
        self.activeCategory = category;
        category.products = category.products.map(function(product, i) {
            return convertToProductCtrlModel(product, i);
        })
    }


    function convertToServiceModel(menu) {
        var menuModel = {};
        menuModel.id = menu.id;
        menuModel.name = menu.name;
        menuModel.currency = menu.currency.code;
        menuModel.mainLanguage = menu.mainLanguage.code;
        menuModel.venues = menu.venues.filter(function (v) {
            return v.selected;
        }).map(function (v) {
            return v.id;
        });
        alert(JSON.stringify(menuModel));
        return menuModel;
    }

    function convertToProductCtrlModel(p, i) {
        p.displayImage = p.image ? p.image + "/r200" : "/styles/resources/business/admin/image-icon.png";
        delete p.image;

        p.tags = p.tags.join(",");
        if(p.productPrices.length == 1) {
            p.priceType = 'single';
        } else {
            p.priceType = 'multi'
        }
        p.prices = p.productPrices;
        delete p.productPrices;

        p.style = i % 2 ? {'background-color' : 'white'} :  {'background-color': '#f4f6f7'};
        return p;
    }

    self.availableLanguages = [
        {
            code: "hy",
            name: "Armenian"
        },
        {
            code: "ru",
            name: "Russian"
        },
        {
            code: "hy",
            name: "Armenian"
        },
        {
            code: "nl",
            name: "Dutch"
        }
    ];

    self.availableCurrencies = [
        {
            code: "ARM",
            name: "ARM"
        },
        {
            code: "RUB",
            name: "RUB"
        },
        {
            code: "EUR",
            name: "EUR"
        }
    ];

    function getLangugeName(code) {
        var f = self.availableLanguages.filter(function(l) {
           return l.code === code;
        });
        return f.length ? f[0].name : undefined;
    }

}]);