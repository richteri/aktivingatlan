'use strict';

angular.module('aktivingatlanApp')
    .controller('CityController', function ($scope, City, CitySearch, ParseLinks) {
        $scope.citys = [];
        $scope.page = 1;
        $scope.filtered = false;
        $scope.sortProperty = "name";
        $scope.sortDirection = "ASC";
        
        $scope.loadAll = function() {
            City.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.citys = result;
            });
            $scope.filtered = false;
        };
        
        $scope.loadAll();
        
        $scope.loadPage = function(page) {
            $scope.page = page;
            if ($scope.filtered) {
            	$scope.search();
            } else {
            	$scope.loadAll();
            }
        };

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
        	if ($scope.searchQuery) {
	            CitySearch.query({
	            	query: $scope.searchQuery, 
	            	page: $scope.page, 
	            	direction: $scope.sortDirection,
	            	property: $scope.sortProperty
	            }, function(result, headers) {
	                $scope.citys = result;
	                $scope.links = ParseLinks.parse(headers('link'));
	                $scope.filtered = true;
	            }, function(response) {
	                if(response.status === 404) {
	                    $scope.loadAll();
	                }
	            });
        	} else {
        		$scope.loadAll();
        	}
        };
        
        $scope.clearSearch = function () {
        	$scope.searchQuery = "";
        	$scope.filtered = false;
        	$scope.loadPage(1);
        };

        $scope.refresh = function () {
        	if ($scope.filtered) {
        		$scope.search();
        	} else {
        		$scope.loadAll();
        	}
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.city = {zip: null, name: null, id: null};
        };
        
        $scope.sortBy = function (property) {
        	if ($scope.sortProperty != property) {
        		$scope.sortProperty = property;
        		$scope.sortDirection = "ASC";
        	} else if ($scope.sortDirection == "ASC") {
        		$scope.sortDirection = "DESC";
        	} else {
        		
        	}
        }
    });
