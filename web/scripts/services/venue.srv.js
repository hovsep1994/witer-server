/**
 * @author shahenpoghosyan
 */


function VenueService($http, host) {

    this.create = create;
    this.findUserVenues = findUserVenues;
    this.remove = remove;
    this.update = update;

    var venueUrl = host + '/api/venue/';

    function create(venue, done) {
        venue = convertToVenueModel(venue);
        console.log(venue);
        if (!done) {
            done = function () {};
        }
        $http.post(venueUrl, venue).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            var createdVenue = response.data.response;

            if(venue.image) {
                var fd = new FormData();
                fd.append('file', venue.image);
                $http.post(venueUrl + createdVenue.id + "/image", fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(function (response) {
                    alert("a" + JSON.stringify(response.data));
                    if (response.data.status !== 'success') return done(response.data.errors);

                    done(null, createdVenue);
                });
            } else {
                done(null, createdVenue);
            }

        });
    }

    function remove(id, done) {
        if (!done) {
            done = function () {};
        }
        var url = venueUrl + id;
        $http.delete(url).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });

    }

    function findUserVenues(done) {
        if (!done) {
            done = function () {};
        }
        $http.get(venueUrl).then(function (response) {
            console.log(response.data);
            if (response.data.status !== 'success') return done(response.data.errors);


            done(null, response.data.response);
        });
    }

    function update(id, venue, done) {
        if (!done) {
            done = function () {};
        }
        venue = convertToVenueModel(venue);
        $http.put(venueUrl + id, venue).then(function (response) {
            if (response.data.status !== 'success') return done(response.data.errors);

            done(null, response.data.response);
        });
    }

    function convertToVenueModel(venue) {
        return {
            name: venue.name,
            location: {
                country: venue.location.country,
                countryCode: venue.location.countryCode,
                city: venue.location.city,
                street: venue.location.street,
                zip: venue.location.zip
            },
            image: venue.image
        }
    }

}
