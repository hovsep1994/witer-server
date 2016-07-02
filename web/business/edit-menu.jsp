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
<body>
<%@ include file="business_header_loged_in.jsp" %>
<div class="container-fluid admin-content edit-menu-content">
    <div class="edit-menu-title">
        Hi !! Let's create your menu.
    </div>
    <hr size="5">
    <div style="padding-bottom: 50px">
        <div class="menu-info-inputs">
            <label for="menu-name"><h4>Menu Name</h4></label><br>
            <input type="text" class="form-control" id="menu-name">
        </div>
        <div class="menu-info-inputs">
            <label for="menu-language"><h4>Menu Language</h4></label><br>
            <select class="form-control" id="menu-language" disabled></select>
        </div>
        <div class="menu-info-inputs">
            <label for="menu-currency"><h4>Menu Currency</h4></label><br>
            <select class="form-control" id="menu-currency" disabled></select>
        </div>
    </div>
    <div id="add-category">
        <span>Add menu category</span>&nbsp;&nbsp;&nbsp;
        <img src="${pageContext.request.contextPath}/styles/resources/business/admin/plus-button.png">
    </div>
    <div id="categories">
        <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#home">Drinks</a></li>
            <li><a data-toggle="tab" href="#menu1">Appertize</a></li>
            <li><a data-toggle="tab" href="#menu2">Soups</a></li>
        </ul>

        <div class="tab-content">
            <div id="home" class="tab-pane fade in active">
                <div class="container">
                    <div class="col-lg-6 product">
                        <div class="product-buttons">
                            <button type="button" class="btn btn-primary delete">Delete Product</button>
                            <button type="button" class="btn btn-primary available">
                                <span class="text-available">Available</span>
                                <span class="text-not-available">Not available</span>
                            </button>
                        </div>
                        <div class="product-info">
                            <div class="col-lg-3">
                                <img src="${pageContext.request.contextPath}/styles/resources/business/admin/image-icon.png">
                            </div>
                            <div class="col-lg-6">
                                <input class="form-control">
                                <textarea class="form-control" style="height: 100px"></textarea>
                                <input class="form-control">
                            </div>
                            <div class="col-lg-3">
                                <select class="form-control">
                                    <option>Price Type</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 product">
                        <div class="product-buttons">
                            <button type="button" class="btn btn-primary delete">Delete Product</button>
                            <button type="button" class="btn btn-primary not-available">
                                <span class="text-available">Available</span>
                                <span class="text-not-available">Not available</span>
                            </button>
                        </div>
                        <div class="product-info">
                            <div class="col-lg-3">
                                <img src="${pageContext.request.contextPath}/styles/resources/business/admin/image-icon.png">
                            </div>
                            <div class="col-lg-6">
                                <input class="form-control">
                                <textarea class="form-control" style="height: 100px"></textarea>
                                <input class="form-control">
                            </div>
                            <div class="col-lg-3">
                                <select class="form-control">
                                    <option>Price Type</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 add-product">
                        <img src="${pageContext.request.contextPath}/styles/resources/business/admin/new-product.png"><br><br>
                        One more product
                    </div>
                </div>
            </div>
            <div id="menu1" class="tab-pane fade">
                <h3>Menu 1</h3>

                <p>Some content in menu 1.</p>
            </div>
            <div id="menu2" class="tab-pane fade">
                <h3>Menu 2</h3>

                <p>Some content in menu 2.</p>
            </div>
        </div>
    </div>
</div>
<div class="desc navbar-fixed-bottom">
    <div class="menu-attach">
        <div>
            <div class="dropup">
                <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                    Choose which venues to attach menu &nbsp;&nbsp;&nbsp;&nbsp;<span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li><a href="#">Venue 1</a></li>
                    <li><a href="#">Venue 2</a></li>
                    <li><a href="#">Venue 2</a></li>
                    <li><a href="#">Venue 2</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row footer">
        <div class="col-sm-12">All Right Reserved. MenuKit 2016</div>
    </div>
</div>
</body>
</html>
