/**
 * @author shahenpogosyan
 */

var host = "..";
var keyCookie = "ckey";

function UserService(helper, $http, host, keyCookie) {

    this.signUp = signUp;
    this.signIn = signIn;
    this.signOut = signOut;
    this.getUser = getUser;
    this.saveUser = saveUser;
    this.removeUser = removeUser;

    function signUp(user, done) {
        var that = this;
        console.log("user: ", user);
        $http.post(host + "/api/users/signup", user).then(function (response) {
            console.log("response", response.data);
            if (response.data.status === 'success') {
                that.saveUser(response.data.response);
                done(null, response.data.response);
            } else {
                done(response.data.errors);
            }
        });
    }

    function signIn(user, done) {
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
            console.log(response.data);
            if (response.data.status === 'success') {
                saveUser(response.data.response);

                done(null, response.data.response);
            } else {
                done(response.data.errors);
            }
        });
    }

    function signOut() {
        removeUser();
    }


    function getUser() {
        if (!helper.getCookie(keyCookie)) {
            return null;
        }
        return localStorage.getItem("user");
    }

    function saveUser(user) {
        var date = new Date();
        date.setDate(new Date().getDate() + 60);
        helper.setCookie(keyCookie, user.token, date);

        localStorage.setItem("user", user);
    }

    function removeUser() {
        localStorage.removeItem("user");
        helper.deleteCookie(keyCookie);
    }

    return this;
}