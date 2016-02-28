'use strict';

angular.module('aktivingatlanApp')
    .controller('ListingController', ['$scope', 'PropertyPublic', 'Category', 
                                      function ($scope, PropertyPublic, Category) {
       
    	$scope.categorys = Category.query();
        $scope.propertys = [];
        
        PropertyPublic.findFeatured().then(function (response) {
            return $scope.propertys = response.data;
        });
        
        $scope.searchParams = {
        		cityId: null,
        		categoryId: null,
        		price: null,
        		code: '',
        		view: false
        };
        
        $scope.search = function () {
        	if ($scope.searchParams.cityId 
        			|| $scope.searchParams.code 
        			|| $scope.searchParams.categoryId) {
	            PropertyPublic.find($scope.searchParams).then(function (response) {
	                return $scope.propertys = response.data;
	            });
        	} else {
                PropertyPublic.findFeatured().then(function (response) {
                    return $scope.propertys = response.data;
                });
        	}
        };
    }]);
