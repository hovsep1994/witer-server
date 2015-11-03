/**
 * Created by shahenpoghosyan on 10/29/15.
 */

$(document).ready(function () {
    $("#reg_compaby_form").on('submit', function(e) {
        e.preventDefault();
        var name = $('#companyName').val();
        var email = $('#companyEmail').val();
        var password = $('#companyPassword').val();
        console.log(password);
        $.post("/api/companies/register", {
            email: email,
            name: name,
            password: password
        }, function(data) {
            console.log(data)
            var response = JSON.parse(data);
            if(response.success) {
                alert("success");
            } else {
                alert("error: " + response.error.message)
            }
        })
    });
});