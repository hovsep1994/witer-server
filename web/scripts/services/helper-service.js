/**
 * Created by shahenpoghosyan on 6/11/16.
 */


function HelperService() {

    this.setCookie = setCookie;
    this.getCookie = getCookie;
    this.deleteCookie = deleteCookie;
}


function setCookie(name, value, expireDate) {
    document.cookie = name + "=" + value + ";expires=" + expireDate.toUTCString() + ";path=/"
}

function getCookie(name) {
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length == 2) return parts.pop().split(";").shift();
}

function deleteCookie(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}