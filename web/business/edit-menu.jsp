<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 6/25/16
  Time: 8:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/business/edit-menu.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/resources/bootstrap/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/commons.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/business/commons.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
            integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
            crossorigin="anonymous"></script>

</head>
<body ng-app="app">
<%@ include file="business_header_loged_in.jsp" %>
<div ng-controller="editMenuCtrl" class="container-fluid admin-content edit-menu-content">
    <div class="edit-menu-title">
        Hi !! Let's create your menu.
    </div>
    <hr size="5">
    <%--<div class="container" style="padding-bottom: 50px">--%>
        <%--<div class="menu-info-inputs">--%>
            <%--<label for="menu-name"><h4>Menu Name</h4></label><br>--%>
            <%--<input type="text" class="form-control" id="menu-name">--%>
        <%--</div>--%>
        <%--<div class="menu-info-inputs">--%>
            <%--<label for="menu-language"><h4>Menu Language</h4></label><br>--%>
            <%--<select class="form-control" id="menu-language" disabled></select>--%>
        <%--</div>--%>
        <%--<div class="menu-info-inputs">--%>
            <%--<label for="menu-currency"><h4>Menu Currency</h4></label><br>--%>
            <%--<select class="form-control" id="menu-currency" disabled></select>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="container" id="add-category">
        <span>Add menu category</span>&nbsp;&nbsp;&nbsp;
        <a href="" data-toggle="modal" data-target="#addCategoryModal">
            <img src="${pageContext.request.contextPath}/styles/resources/business/admin/plus-button.png">
        </a>
        <%@ include file="modals/add_category_modal.jsp" %>
    </div>
    <div class="container" id="categories">
        <ul class="nav nav-tabs">
            <li ng-repeat="category in menu.categories" ng-click="selectCategory(category)" class="{{category.active ? 'active' : ''}}">
                <a data-toggle="tab" href="#category{{category.id}}-products">
                    {{category.name}} &nbsp;&nbsp;
                    <img style="cursor:pointer;" data-toggle="modal" data-target="#addCategoryModal"
                         src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-edit-icon.png">
                    &nbsp;
                    <img style="cursor:pointer;" data-toggle="modal" data-target="#deleteCategoryModal"
                         src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-delete-icon.png">
                </a>
                <%--<a href="" data-toggle="modal" data-target="#deleteVenueModal" ng-click="initRemove(venue.id)">--%>
                <%--</a>--%>
            </li>
            <%@ include file="modals/delete_category_modal.jsp" %>
            <%--<%@ include file="modals/edit_venue_modal.jsp" %>--%>
        </ul>

        <div class="tab-content">
            <div ng-repeat="category in menu.categories" id="category{{category.id}}-products" class="tab-pane fade {{category.active ? 'in active' : ''}}">
                <div class="products-container">
                    <div ng-repeat="product in category.products" class="col-lg-6 product {getProductClass($index)}">
                        <div class="product-buttons">
                            <button type="button" class="btn btn-primary delete">Delete Product</button>
                            <button type="button" class="btn btn-primary not-available">
                                <span class="text-available">Available</span>
                                <span class="text-not-available">Not available</span>
                            </button>
                        </div>
                        <div class="product-info">
                            <div class="grid-padding-small col-lg-3" style="width: 20%; padding-left: 10px; padding-right: 0px">
                                <div class="image" >
                                    <label for="file-input">
                                        <img style="cursor: pointer;"
                                             src="{{product.image}}" width="95px" height="95px">
                                    </label>
                                    <input accept=".png,.jpg,.jpeg"  id="file-input" type="file" style="display: none"
                                           onchange="angular.element(this).scope().image_changed(this)" />
                                </div>
                            </div>
                            <div class="grid-padding-small col-lg-6" style="width: 45%; padding-left: 5px; padding-right: 0px">
                                <input class="form-control" ng-blur="checkAndUpdateProduct(product)" ng-model="product.name">
                                <textarea class="form-control" style="height: 100px" ng-blur="checkAndUpdateProduct(product)" ng-model="product.description"></textarea>
                                <input class="form-control" ng-blur="checkAndUpdateProduct(product)" ng-model="product.tags">
                            </div>
                            <div class="col-lg-3 grid-padding-small" style="width: 35%; padding-left: 10px; padding-right: 10px">
                                <select class="form-control" ng-model="product.priceType">
                                    <option selected="selected" value="single">Single Price</option>
                                    <option value="multi">Multi Price</option>
                                </select><br>
                                <div style="overflow-y: scroll; max-height: 130px;">
                                    <div ng-if="product.priceType=='multi'" ng-repeat="price in product.prices" style="margin-bottom: 5px;">
                                        <div style="display: inline-block; width: 40%;">
                                            <input placeholder="type" class="form-control" type="text" ng-model="price.name">
                                        </div>
                                        <div style="display: inline-block; width: 35%;">
                                            <input placeholder="price" class="form-control" type="number" ng-model="price.price">
                                        </div>
                                        <div style="display: inline-block; width: 20%; padding-left: 2px;">
                                            <img style="cursor: pointer" ng-click="addPrice(product)" src="${pageContext.request.contextPath}/styles/resources/business/admin/plus-button.png">
                                        </div>
                                    </div>
                                </div>
                                <div ng-if="product.priceType=='single'">
                                    <input class="form-control" type="number" ng-model="product.price" placeholder="Price">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 add-product">
                        <img src="${pageContext.request.contextPath}/styles/resources/business/admin/new-product.png"><br><br>
                        One more product
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--<div class="desc navbar-fixed-bottom">--%>
    <%--<div class="menu-attach">--%>
        <%--<div>--%>
            <%--<div class="dropup" style="max-width: 300px; display: inline-block;">--%>
                <%--<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" type="button" style="width: 100%" >--%>
                    <%--Choose which venues to attach menu &nbsp;&nbsp;&nbsp;&nbsp;<span class="caret"></span></button>--%>
                <%--<ul class="dropdown-menu attach-venues">--%>
                    <%--<li>--%>
                        <%--<a><span>Venue 1</span><div class="image-div"></div></a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a><span>Venue 2</span><div class="image-div"></div></a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a><span>Venue 3</span><div class="image-div"></div></a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a><span>Venue 4</span><div class="image-div"></div></a>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            <%--</div>--%>
            <%--<div style="display: inline; float: right; right: 0;">--%>
                <%--<input type="button" class="btn btn-info" value="Save" style="width: 80px">--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
</body>
<script>
    $(document).ready(function() {

        $(document).on('click', '.dropdown-menu', function (e) {
            e.stopPropagation();
        });

        $('.attach-venues li').on('click', function (event) {
            if($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            } else {
                $(this).toggleClass('selected');
            }
        });
    });

</script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/map.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/helper.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/user.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/venue.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/menu.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/category.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/services/product.srv.js"></script>
<script src="${pageContext.request.contextPath}/scripts/app.js"></script>
<script src="${pageContext.request.contextPath}/scripts/controllers/user.ctrl.js"></script>
<script src="${pageContext.request.contextPath}/scripts/controllers/menu-edit.ctrl.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>
</html>
