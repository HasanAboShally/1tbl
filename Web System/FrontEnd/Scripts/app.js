
var TEST_SERVER_PATH =  'http://79.180.14.151:8080/OneServer/web/';
var PUBLISH_SERVER_PATH = 'http://www.1tbl.org:8080/web/';

var serverWebPath = PUBLISH_SERVER_PATH;

alert = function () { }; // disable all alert messages.

//if (serverWebPath == TEST_SERVER_PATH) {
   // alert = function () { }; // disable all alert messages.
//}

var app = angular.module('oneTable', ['ngGrid']);

app.directive('ngBlur', function () {
    return function (scope, elem, attrs) {
        elem.bind('blur', function () {
            scope.$apply(attrs.ngBlur);
        });
    };
});

app.config(function ($httpProvider) {
    $httpProvider.responseInterceptors.push('myHttpInterceptor');
    var spinnerFunction = function (data, headersGetter) {
        // todo start the spinner here
        $('#loading-spinner').show();
        return data;
    };
    $httpProvider.defaults.transformRequest.push(spinnerFunction);
})
// register the interceptor as a service, intercepts ALL angular ajax http calls
.factory('myHttpInterceptor', function ($q, $window) {
    return function (promise) {
        return promise.then(function (response) {
            // do something on success
            // todo hide the spinner


            $('#loading-spinner').hide();

            return response;
        }, function (response) {
            // do something on error
            // todo hide the spinner
            $('#loading-spinner').hide();
            return $q.reject(response);
        });
    };
})