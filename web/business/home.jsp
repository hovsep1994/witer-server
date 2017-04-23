<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 3/13/16
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MenuKit For Bussiness</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/commons.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/business/commons.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/business/business-admin.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/business/edit-menu.css">
</head>
<body ng-app="app">
<%@ include file="business_header_loged_in.jsp" %>
<div class="container admin-content">
    <div class="company-header">
        <h2>${user.company.name}</h2>
        <%--<h6> ivamus consequat ex ligula, a faucibus ex placerat sit amet.</h6>--%>
    </div>


    <div class="company-panel">
        <ul id="company_tabs" class="nav nav-pills nav-justified">
            <li class="active"><a data-toggle="pill" href="#venues">Venues</a></li>
            <li><a data-toggle="pill" href="#menus">Menus</a></li>
        </ul>

        <div class="tab-content">


            <%---- -------------------- Venues Section ------------------------%>
            <div ng-controller="venueCtrl" id="venues" class="venues-panel tab-pane fade in active">
                <div class="panel-headers-row row">
                    <div class="col-lg-3 col-md-3 panel-headers-col-left" style="padding: 8px 0 8px 0">
                        <span>VENUES</span> &nbsp;
                        <a href="" data-toggle="modal" data-target="#editVenueModal" ng-click="initEditVenue(true)">
                            <img src="${pageContext.request.contextPath}/styles/resources/business/admin/plus-button-black.png">
                        </a>
                    </div>
                    <div class="col-lg-9 col-md-9 panel-headers-col-right">INFORMATION</div>
                </div>
                <div class="content-row row">
                    <div class="item-list col-lg-3">
                        <ul class="nav nav-pills nav-stacked">
                            <li ng-repeat="venue in venues">
                                <span id="{{'venue_link' + venue.id}}" data-toggle="pill" href="{{'#venue_' + venue.id}}">{{venue.name}}</span>
                                <a href="" data-toggle="modal" data-target="#deleteVenueModal" ng-click="initRemove(venue.id)">
                                    <img src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-delete-icon.png">
                                </a>
                                <a href="" data-toggle="modal" data-target="#editVenueModal" ng-click="initEditVenue(false, venue)">
                                    <img src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-edit-icon.png">
                                </a>
                            </li>
                        </ul>
                        <%@ include file="modals/delete_venue_modal.jsp" %>
                        <%@ include file="modals/edit_venue_modal.jsp" %>
                    </div>

                    <div class="tab-content col-lg-9">
                        <div ng-repeat="venue in venues" id="{{'venue_' + venue.id}}" class="tab-pane fade{{venue.active ? ' in active' : ''}}">
                            <div id="venue-info">
                                <div class="container-fluid">
                                    <div class="row">
                                        <img src="{{venue.image}}"
                                             style="margin-right: 30px" align="center" width="100px">
                                        <span style="font-weight: bold">{{venue.name}}</span>
                                    </div>
                                    <br><br>

                                    <div class="venue-data row">
                                        <div style="">City</div>
                                        <span>{{venue.location.city}}</span><br>

                                        <div style="display: inline-block; min-width: 130px; font-weight: bold">
                                            Country
                                        </div>
                                        <span>{{venue.location.country}}</span><br>

                                        <div style="display: inline-block; min-width: 130px; font-weight: bold">Street
                                        </div>
                                        <span>{{venue.location.street}}</span><br>

                                    </div>
                                    <br><br>

                                    <div class="row">
                                        <div id="{{'map' + venue.id}}" style="min-height: 400px"></div>
                                        <br>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- ------------- Menu Section ------------------>
            <div ng-controller="menuCtrl" id="menus" class="tab-pane fade">
                <div class="panel-headers-row row">
                    <div class="col-lg-3 col-md-3 panel-headers-col-left" style="padding: 8px 0 8px 0; width: 25%">
                        <span>MENUS</span>  &nbsp;
                        <a href="" data-toggle="modal" data-target="#editMenuModal" ng-click="initEdit(true)">
                            <img src="${pageContext.request.contextPath}/styles/resources/business/admin/plus-button-black.png">
                        </a>
                    </div>
                    <div class="col-lg-1 col-md-1 panel-headers-col-middle" style="width: 12.5%">LANGUAGES</div>
                    <div class="col-lg-2 col-md-2 panel-headers-col-middle" style="width: 12.5%">CATEGORIES</div>
                    <div class="col-lg-6 col-md-6 panel-headers-col-right">PRODUCTS</div>
                    <%@ include file="modals/edit_menu_dialog.jsp" %>
                </div>

                <div class="content-row row">
                    <div class="item-list col-lg-3">
                        <ul class="nav nav-pills nav-stacked">
                            <li ng-repeat="menu in menus">
                                <span data-toggle="pill" ng-click="selectMenuByIndex($index)">{{menu.name}}</span>
                                <a href="" data-toggle="modal" data-target="#deleteVenueModal">
                                    <img src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-delete-icon.png">
                                </a>
                                <a href="" data-toggle="modal" data-target="#editMenuModal" ng-click="initEdit(false, menu)">
                                    <img src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-edit-icon.png">
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="item-list col-lg-1" style="width: 12.5%">
                        <ul class="nav nav-pills nav-stacked">
                            <li ng-repeat="language in activeMenu.languages" ng-click="selectLanguage(language)">
                                <span>{{language}}</span>
                                <a href="" data-toggle="modal" data-target="#deleteVenueModal">
                                    <img src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-delete-icon.png">
                                </a>
                                <a href="" data-toggle="modal" data-target="#editVenueModal">
                                    <img src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-edit-icon.png">
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="item-list col-lg-2" style="width: 12.5%">
                        <ul class="nav nav-pills nav-stacked">
                            <li ng-repeat="category in activeMenu.categories" ng-click="selectCategory(category)">
                                <span data-toggle="pill">{{category.name}}</span>
                            </li>
                        </ul>
                    </div>
                    <div class="col-lg-6" style="padding-left: 0; padding-right: 0; max-height: 115%; overflow-y: scroll; ">
                        <ul style="list-style:none; padding-left: 0px">
                            <li ng-repeat="product in activeCategory.products">
                                <div class="product-item" ng-style="product.style">
                                    <div class="col-lg-3">
                                        <img src="{{product.displayImage}}" width="120px"/>
                                    </div>
                                    <div class="col-lg-5">
                                        <h5>{{product.name}}</h5>
                                        <p>{{product.description}}</p>
                                        <p>{{product.tags}}</p>
                                    </div>
                                    <div class="col-lg-4">
                                        <div class="product-prices">
                                            <div ng-if="product.priceType=='multi'" class="product-multi-price">
                                                <div ng-repeat="price in product.prices" style="margin-bottom: 5px;">
                                                    <div style="display: inline-block; width: 50%;">{{price.name}}</div>
                                                    <div style="display: inline-block; width: 35%;">{{price.price}}</div>
                                                    <br>
                                                </div>
                                            </div>
                                            <div ng-if="product.priceType=='single'" style="padding-top: 30%;">
                                                <span>{{product.prices[0].price}}</span>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAW_hI04roTnEO08eq4tlKgNh4okOdMSTE"></script>
<script src="${pageContext.request.contextPath}/scripts/services/map.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/helper.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/user.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/venue.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/menu.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/category.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/product.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/app.js"></script>
<script src="${pageContext.request.contextPath}/scripts/controllers/user.ctrl.js"></script>
<script src="${pageContext.request.contextPath}/scripts/controllers/venue.ctrl.js"></script>
<script src="${pageContext.request.contextPath}/scripts/controllers/menu.ctrl.js"></script>
<script src="${pageContext.request.contextPath}/scripts/controllers/menu-edit.ctrl.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
crossorigin="anonymous"></script>

</html>
