var app = angular.module('dblabs', ['ngMaterial', 'ui.router', 'md.data.table']);

app.config(function($stateProvider, $urlRouterProvider) {

       $urlRouterProvider.otherwise("/databaseSchema");

      $stateProvider
        .state('databaseSchema', {
          url: "/database-schema",
          templateUrl: "/views/databaseSchema.html",
          controller: 'databaseSchemaController'
        })
        .state('teamTable', {
          url: "/team-table",
          templateUrl: "views/teamTable.html",
          controller: 'teamTableController'
         })
         .state('competitionTable', {
                   url: "/competition-table",
                   templateUrl: "views/competitionTable.html",
                   controller: 'competitionTableController'
         })
          .state('competitionResultTable', {
              url: "/competition-result-table",
              templateUrl: "views/competitionResultTable.html",
              controller: 'competitionResultController'
          })
        ;
    });

var controllers = {};

controllers.competitionTableController = function ($scope, $http) {

    $scope.selected = [];
    $scope.competitions = [];

    var res = $http.get('/getAllCompetitions');
        res.success(function (data) {
        $scope.competitions = data;
    });
    res.error(function (data, status) {
        swal("Error: " + status, JSON.stringify(data), "error");
    });
    $scope.logItem = function (item) {
        swal("item.name, 'was selected'", "success");
    };
};

controllers.competitionResultController = function ($scope, $http) {

    $scope.selected = [];
    $scope.competitionResults = [];

    $scope.orderQuery = {
        order: 'teamPosition'
    };

    var res = $http.get('/getAllCompetitionResults');
    res.success(function (data) {
        $scope.competitionResults = data;
    });
    res.error(function (data, status) {
        swal("Error: " + status, JSON.stringify(data), "error");
    });

};

controllers.databaseSchemaController = function ($scope, $http) {

    $scope.databaseTables = [];
    var res = $http.get('/getDatabaseSchema');
    res.success(function (data) {
        $scope.databaseTables = data;
    });
    res.error(function (data, status) {
        swal("Error: " + status, JSON.stringify(data), "error");
    });

};

controllers.teamTableController = function ($scope, $http) {

    $scope.selected = [];
    $scope.teams = [];

    var res = $http.get('/getAllTeams');
    res.success(function (data) {
        $scope.teams = data;
    });
    res.error(function (data, status) {
        swal("Error: " + status, JSON.stringify(data), "error");
    });

    $scope.logItem = function (item) {
        console.log(item.name, 'was selected');
      };

};

controllers.AppCtrl = function($scope, $mdSidenav) {

    $scope.toggleSidenav = toggleSidenav;
    function toggleSidenav(name) {
        $mdSidenav(name).toggle();
    }

};

app.controller(controllers);
