'use strict';

angular.module('aktivingatlanApp')
    .controller('CityDetailController', function ($scope, $rootScope, $stateParams, entity, City) {
        $scope.city = entity;
        $scope.load = function (id) {
            City.get({id: id}, function(result) {
                $scope.city = result;
            });
        };
        $rootScope.$on('aktivingatlanApp:cityUpdate', function(event, result) {
            $scope.city = result;
        });
    });
