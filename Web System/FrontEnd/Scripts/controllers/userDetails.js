
function userDetailsController($scope, $http, $location) {
    $scope.user = { name: "username", lastLogin:"last-login" };

    $http.get(serverWebPath + 'user').success(function (data, status, headers, config) {
        $scope.user.name = data.username;
        $scope.user.lastLogin = data.lastLogin;
    }).error(function () {
        alert("Error - userDetailsController : Get User Details");
    });

    $scope.signout = function () {
        $http.get(serverWebPath + 'users/logout').success(function (data, status, headers, config) {
            window.location = '/index.html';
        }).error(function () { alert("Error - userDetailsController : signout"); });
    }
}

