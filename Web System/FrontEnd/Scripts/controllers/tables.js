

app.factory('CurrentTable', function () {
    var currentTable = {};

    return {
        get: function () {
            return currentTable;
        }
        ,
        getColumns: function () {
            return currentTable.columns;
        }
        ,
        setColumns: function (columns) {
            currentTable.columns = columns;
        }
        ,
        getName: function () {
            return currentTable.name;
        }
        ,
        setName: function (name) {
            currentTable.name = name;
        },
        renameColumn: function (name, newName) {
            var column = $.grep(currentTable.columns, function (column) { return column.name == name; })[0];
            column.name = newName;
        },
        addColumn: function (col) {
            currentTable.columns.push(col);
        }
    }
});


function TablesController($scope, $http, CurrentProject, CurrentTable, Rows) {

    $scope.tables;
    $scope.currentTable = CurrentTable.get();
    $scope.newTable = { columns: [] };
    $scope.currentTableToDelete = '';
    $scope.newColumn;

    $scope.$watch(function () { return CurrentProject.getTables(); }, function () {
        $scope.tables = CurrentProject.getTables();
    });

    $scope.selectTable = function (tableName) {

        var loadRows = $http.get(serverWebPath + 'projects/' + CurrentProject.getId() + '/tables/' + tableName + '/rows?rowsPerPage=-1').success(function (data, status, headers, config) {
            Rows.setRows(data);
        }).error(function () { alert("Error - RowsController : Count"); });

        loadRows.then(function (data) {
            var table = $.grep($scope.tables, function (table) { return table.name == tableName; })[0];
            CurrentTable.setName(table.name);
            CurrentTable.setColumns(table.columns);
        });
    };

    $scope.selectTableForUpdate = function (tableName) {
        var table = $.grep($scope.tables, function (table) { return table.name == tableName; })[0];
        CurrentTable.setName(table.name);
        CurrentTable.setColumns(table.columns);
    };

    $scope.updateColumn = function (name, newName) {

        $http.put(serverWebPath + 'projects/' + CurrentProject.getId() + '/tables/' + CurrentTable.getName() + '/columns/' + name, newName).success(function (data, status, headers, config) {
            $.pnotify({
                title: 'Update Column',
                text: 'Column successfully Updated!',
                type: 'info',
                shadow: false
            });

            CurrentTable.renameColumn(name, newName);

        }).error(function () { alert("Error - TablesController : updateColumn"); });
    }

    $scope.deleteColumn = function (name) {

        $http.delete(serverWebPath + 'projects/' + CurrentProject.getId() + '/tables/' + CurrentTable.getName() + '/columns/' + name).success(function (data, status, headers, config) {
            $.pnotify({
                title: 'Delete Column',
                text: 'Column successfully deleted!',
                type: 'info',
                shadow: false
            });
        }).error(function () { alert("Error - TablesController : deleteColumn"); });
    }

    $scope.addTable = function () {

        var newTable = {};
        newTable.name = $scope.newTable.name;
        newTable.columns = [{ 'name': '_oneId' }];

        var newTableObj = [$scope.newTable.name];
        angular.forEach($scope.newTable.columns, function (column) {
            newTableObj.push(column.name);
            newTable.columns.push({ 'name': column.name });
        });

        $http.post(serverWebPath + 'projects/' + CurrentProject.getId() + '/tables', newTableObj)
            .success(function (data, status, headers, config) {
                $.pnotify({
                    title: 'Add Table',
                    was: 'Table Ttext successfully Added!',
                    type: 'success',
                    shadow: false
                });

                CurrentProject.addTable(newTable);
                $scope.newTable.name = '';
                $scope.newTable.columns = [];
            })
            .error(function (data, status, headers, config) {
                alert("Error - TablesController : addTable");
            });
    }

    $scope.addColumn = function () {
        $scope.newTable.columns.push({ name: $scope.newColumn.name });
        $scope.newColumn.name = '';
    }

    $scope.addColumnToCurrentTable = function () {

        var newColumn = { 'columnName': $scope.newColumn.name };

        $http.post(serverWebPath + 'projects/' + CurrentProject.getId() + '/tables/' + CurrentTable.getName() + '/columns', newColumn).success(function (data, status, headers, config) {
            $.pnotify({
                title: 'Add Column',
                text: 'Column successfully Added!',
                type: 'info',
                shadow: false
            });

            CurrentTable.setName(newName);
            CurrentProject.renameTable(name, newName);
        }).error(function () { alert("Error - TablesController : renameCurrentTable"); });

        CurrentTable.addColumn({ name: $scope.newColumn.name });
        $scope.newColumn.name = '';
    }

    $scope.setCurrentTableToDelete = function (tableName) {
        $scope.currentTableToDelete = tableName;
    }

    $scope.renameCurrentTable = function () {

        var name = $scope.currentTable.name;
        var newName = $scope.currentTable.newName;

        if (newName && newName != '' && newName != name) {
            $http.put(serverWebPath + 'projects/' + CurrentProject.getId() + '/tables/' + name, newName).success(function (data, status, headers, config) {
                $.pnotify({
                    title: 'Update Table',
                    text: 'Table successfully Updated!',
                    type: 'info',
                    shadow: false
                });

                CurrentTable.setName(newName);
                CurrentProject.renameTable(name, newName);
            }).error(function () { alert("Error - TablesController : renameCurrentTable"); });
        }
    }

    $scope.deleteTable = function () {
        $http.delete(serverWebPath + 'projects/' + CurrentProject.get().id + '/tables/' + $scope.currentTableToDelete).success(function (data, status, headers, config) {

            $.pnotify({
                title: 'Delete Table',
                text: 'Table successfully deleted!',
                type: 'info',
                shadow: false
            });

           
            var table = $.grep($scope.tables, function (table) { return table.name == $scope.currentTableToDelete; })[0];
            $scope.tables.splice($.inArray(table, $scope.tables), 1);

            $scope.currentTableToDelete = '';

        }).error(function () { alert("Error - TablesController : deleteTable"); });
    }

}