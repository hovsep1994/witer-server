/**
 * Created by shahenpoghosyan on 11/9/15.
 */
$(document).ready(function () {
    $("#add_menu_form").on('submit', function(e) {
        e.preventDefault();
        var menuName = $('#menuName').val();
        $.post("../api/menus/add", {
            name: menuName,
            key: getCookie("ckey")
        }, function(data) {
            console.log(data);
            var response = JSON.parse(data);
            if(response.success) {
                window.location.href = "/menus/add";
            } else {
                alert("error: " + response.error.message)
            }
        });
    });

    $("#add_group_form").on('submit', function(e) {
        e.preventDefault();
        var xhr = new XMLHttpRequest();
        (xhr.upload || xhr).addEventListener('progress', function(e) {
            var done = e.position || e.loaded
            var total = e.totalSize || e.total;
            console.log('xhr progress: ' + Math.round(done/total*100) + '%');
        });
        xhr.addEventListener('load', function(e) {
            console.log('xhr upload complete', e, this.responseText);
        });

        var photo = $('#groupImage')[0].files[0];
        var groupName = $('#groupName').val();
        var menuId = $('#groupMenu').find('option:selected').val();

        var fd = new FormData;
        fd.append('image', photo);
        fd.append('name', groupName);
        fd.append('menu_id', menuId);

        xhr.open('post', '../api/category/add', true);
        xhr.send(fd);
    });

    function getCookie(name) {
        var value = "; " + document.cookie;
        console.log("valodik: " + document.cookie);
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) return parts.pop().split(";").shift();
    }
});