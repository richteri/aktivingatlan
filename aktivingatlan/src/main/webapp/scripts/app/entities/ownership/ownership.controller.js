'use strict';

angular.module('aktivingatlanApp')
    .controller('OwnershipController', function ($scope, Ownership, OwnershipSearch, ParseLinks) {
        $scope.ownerships = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Ownership.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.ownerships = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Ownership.get({id: id}, function(result) {
                $scope.ownership = result;
                $('#deleteOwnershipConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Ownership.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOwnershipConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            OwnershipSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.ownerships = result;
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
            $scope.ownership = {note: null, id: null};
        };
    });
