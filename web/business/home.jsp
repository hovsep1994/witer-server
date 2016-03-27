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
</head>
<body ng-app="app">
<%@ include file="business_header_loged_in.jsp" %>
<div class="container admin-content">
    <div class="company-header">
        <h2>Paris Night Restaurant</h2>
        <h6> ivamus consequat ex ligula, a faucibus ex placerat sit amet.</h6>
    </div>
    <div class="company-panel">
        <ul id="company_tabs" class="nav nav-pills nav-justified">
            <li class="active"><a data-toggle="pill" href="#venues">Venues</a></li>
            <li><a data-toggle="pill" href="#menues">Menues</a></li>
        </ul>

        <div class="tab-content">
            <div id="venues" class="venues-panel tab-pane fade in active">
                <div class="venues-headers-row row">
                    <div class="col-lg-3 col-md-3 venues-headers-col-left">VENUES</div>
                    <div class="col-lg-9 col-md-9 venues-headers-col-right">INFORMATION</div>
                </div>
                <div class="venues-list-row row">
                    <div class="venues-list col-lg-3">
                        <ul class="nav nav-pills nav-stacked">
                            <li>
                                <span data-toggle="pill" href="#venue1">Paris Night Restaurant St. 1</span>
                                <a href="" data-toggle="modal" data-target="#deleteVenueModal">
                                    <img src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-delete-icon.png">
                                </a>
                                <a href="" data-toggle="modal" data-target="#editVenueModal">
                                    <img src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-edit-icon.png">
                                </a>
                            </li>
                            <li>
                                <span data-toggle="pill" href="#venue2">Paris Night Restaurant St. 2</span>
                                <a href=""><img
                                        src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-delete-icon.png"></a>
                                <a href=""><img
                                        src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-edit-icon.png"></a>
                            </li>
                            <div id="deleteVenueModal" class="modal fade" role="dialog">
                                <div class="modal-dialog" style="width: 400px">
                                    <div class="modal-content">
                                        delete modal
                                    </div>
                                </div>
                            </div>
                            <%@ include file="modals/edit_venue_modal.jsp"%>
                        </ul>
                    </div>

                    <div class="tab-content col-lg-9">
                        <div id="venue1" class="tab-pane fade in active">
                            INFORMATION1
                        </div>
                        <div id="venue2" class="tab-pane fade">
                            INFORMATION2
                        </div>
                    </div>
                </div>
            </div>
            <div id="menues" class="tab-pane fade">
                <h3>Menu 1</h3>

                <p>Some content in menu 1.</p>
            </div>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/user-controlller.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>

<script type="text/javascript">
    var map;
    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: 70, lng: 0},
            zoom: 2
        });
    }

    $("#editVenueModal").on("shown.bs.modal", function () {
        google.maps.event.trigger(map, "resize");
    });

</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAW_hI04roTnEO08eq4tlKgNh4okOdMSTE&callback=initMap">
</script>
</html>
