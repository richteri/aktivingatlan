'use strict';

angular.module('aktivingatlanApp')
    .controller('CityController', function ($scope, City, CitySearch, ParseLinks, Pageable) {
        $scope.citys = [];
        $scope.pageable = new Pageable($scope, 'pageable');

        $scope.load = function() {
            if ($scope.pageable.filtered) {
                // Search
                CitySearch.query($scope.pageable, function(result, headers) {
                    $scope.citys = result;
                    $scope.links = ParseLinks.parse(headers('link'));
                    if ($scope.links['last'] < $scope.pageable.page) {
                        $scope.loadPage($scope.links['last']);
                    }
                });
            } else {
                // Load
                City.query($scope.pageable, function(result, headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                    $scope.citys = result;
                });
            }
        }

        // Load one page
        $scope.loadPage = function(page) {
            $scope.pageable.page = page;
            $scope.load();
        };

        // Search
        $scope.search = function() {
            $scope.pageable.filtered = ($scope.pageable.query != "");
            $scope.load();
        }

        // Clear search
        $scope.clearSearch = function () {
        	$scope.pageable.query = "";
            $scope.pageable.filtered = false;
        	$scope.loadPage(1);
        };

        // Modify sort criteria
        $scope.sortBy = function (property) {
            $scope.pageable.sortBy(property);
            $scope.load();
        }


        // Show delete confirmation window
        $scope.delete = function (id) {
            City.get({id: id}, function(result) {
                $scope.city = result;
                $('#deleteCityConfirmation').modal('show');
            });
        };

        // Confirm delete
        $scope.confirmDelete = function (id) {
            City.delete({id: id},
                function () {
                    $scope.load();
                    $('#deleteCityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.city = {zip: null, name: null, id: null};
        };

        $scope.load();

    });
