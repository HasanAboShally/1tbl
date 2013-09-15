TEST_SERVER_PATH = "http://79.180.14.151:8080/OneServer/web/"
PUBLISH_SERVER_PATH = "http://www.1tbl.org:8080/web/"
serverWebPath = PUBLISH_SERVER_PATH
alert = ->

app = angular.module("oneTable", [ "ngGrid" ])
app.directive "ngBlur", ->
  (scope, elem, attrs) ->
    elem.bind "blur", ->
      scope.$apply attrs.ngBlur

app.config(($httpProvider) ->
  $httpProvider.responseInterceptors.push "myHttpInterceptor"
  spinnerFunction = (data, headersGetter) ->
    $("#loading-spinner").show()
    data

  $httpProvider.defaults.transformRequest.push spinnerFunction
).factory "myHttpInterceptor", ($q, $window) ->
  (promise) ->
    promise.then ((response) ->
      $("#loading-spinner").hide()
      response
    ), (response) ->
      $("#loading-spinner").hide()
      $q.reject response
