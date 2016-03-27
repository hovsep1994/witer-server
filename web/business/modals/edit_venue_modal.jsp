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
                <div class="image">
                    <img src="${pageContext.request.contextPath}/styles/resources/business/admin/venue-image.png">
                </div>
                <div class="inputs">
                    <input class="form-control" placeholder="*Name">
                    <input class="form-control" placeholder="Phone"><br>
                    <input class="form-control" placeholder="*Country">
                    <input class="form-control" placeholder="*City"><br>
                    <input class="form-control" placeholder="*Street">
                    <input class="form-control" placeholder="Zip"><br>
                </div><br>

                <div id="map" class="map"></div><br>
                <div class="buttons">
                    <input type="button" class="btn btn-info" value="Save">
                    <input type="button" class="btn btn-link" value="Cancel">
                </div>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
