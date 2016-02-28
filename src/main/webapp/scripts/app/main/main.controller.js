'use strict';

angular.module('aktivingatlanApp')
    .controller('MainController', function ($scope, Principal, PropertyPublic) {
       
        $scope.featured = [];
        PropertyPublic.findFeatured().then(function (response) {
        	console.log(response.data);
            return $scope.featured = response.data;
        });
        
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    });
