'use strict';

angular.module('aktivingatlanApp')
    .controller('PropertyDetailController', function ($scope, $rootScope, $stateParams, entity, Property, Category, Photo, Statement, Feature, Ownership, City, Contract, User, Apartment) {
        $scope.property = entity;
        $scope.load = function (id) {
            Property.get({id: id}, function(result) {
                $scope.property = result;
            });
        };
        var unsubscribe = $rootScope.$on('aktivingatlanApp:propertyUpdate', function(event, result) {
            $scope.property = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
