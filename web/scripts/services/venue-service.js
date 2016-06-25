/**
 * Created by shahenpoghosyan on 6/11/16.
 */

var host = "..";

function VenueService(userService, $http) {
    this.$http = $http;
    this.userService = userService;

    this.add = add;
    this.findUserVenues = findUserVenues;
    this.remove = remove;
    this.update = update;
}

function add(venue, done) {
    $http.post(host + "/api/venue?token=" + userService.getUser().token, venue).then(function (response) {
        if(!response.data.success) return done(response.data.errors);

        done(null, response.data.response);
    });
}

function remove(id, done) {
    var url = host + "/api/venue/" + id + "?token=" + userService.getUser().token;
    $http.delete(url).then(function (response) {
        if(!response.data.success) return done(response.data.errors);

        done(null, response.data.response);
    });

}

function findUserVenues() {
    $http.get(host + "/api/venue?token=", {
        token: userService.getUser().token,
        companyId : userService.getUser().company.id
    }).then(function (response) {
        if(!response.data.success) return done(response.data.errors);

        done(null, response.data.response);
    });
}

function update(venue) {

}

