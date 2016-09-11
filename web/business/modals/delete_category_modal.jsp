<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 8/20/16
  Time: 5:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="deleteCategoryModal" class="modal fade deleteModal" role="dialog">
    <div class="modal-dialog" style="width: 400px">
        <div class="modal-content">
            <div class="modal-body">
                Are you sure you want to delete category ? <br><br>
                <div class="buttons">
                    <input type="button" class="btn btn-link" value="Cancel" data-dismiss="modal">
                    <input type="button" class="btn btn-info" value="Delete" ng-click="remove()">
                </div>
            </div>
        </div>
    </div>
</div>
