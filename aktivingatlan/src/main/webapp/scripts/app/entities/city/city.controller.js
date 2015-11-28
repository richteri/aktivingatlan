'use strict';

angular.module('aktivingatlanApp')
    .controller('CityController', function ($scope, $state, $modal, City, ParseLinks) {
      
        $scope.citys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            City.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.citys = result;
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
            $scope.city = {
                zip: null,
                name: null,
                id: null
            };
        };
    });
