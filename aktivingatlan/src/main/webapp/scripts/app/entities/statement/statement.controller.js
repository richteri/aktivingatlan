'use strict';

angular.module('aktivingatlanApp')
    .controller('StatementController', function ($scope, Statement, StatementSearch, ParseLinks) {
        $scope.statements = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Statement.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.statements = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Statement.get({id: id}, function(result) {
                $scope.statement = result;
                $('#deleteStatementConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Statement.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStatementConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            StatementSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.statements = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.statement = {date: null, note: null, id: null};
        };
    });
