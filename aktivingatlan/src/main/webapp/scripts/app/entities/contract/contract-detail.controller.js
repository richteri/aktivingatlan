'use strict';

angular.module('aktivingatlanApp')
    .controller('ContractDetailController', function ($scope, $rootScope, $stateParams, entity, Contract, Property, Client) {
        $scope.contract = entity;
        $scope.load = function (id) {
            Contract.get({id: id}, function(result) {
                $scope.contract = result;
            });
        };
        var unsubscribe = $rootScope.$on('aktivingatlanApp:contractUpdate', function(event, result) {
            $scope.contract = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
