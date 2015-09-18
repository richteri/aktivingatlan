'use strict';

angular.module('aktivingatlanApp')
    .controller('ApartmentDetailController', function ($scope, $rootScope, $stateParams, entity, Apartment, Property) {
        $scope.apartment = entity;
        $scope.load = function (id) {
            Apartment.get({id: id}, function(result) {
                $scope.apartment = result;
            });
        };
        $rootScope.$on('aktivingatlanApp:apartmentUpdate', function(event, result) {
            $scope.apartment = result;
        });
    });
