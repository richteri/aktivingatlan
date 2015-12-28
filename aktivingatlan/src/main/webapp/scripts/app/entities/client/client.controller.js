'use strict';

angular.module('aktivingatlanApp')
    .controller('ClientController', function ($scope, $state, Client, ParseLinks) {

        $scope.clients = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Client.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.clients = result;
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
            $scope.client = {
                name: null,
                email: null,
                phone1: null,
                phone2: null,
                address1: null,
                address2: null,
                idNo: null,
                note: null,
                id: null
            };
        };
    });
