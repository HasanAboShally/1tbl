


function RecordsController($scope, $http) {

    $scope.filterText = "";

    $scope.mySelections = [];
    $scope.myData = [
        { _id: 1, name: "Waseem", age: 50 },
        { _id: 2, name: "Rami", age: 43 },
        { _id: 3, name: "Saed", age: 27 },
        { _id: 4, name: "Mostafa", age: 29 },
        { _id: 5, name: "Zonkol", age: 34 }
    ];

    $scope.gridOptions = {

    };

    $scope.gridOptions = {
        data: 'myData',
        enableCellEdit: true,
        selectEditms: $scope.mySelections,
        showFilter: true,
        showColumnMenu: true,
        enablePaging: true,
        showFooter: true,
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions
    };

    $scope.filterOptions = {
        filterText: $scope.filterText,
        useExternalFilter: false
    };


    $scope.pagingOptions = {
        pageSizes: [250, 500, 1000],
        pageSize: 250,
        totalServerItems: 0,
        currentPage: 1
    };
    $scope.setPagingData = function (data, page, pageSize) {
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
        $scope.myData = pagedData;
        $scope.pagingOptions.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var data;
            if (searchText) {
                var ft = searchText.toLowerCase();
                $http.get('http://angular-ui.github.io/ng-grid/jsonFiles/largeLoad.json').success(function (largeLoad) {
                    data = largeLoad.filter(function (item) {
                        return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                    });
                    $scope.setPagingData(data, page, pageSize);
                });
            } else {
                $http.get('http://angular-ui.github.io/ng-grid/jsonFiles/largeLoad.json').success(function (largeLoad) {
                    $scope.setPagingData(largeLoad, page, pageSize);
                });
            }
        }, 100);
    };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);
    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);


}


