
var DEFAULT_PROJECT_ICON = "http://cdn1.iconfinder.com/data/icons/ampola-final-by-ampeross/256/empty.png";

app.factory('CurrentProject', function () {
    var currentProject = {};

    return {
        get: function () {
            return currentProject;
        }
        ,
        getName: function () {
            return currentProject.name;
        }
        ,
        getId: function () {
            return currentProject.id;
        },
        getTables: function () {
            return currentProject.tables;
        }
        ,
        addTable: function (newTable) {
            return currentProject.tables.push(newTable);
        }
        ,
        setTables: function (projectTables) {
            currentProject.tables = projectTables;
        }
        ,
        set: function (project) {
            // currentProject = project;
            currentProject.id = project.id;
            currentProject.ownerId = project.ownerId;
            currentProject.name = project.name;
            currentProject.description = project.description;

            currentProject.token = project.token;

            if (project.iconUrl)
                currentProject.iconUrl = project.iconUrl;
            else
                currentProject.iconUrl = DEFAULT_PROJECT_ICON;
        },

        renameTable: function (name, newName) {
            var table = $.grep(currentProject.tables, function (table) { return table.name == name; })[0];
            table.name = newName;
        }
    }
});

function CurrentProjectController($scope, CurrentProject) {
    $scope.currentProject = CurrentProject.get();
}

function CurrentProjectInfoController($scope, $http, CurrentProject) {
    $scope.currentProject = CurrentProject.get();
    $scope.isEditMode = false;

    $scope.toggleEditMode = function () {
        $scope.isEditMode = true;
    }

    $scope.updateProject = function () {

        var projectBeanObj = {
            'id': $scope.currentProject.id,
            'name': $scope.currentProject.name,
            'description': $scope.currentProject.description,
            'iconUrl': $scope.currentProject.iconUrl
        };

        $http.put(serverWebPath + 'projects/' + $scope.currentProject.id, projectBeanObj).success(function (data, status, headers, config) {
            $.pnotify({
                title: 'Update Project',
                text: 'Project successfully Updated!',
                type: 'info',
                shadow: false
            });
        }).error(function () { alert("Error - CurrentProjectInfoController : updateProject"); });

        $scope.isEditMode = false;
    }

    $scope.deleteProject = function () {
        $http.delete(serverWebPath + 'projects/' + $scope.currentProject.id).success(function (data, status, headers, config) {

            $.pnotify({
                title: 'Delete Project',
                text: 'Project successfully deleted!',
                type: 'info',
                shadow: false
            });

            location.reload();
           // var project = $.grep($scope.tables, function (project) { return project.name == $scope.currentTableToDelete; })[0];
        //    $scope.tables.splice($.inArray(project, $scope.tables), 1);

        }).error(function () { alert("Error - CurrentProjectInfoController : deleteProject"); });

        //$scope.isEditMode = false;
    }
}

function ProjectsController($scope, $http, CurrentProject) {

    $scope.projects = {};
    $scope.newProject = {};

    $http.get(serverWebPath + 'projects').success(function (data, status, headers, config) {
        $scope.projects = data;

        angular.forEach($scope.projects, function (project) {
            // Default Project Icon
            if (!project.iconUrl || project.iconUrl == "")
                project.iconUrl = DEFAULT_PROJECT_ICON;
        });

        if ($scope.projects[0])
            $scope.selectProject($scope.projects[0].id);
        else {
            // if the user has no projects (not yet :)

            $(document).ready(function () {
                $('#new-project-modal').modal('show');
                $('#new-project-modal').on('shown', function () {
                    $("#new-project-modal .modal-body input:first-child").focus();
                })
            });
        }

    }).error(function () { alert("Error - ProjectsController : Loading Projects"); });

    function loadTables(projectId) {
        $http.get(serverWebPath + 'projects/' + projectId + '/tables').success(function (data, status, headers, config) {
            CurrentProject.setTables(data);
        }).error(function () { alert("Error - TablesController : Get Tables"); });
    }

    $scope.selectProject = function (projectId) {

        if (projectId != CurrentProject.get().id) {
            $http.get(serverWebPath + 'projects/' + projectId).success(function (data, status, headers, config) {
                CurrentProject.set(data);
                loadTables(projectId);
            }).error(function () { alert("Error - ProjectsController : selectProject"); });
        }

        $('#projects-list').slideUp(250);
        $('#project-dismisser').fadeOut(250);
    };

    $scope.addProject = function () {

        $http.post(serverWebPath + 'projects', $scope.newProject
        ).success(function (project, status, headers, config) {

            $scope.projects.push(project);
            $scope.selectProject(project.id);

            $.pnotify({
                title: 'Add Project',
                text: $scope.newProject.name + ' - project was successfully Added!',
                type: 'success',
                shadow: false
            });

            $scope.newProject.name = "";
            $scope.newProject.description = "";
            $scope.newProject.iconUrl = "";

        }).error(function (data, status, headers, config) {
            alert("Error - ProjectsController : addProject");
        });
    }
}
