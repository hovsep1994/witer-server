<!DOCTYPE html>
<html ng-app="app" ng-controller="venuePageCtrl">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="#">
    <title>{{venue.name}} - Menu</title>
    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/font-awesome.min.css" rel="stylesheet">
    <link href="/css/animsition.min.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="/css/style.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/styles/home.css">
    <link rel="stylesheet" type="text/css" href="/styles/commons.css">

</head>

<body>
    <div class="site-wrapper animsition" data-animsition-in="fade-in" data-animsition-out="fade-out">
        <!--header starts-->
        <!--<nav class="navbar navbar-fixed-top navbar_custom">-->
            <!--<div class="container-fluid">-->
                <!--<a class="brand brand-responsive" href="/">-->
                    <!--<img src="/styles/resources/commons/logo.png" width="55px"-->
                         <!--style="margin-top: -10px; margin-right: -5px;">-->
                    <!--Menu<span style="color:#ffcc00">Kit</span>-->
                <!--</a>-->
                <!--<a class="navbar_bussiness" href="./business/" >Business</a>-->
            <!--</div>-->
        <!--</nav>-->
        <header id="header" class="header-scroll top-header headrom">
            <!-- .navbar -->
            <nav class="navbar navbar-dark">
                <div class="container">
                    <a class="brand" href="/">
                        <img src="/styles/resources/commons/logo.png" width="60px"
                             style="margin: -20px -5px -10px -10px;">
                        Menu<span style="color:#ffcc00">Kit</span>
                    </a>
                    <a class="navbar_bussiness_template" href="/business/" >Business</a>
                </div>
            </nav>
            <!-- /.navbar -->
        </header>
        <div class="page-wrapper">
            <!-- end:Top links -->
            <!-- start: Inner page hero -->
            <section class="inner-page-hero" data-image-src="{{venue.image}}" style="background: url('{{venue.image}}') center center / cover no-repeat;">
                <div class="profile">
                    <div class="container">
                        <div class="row">
                            <div ng-if="venue.logo" class="col-xs-12 col-sm-12  col-md-4 col-lg-4 profile-img">
                                <div class="image-wrap">
                                    <figure><img src="{{venue.logo}}" alt="Profile Image"></figure>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8 profile-desc">
                                <div class="pull-left right-text white-txt">
                                    <h6><a href="#">{{venue.name}}</a></h6>
                                    <p>{{venue.location.address}}</p>
                                    <!--<ul class="nav nav-inline">-->
                                        <!--<li class="nav-item"> <a class="nav-link active" href="#"><i class="fa fa-check"></i> Min $ 10,00</a> </li>-->
                                        <!--<li class="nav-item"> <a class="nav-link" href="#"><i class="fa fa-motorcycle"></i> 30 min</a> </li>-->
                                        <!--<li class="nav-item ratings">-->
                                            <!--<a class="nav-link" href="#"> <span>-->
                                    <!--<i class="fa fa-star"></i>-->
                                    <!--<i class="fa fa-star"></i>-->
                                    <!--<i class="fa fa-star"></i>-->
                                    <!--<i class="fa fa-star"></i>-->
                                    <!--<i class="fa fa-star-o"></i>-->
                                    <!--</span> </a>-->
                                        <!--</li>-->
                                    <!--</ul>-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- end:Inner page hero -->
            <div class="container m-t-30">
                <div class="row">
                    <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                        <div class="sidebar clearfix m-b-20">
                            <div class="main-block">
                                <div class="sidebar-title white-txt">
                                    <h6>Choose Cusine</h6> <i class="fa fa-cutlery pull-right"></i> </div>
                                <ul>
                                    <li ng-repeat="category in venue.menu.categories"><a href="#category_{{category.id}}" class="scroll active">{{category.name}}</a></li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <!-- end:Sidebar nav -->
                        </div>
                        <!-- end:Left Sidebar -->
                    </div>
                    <div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">
                        <div ng-repeat="category in venue.menu.categories" class="menu-widget m-b-30">
                            <div class="widget-heading">
                                <h3 class="widget-title text-dark" id="category_{{category.id}}">
                              {{category.name}} <a class="btn btn-link pull-right" data-toggle="collapse" href="#popular" aria-expanded="true">
                              <i class="fa fa-angle-right pull-right"></i>
                              <i class="fa fa-angle-down pull-right"></i>
                              </a>
                           </h3>
                                <div class="clearfix"></div>
                            </div>
                            <div class="collapse in">
                                <div ng-repeat="product in category.products" class="food-item {{$index%2 ? '' : 'white'}}">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-lg-8">
                                            <div class="rest-logo pull-left">
                                                <a class="restaurant-logo pull-left"><img src="{{product.image}}" alt="Food logo"></a>
                                            </div>
                                            <!-- end:Logo -->
                                            <div class="rest-descr">
                                                <h6><a>{{product.name}}</a></h6>
                                                <p>{{product.description}}</p>
                                            </div>
                                            <!-- end:Description -->
                                        </div>
                                        <!-- end:col -->
                                        <div class="col-xs-12 col-sm-12 col-lg-4 pull-right item-cart-info"> <span class="price pull-right">{{product.productPrices[0].currency}} {{product.productPrices[0].price}} </span> </div>
                                    </div>
                                    <!-- end:row -->
                                </div>
                                <!-- end:Food item -->
                            </div>
                            <!-- end:Collapse -->
                        </div>
                        <!-- end:Widget menu -->
                    </div>
                    <!-- end:Bar -->
                </div>
                <!-- end:row -->
            </div>
            <!-- end:Container -->
            <section class="app-section">
                <div class="app-wrap">
                    <div class="container">
                        <div class="row text-img-block text-xs-left">
                            <div class="container">
                                <div class="col-xs-12 col-sm-6 hidden-xs-down right-image text-center">
                                    <figure> <img src="/images/app.png" alt="Right Image"> </figure>
                                </div>
                                <div class="col-xs-12 col-sm-6 left-text">
                                    <h3>The Best Food Delivery App</h3>
                                    <p>Now you can make food happen pretty much wherever you are thanks to the free easy-to-use Food Delivery &amp; Takeout App.</p>
                                    <div class="social-btns">
                                        <a href="https://play.google.com/store/apps/details?id=com.menukit.android" class="app-btn android-button clearfix">
                                            <div class="pull-left"><i class="fa fa-android"></i> </div>
                                            <div class="pull-right"> <span class="text">Available on the</span> <span class="text-2">Play store</span> </div>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- start: FOOTER -->
            <footer class="footer" style="padding-bottom: 15px; padding-top:15px; ">
                <div class="container">
                    <!--<div class="row footer">-->
                        <div class="col-sm-12">All Right Reserved. MenuKit 2017</div>
                    <!--</div>-->
                    <!-- bottom footer ends -->
                </div>
            </footer>
            <!-- end:Footer -->
        </div>
        <!-- end:page wrapper -->
    </div>
    <!--/end:Site wrapper -->
    <!-- Modal -->
    <div class="modal fade" id="order-modal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"> <span aria-hidden="true">&times;</span> </button>
                <div class="modal-body cart-addon">
                    <div class="food-item white">
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-lg-6">
                                <div class="item-img pull-left">
                                    <a class="restaurant-logo pull-left" href="#"><img src="http://placehold.it/70x70" alt="Food logo"></a>
                                </div>
                                <!-- end:Logo -->
                                <div class="rest-descr">
                                    <h6><a href="#">Sandwich de Alegranza Grande Menü (28 - 30 cm.)</a></h6> </div>
                                <!-- end:Description -->
                            </div>
                            <!-- end:col -->
                            <div class="col-xs-6 col-sm-2 col-lg-2 text-xs-center"> <span class="price pull-left">$ 2.99</span></div>
                            <div class="col-xs-6 col-sm-4 col-lg-4">
                                <div class="row no-gutter">
                                    <div class="col-xs-7">
                                        <select class="form-control b-r-0" id="exampleSelect2">
                                            <option>Size SM</option>
                                            <option>Size LG</option>
                                            <option>Size XL</option>
                                        </select>
                                    </div>
                                    <div class="col-xs-5">
                                        <input class="form-control" type="number" value="0" id="quant-input-2"> </div>
                                </div>
                            </div>
                        </div>
                        <!-- end:row -->
                    </div>
                    <!-- end:Food item -->
                    <div class="food-item">
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-lg-6">
                                <div class="item-img pull-left">
                                    <a class="restaurant-logo pull-left" href="#"><img src="http://placehold.it/70x70" alt="Food logo"></a>
                                </div>
                                <!-- end:Logo -->
                                <div class="rest-descr">
                                    <h6><a href="#">Sandwich de Alegranza Grande Menü (28 - 30 cm.)</a></h6> </div>
                                <!-- end:Description -->
                            </div>
                            <!-- end:col -->
                            <div class="col-xs-6 col-sm-2 col-lg-2 text-xs-center"> <span class="price pull-left">$ 2.49</span></div>
                            <div class="col-xs-6 col-sm-4 col-lg-4">
                                <div class="row no-gutter">
                                    <div class="col-xs-7">
                                        <select class="form-control b-r-0" id="exampleSelect3">
                                            <option>Size SM</option>
                                            <option>Size LG</option>
                                            <option>Size XL</option>
                                        </select>
                                    </div>
                                    <div class="col-xs-5">
                                        <input class="form-control" type="number" value="0" id="quant-input-3"> </div>
                                </div>
                            </div>
                        </div>
                        <!-- end:row -->
                    </div>
                    <!-- end:Food item -->
                    <div class="food-item">
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-lg-6">
                                <div class="item-img pull-left">
                                    <a class="restaurant-logo pull-left" href="#"><img src="http://placehold.it/70x70" alt="Food logo"></a>
                                </div>
                                <!-- end:Logo -->
                                <div class="rest-descr">
                                    <h6><a href="#">Sandwich de Alegranza Grande Menü (28 - 30 cm.)</a></h6> </div>
                                <!-- end:Description -->
                            </div>
                            <!-- end:col -->
                            <div class="col-xs-6 col-sm-2 col-lg-2 text-xs-center"> <span class="price pull-left">$ 1.99</span></div>
                            <div class="col-xs-6 col-sm-4 col-lg-4">
                                <div class="row no-gutter">
                                    <div class="col-xs-7">
                                        <select class="form-control b-r-0" id="exampleSelect5">
                                            <option>Size SM</option>
                                            <option>Size LG</option>
                                            <option>Size XL</option>
                                        </select>
                                    </div>
                                    <div class="col-xs-5">
                                        <input class="form-control" type="number" value="0" id="quant-input-4"> </div>
                                </div>
                            </div>
                        </div>
                        <!-- end:row -->
                    </div>
                    <!-- end:Food item -->
                    <div class="food-item">
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-lg-6">
                                <div class="item-img pull-left">
                                    <a class="restaurant-logo pull-left" href="#"><img src="http://placehold.it/70x70" alt="Food logo"></a>
                                </div>
                                <!-- end:Logo -->
                                <div class="rest-descr">
                                    <h6><a href="#">Sandwich de Alegranza Grande Menü (28 - 30 cm.)</a></h6> </div>
                                <!-- end:Description -->
                            </div>
                            <!-- end:col -->
                            <div class="col-xs-6 col-sm-2 col-lg-2 text-xs-center"> <span class="price pull-left">$ 3.15</span></div>
                            <div class="col-xs-6 col-sm-4 col-lg-4">
                                <div class="row no-gutter">
                                    <div class="col-xs-7">
                                        <select class="form-control b-r-0" id="exampleSelect6">
                                            <option>Size SM</option>
                                            <option>Size LG</option>
                                            <option>Size XL</option>
                                        </select>
                                    </div>
                                    <div class="col-xs-5">
                                        <input class="form-control" type="number" value="0" id="quant-input-5"> </div>
                                </div>
                            </div>
                        </div>
                        <!-- end:row -->
                    </div>
                    <!-- end:Food item -->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn theme-btn">Add to cart</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <script src="/js/jquery.min.js"></script>
    <script src="/js/tether.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/animsition.min.js"></script>
    <script src="/js/bootstrap-slider.min.js"></script>
    <script src="/js/jquery.isotope.min.js"></script>
    <script src="/js/headroom.js"></script>
    <script src="/js/foodpicky.min.js"></script>
    <script src="/scripts/services/helper.srv.js"></script>
    <script src="/scripts/services/venue.srv.js"></script>
    <script src="/scripts/services/map.srv.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.min.js"></script>
    <script src="/scripts/app.js"></script>
    <script src="/scripts/controllers/venue-page.ctrl.js" type="text/javascript"></script>
    <%@ include file="../google-analytics.jsp" %>
</body>

</html>