<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 8/21/16
  Time: 12:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="editMenuModal" class="modal fade editMenuModal" role="dialog">
    <div class="modal-dialog" style="width: 400px">
        <div class="modal-content">
            <div class="modal-header">Menu</div>
            <div class="modal-body">
                <form>
                    <div class="inputs">
                        <input class="form-control" placeholder="*Name" value="{{editMenu.name}}"
                               ng-model="editMenu.name"><br>
                        <select class="form-control"
                                ng-options="option.name for option in availableCurrencies track by option.code"
                                ng-model="editMenu.currency"></select>
                        </select>
                        <br>
                        <select class="form-control"
                                ng-options="option.name for option in availableLanguages track by option.code"
                                ng-model="editMenu.mainLanguage"></select>
                        </select>
                        <br>

                        <div class="dropdown" style="width: 100%; display: inline-block;">
                            <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" type="button"
                                    style="width: 100%">
                                Choose which venues to attach menu &nbsp;&nbsp;&nbsp;&nbsp;<span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu attach-venues">
                                <li ng-repeat="venue in editMenu.venues">
                                    <a ng-click="selectVenue(venue)"><span>{{venue.name}}</span>

                                        <div class="image-div"></div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <div class="buttons">
                    <input type="button" class="btn btn-link" value="Cancel" data-dismiss="modal" style="width: 100px">
                    <input type="button" class="btn btn-info" value="Save" ng-click="update(editMenu)"
                           style="width: 100px">
                </div>
            </div>
        </div>
    </div>
</div>
