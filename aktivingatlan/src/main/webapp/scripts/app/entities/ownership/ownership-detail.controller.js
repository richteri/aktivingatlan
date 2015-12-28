'use strict';

angular.module('aktivingatlanApp')
    .controller('OwnershipDetailController', function ($scope, $rootScope, $stateParams, entity, Ownership, Property, Client) {
        $scope.ownership = entity;
        $scope.load = function (id) {
            Ownership.get({id: id}, function(result) {
                $scope.ownership = result;
            });
        };
        var unsubscribe = $rootScope.$on('aktivingatlanApp:ownershipUpdate', function(event, result) {
            $scope.ownership = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
