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
                                 src="{{venue.image}}" width="100px" height="100px">
                        </label>
                        <input id="file-input" type="file" style="display: none"
                               onchange="angular.element(this).scope().image_changed(this)" />
                    </div>
                    <div class="inputs">
                        <input class="form-control" placeholder="*Name">
                        <input class="form-control" placeholder="Phone"><br>
                        <input class="form-control" placeholder="*Country">
                        <input class="form-control" placeholder="*City"><br>
                        <input class="form-control" placeholder="*Street">
                        <input class="form-control" placeholder="Zip"><br>
                    </div>
                    <br>
                </form>

                <div id="map" class="map"></div>
                <br>

                <div class="buttons">
                    <input type="button" class="btn btn-info" value="Save">
                    <input type="button" class="btn btn-link" value="Cancel" data-dismiss="modal">
                </div>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
