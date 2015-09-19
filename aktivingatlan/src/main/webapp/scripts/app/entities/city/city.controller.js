'use strict';

angular.module('aktivingatlanApp')
    .controller('CityController', function ($scope, City, CitySearch) {
        $scope.citys = [];
        $scope.loadAll = function() {
            City.query(function(result) {
               $scope.citys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            City.get({id: id}, function(result) {
                $scope.city = result;
                $('#deleteCityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            City.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            CitySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.citys = result;
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
            $scope.city = {zip: null, name: null, id: null};
        };
    });
