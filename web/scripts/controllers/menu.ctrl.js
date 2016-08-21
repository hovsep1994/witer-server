/**
 * @author shahenpoghosyan
 */
var app = angular.module('app');
app.controller('menuCtrl', ['$scope', 'menuService', 'venueService', function ($scope, menuService, venueService) {


    var self = $scope;

    self.update = update;
    self.initEdit = initEdit;
    self.selectVenue = selectVenue;

    self.editMenu = {};

    initVenues();

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

}]);