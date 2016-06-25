/**
 * Created by shahenpoghosyan on 6/11/16.
 */

var host = "..";
var keyCookie = "ckey";

function UserService(helper, $http) {

    this.helper = helper;

    this.signUp = signUp;
    this.signIn = signIn;
    this.signOut = signOut;
    this.getUser = getUser;
    this.saveUser = saveUser;
    this.removeUser = removeUser;
}


function signUp(user, done) {
    $http.post(host + "/api/users/signup", user).then(function (response) {
        alert(JSON.stringify(response));
        if (response.data.status === 'success') {
            this.saveUser(response.data.response);
            done(null, response.data.response);
        } else {
            done(response.data.errors);
        }
    });
};

function signIn(user) {
    $http({
        method: 'POST',
        url: host + "/api/users/signin",
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        transformRequest: function (obj) {
            var str = [];
            for (var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
            return str.join("&");
        },
        data: user
    }).then(function (response) {
        if (response.data.status === 'success') {
            this.saveUser(response.data.response);

            done(null, response.data.response);
        } else {
            done(response.data.errors);
        }
    });
};

function signOut() {
    this.removeUser();
};


function getUser() {
    if (!this.helper.getCookie(keyCookie)) {
        return null;
    }
    return localStorage.getItem("user");
}

function saveUser(user) {
    var date = new Date();
    date.setDate(new Date().getDate() + 60);
    this.helper.setCookie(keyCookie, user.token, date);

    localStorage.setItem("user", user);
}

function removeUser() {
    localStorage.removeItem("user");
    this.helper.deleteCookie(keyCookie);
}