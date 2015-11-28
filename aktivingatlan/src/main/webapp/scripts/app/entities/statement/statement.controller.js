'use strict';

angular.module('aktivingatlanApp')
    .controller('StatementController', function ($scope, $state, $modal, Statement, ParseLinks) {
      
        $scope.statements = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Statement.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.statements = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.statement = {
                date: null,
                note: null,
                id: null
            };
        };
    });
