
function ColumnsController($scope, CurrentTable) {
    $scope.columns = CurrentTable.getColumns();

    $scope.$watch(function () { return CurrentTable.getColumns(); }, function () {
        $scope.columns = CurrentTable.getColumns();
    });
}
