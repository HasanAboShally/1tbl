app.factory('Rows', function () {
    var data = { rows: [] };

    return {
        getRows: function () {
            return data.rows;
        }
        ,
        setRows: function (rows) {
            data.rows = rows;
        }
    }
});

function RowsController($scope, $http, CurrentProject, CurrentTable, Rows) {

    $scope.currentTableName;
    $scope.currentTableName;

    $scope.rows;
    $scope.columns;
    $scope.selectedRowId;
    $scope.isRowEdited = false;
    $scope.newRow = {};

    var rowTemplate = "<div ng-style=\"{'cursor': row.cursor, 'z-index': col.zIndex() }\" ng-repeat=\"col in renderedColumns\" ng-class=\"col.colIndex()\" class=\"ngCell {{col.cellClass}}\" ng-cell></div>";
    var cellEditableTemplate = "<input ng-class=\"'colt' + col.index\" ng-input=\"COL_FIELD\" ng-model=\"COL_FIELD\" ng-blur=\"cellEdited(col, row);\" />";

    $scope.gridOptions = {
        data: 'rows',
        columnDefs: 'columns',
        showFilter: true,
        selectedItems: [],
        multiSelect: false,
        enableCellEdit: true,
        rowTemplate: rowTemplate
    };


    // Watch for row update
    $scope.$watch(
        function () {

            var row = $scope.gridOptions.selectedItems[0];

            if (!row)
                return -1;

            return row._oneId;

        }
        ,
        function (newVal, oldVal) {

            if ($scope.isRowEdited) {
                var row = $.grep($scope.rows, function (row) { return row._oneId == oldVal; })[0];
                updateRow(row);
            }

            $scope.isRowEdited = false;
            $scope.selectedRowId = newVal;

        }
        ,
        true
    );

    // Watch for table change
    $scope.$watch(function () { return CurrentTable.getName(); }, function () {

        $scope.currentTableName = CurrentTable.getName();
        $scope.currentProjectName = CurrentProject.getName();

        $scope.columns = [];

        var systemColumn = '_oneId';
        $scope.columns.push({ field: systemColumn, displayName: '#', width: '*', cellClass: "systemColumnCell", headerClass: "systemColumnHeader", enableCellEdit: false });

        var columns = CurrentTable.getColumns();
        $(columns).each(function () {
            if (this.name != systemColumn) {
                $scope.columns.push({ field: this.name, displayName: this.name, width: '**', editableCellTemplate: cellEditableTemplate });
            }
        });

        $scope.rows = Rows.getRows();
    });

    $scope.deleteRow = function () {

        if ($scope.selectedRowId == -1) {
            $.pnotify({
                title: 'Delete Row',
                text: 'Please select a row first',
                type: 'error',
                shadow: false
            });
            return;
        }

        $http.delete(serverWebPath + 'projects/' + CurrentProject.getId() + '/tables/' + CurrentTable.getName() + '/rows/' + $scope.selectedRowId).success(function (data, status, headers, config) {
            $.pnotify({
                title: 'Delete Row',
                text: 'Row successfully deleted!',
                type: 'info',
                shadow: false
            });
        }).error(function () { alert("Error - RowsController : deleteRows"); });

        var row = $.grep($scope.rows, function (row) { return row._oneId == $scope.selectedRowId; })[0];
        $scope.rows.splice($.inArray(row, $scope.rows), 1);
    }

    $scope.cellEdited = function (col, row) {
        $scope.isRowEdited = true;
    }

    function updateRow(row) {

        $http.put(serverWebPath + 'projects/' + CurrentProject.getId() + '/tables/' + CurrentTable.getName() + '/rows/' + row._oneId, row).success(function (data, status, headers, config) {
            $.pnotify({
                title: 'Update Row',
                text: 'Row successfully Updated!',
                type: 'info',
                shadow: false
            });

        }).error(function () { alert("Error - RowsController : updateRow"); });
    }


    $scope.addRow = function () {

        $http.post(serverWebPath + 'projects/' + CurrentProject.getId() + '/tables/' + CurrentTable.getName() + '/rows', $scope.newRow)
            .success(function (_oneId, status, headers, config) {
                $.pnotify({
                    title: 'Add Row',
                    was: 'Row successfully Added!',
                    type: 'success',
                    shadow: false
                });

                $scope.newRow._oneId = _oneId; // assign the _oneId that came from the server.
                $scope.rows.push($scope.newRow);
                $scope.newRow = {};
            })
            .error(function (data, status, headers, config) {
                alert("Error - TablesController : addRow");
            });
    }



}
