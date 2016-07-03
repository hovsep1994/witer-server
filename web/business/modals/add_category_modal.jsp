<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 6/26/16
  Time: 11:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addCategoryModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">Category Name</div>
            <div class="modal-body">
                <form>
                    <div class="image col-lg-4">
                        <label for="file-input">
                            <img style="cursor: pointer;"
                                 src="${pageContext.request.contextPath}/styles/resources/business/admin/image-icon.png"
                                 width="100px" height="100px">
                        </label>
                        <input id="file-input" type="file" style="display: none"
                               onchange="angular.element(this).scope().image_changed(this)"/>
                    </div>
                    <div class="inputs col-lg-8">
                        <input class="form-control" placeholder="Name">
                        <input class="form-control" placeholder="Tags"><br>
                    </div>
                </form>

                <div class="buttons">
                    <div class="col-lg-6">
                        <input type="button" class="btn btn-info" value="Save">
                    </div>
                    <div class="col-lg-6">
                        <input type="button" class="btn btn-link" value="Cancel" data-dismiss="modal">
                    </div>
                </div>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
