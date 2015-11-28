'use strict';

angular.module('aktivingatlanApp')
    .controller('ContractController', function ($scope, $state, $modal, Contract, ParseLinks) {
      
        $scope.contracts = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Contract.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.contracts = result;
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
            $scope.contract = {
                idNo: null,
                exclusive: null,
                startDate: null,
                endDate: null,
                note: null,
                id: null
            };
        };
    });
