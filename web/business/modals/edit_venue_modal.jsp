<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 3/27/16
  Time: 2:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="editVenueModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">VENUE</div>
            <div class="modal-body">
                <form>
                    <div class="image" >
                        <label for="file-input">
                            <img class="img-circle" style="cursor: pointer;"
                                 src="{{editVenue.image}}" width="100px" height="100px">
                        </label>
                        <input accept=".png,.jpg,.jpeg"  id="file-input" type="file" style="display: none"
                               onchange="angular.element(this).scope().image_changed(this)" />
                    </div>
                    <div class="inputs">
                        <input class="form-control" placeholder="*Name" value="{{editVenue.name}}" ng-change="onEditVenueChange()" ng-model="editVenue.name">
                        <input class="form-control" placeholder="*Country" value="{{editVenue.location.country}}" ng-change="onEditVenueChange()" ng-model="editVenue.location.country"><br>
                        <input class="form-control" placeholder="*City" value="{{editVenue.location.city}}" ng-change="onEditVenueChange()" ng-model="editVenue.location.city">
                        <input class="form-control" placeholder="*Street" value="{{editVenue.location.street}}" ng-change="onEditVenueChange()" ng-model="editVenue.location.street"><br>
                        <input class="form-control" style="float: left; left: 0;" placeholder="Zip" value="{{editVenue.location.zip}}" ng-change="onEditVenueChange()" ng-model="editVenue.location.zip"><br>
                    </div>
                    <br>
                </form>

                <div id="map" class="map"></div>
                <br>

                <div class="buttons">
                    <input type="button" class="btn btn-info" value="Save" ng-click="update(editVenue)">
                    <input type="button" class="btn btn-link" value="Cancel" data-dismiss="modal">
                </div>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
