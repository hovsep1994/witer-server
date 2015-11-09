/**
 * Created by shahenpoghosyan on 11/9/15.
 */
$(document).ready(function () {
    $("#add_menu_form").on('submit', function(e) {
        e.preventDefault();
        var menuName = $('#menuName').val();
        $.post("/api/menus/add", {
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

    function getCookie(name) {
        var value = "; " + document.cookie;
        console.log("valodik: " + document.cookie);
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) return parts.pop().split(";").shift();
    }
});