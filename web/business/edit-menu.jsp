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
        <a href="" data-toggle="modal" data-target="#addCategoryModal">
            <img src="${pageContext.request.contextPath}/styles/resources/business/admin/plus-button.png">
        </a>
        <%@ include file="modals/add_category_modal.jsp" %>
    </div>
    <div id="categories">
        <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#home">Drinks</a></li>
            <li><a data-toggle="tab" href="#menu1">Appertize</a></li>
            <li><a data-toggle="tab" href="#menu2">Soups</a></li>
        </ul>

        <div class="tab-content">
            <div id="home" class="tab-pane fade in active">
                <div class="container products-container">
                    <div class="col-lg-6 product product-left">
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
                    <div class="col-lg-6 product product-right">
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
            <div class="dropup" style="max-width: 300px; display: inline-block;">
                <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" type="button" style="width: 100%" >
                    Choose which venues to attach menu &nbsp;&nbsp;&nbsp;&nbsp;<span class="caret"></span></button>
                <ul class="dropdown-menu attach-venues">
                    <li>
                        <a><span>Venue 1</span><div class="image-div"></div></a>
                    </li>
                    <li>
                        <a><span>Venue 2</span><div class="image-div"></div></a>
                    </li>
                    <li>
                        <a><span>Venue 3</span><div class="image-div"></div></a>
                    </li>
                    <li>
                        <a><span>Venue 4</span><div class="image-div"></div></a>
                    </li>
                </ul>
            </div>
            <div style="display: inline; float: right; right: 0;">
                <input type="button" class="btn btn-info" value="Save" style="width: 80px">
            </div>
        </div>
    </div>
</div>
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
</html>
