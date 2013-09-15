


function LoginController($scope, $http) {

    var newUser = {};
    newUser.firstName

    $http.post('api/user', postData, null
    ).success(function (data, status, headers, config) {
        // Do something successful
    }).error(function (data, status, headers, config) {
        // Handle the error
    });

}