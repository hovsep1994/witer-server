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
    Hi !! Let's translate your menu.
  </div>
  <hr>
  <div class="row" style="min-height: 100px">
    <div class="col-lg-3">
      <h4>Menu Name</h4><br>
      <span>My Menu 1</span>
    </div>
    <div class="col-lg-3">
      <label for="translation-language"><h4>Original Language</h4></label><br>
      <select class="form-control" id="translation-language">
        <option>Armenian</option>
      </select>
    </div>
    <div class="col-lg-3">
      <label for="menu-language"><h4>Translation Language</h4></label><br>
      <select class="form-control" id="menu-language">
        <option>Russian</option>
      </select>
    </div>
    <div class="col-lg-3">
      <label for="auto-translate"><h4>Auto Translate</h4></label><br>
      <input type="button" class="btn btn-info" id="auto-translate" value="Translate" style="width: 100%;"/>
    </div>
  </div>
  <div class="row translation-decs">
    <div class="col-lg-3">
      Menu Categories
    </div>
    <div class="col-lg-3 float-right" style="font-weight: 400">
      <img style="margin-top: -3px" src="${pageContext.request.contextPath}/styles/resources/business/admin/us_flag.png" width="24px">
      <span>English</span>
    </div>
    <div class="col-lg-3">
      Translation Categories
    </div>
    <div class="col-lg-3 float-right" style="font-weight: 400">
      <img style="margin-top: -3px" src="${pageContext.request.contextPath}/styles/resources/business/admin/us_flag.png" width="24px">
      <span>Russian</span>
    </div>
  </div>
  <div class="row panel-group" id="categories">


    <%-- ------- start category section --------  --%>
    <div class="col-lg-12">
      <div class="category-header-translation">
        <div class="col-lg-6">
          <img src="${pageContext.request.contextPath}/styles/resources/business/admin/image-icon.png" width="32px">
          &nbsp;&nbsp;&nbsp;Breakfast
        </div>
        <div class="col-lg-6">
          <img src="${pageContext.request.contextPath}/styles/resources/business/admin/image-icon.png" width="32px">
          <input type="text" class="form-control" style="width: 200px; display: inline; margin-left: 10px">
          <div class="category-caret"><a data-toggle="collapse" data-parent="#categories" href="#category1">
              <img src="${pageContext.request.contextPath}/styles/resources/business/admin/caret.png" width="46px" />
          </a></div>
        </div>
      </div>
    </div>

    <div class="panel-collapse collapse in" id="category1">
      <div>
          <div class="col-lg-6 product">
            <div class="product-info">
              <div class="col-lg-3">
                <img src="${pageContext.request.contextPath}/styles/resources/business/admin/image-icon.png">
              </div>
              <div class="col-lg-6" style="border-right: 1px #ebebeb solid;">
                <div class="product-name">Product 1</div>
                <div class="product-desc">Product Desc 1</div>
                <div class="product-tags">#tag1, #tag2</div>
              </div>
              <div class="col-lg-3" style="text-align: center; font-size: 16px">
                2500 AMD
              </div>
            </div>
          </div>
          <div class="col-lg-6 product">
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
        </div>
      </div>



    <%-- ------- start category section --------  --%>

      <div class="col-lg-12">
        <div class="category-header-translation">
          <div class="col-lg-6">
            <img src="${pageContext.request.contextPath}/styles/resources/business/admin/image-icon.png" width="32px">
            &nbsp;&nbsp;&nbsp;Breakfast
          </div>
          <div class="col-lg-6">
            <img src="${pageContext.request.contextPath}/styles/resources/business/admin/image-icon.png" width="32px">
            <input type="text" class="form-control" style="width: 200px; display: inline; margin-left: 10px">
            <div class="category-caret"><a data-toggle="collapse" data-parent="#categories" href="#category2">
              <img src="${pageContext.request.contextPath}/styles/resources/business/admin/caret.png" width="46px" />
            </a></div>
          </div>
        </div>
      </div>

      <div class="panel-collapse collapse in" id="category2">
        <div>
          <div class="col-lg-6 product">
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
        </div>
      </div>
      <%-- ------- start category section --------  --%>



    </div>
  </div>
</div>



<div class="desc navbar-fixed-bottom">
  <div class="menu-attach">
    <div class="container">
      <div style="width: 400px; display: inline-block;">
        <div id="pb" class="progress" style="height: 25px;">
          <div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="40"
               aria-valuemin="0" aria-valuemax="100" style="width:10%; padding-top: 3px">
            10%
          </div>
        </div>
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

    $('#pb').css({
      'background-image': 'none',
      'background-color': '#cccccc'
    });


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
