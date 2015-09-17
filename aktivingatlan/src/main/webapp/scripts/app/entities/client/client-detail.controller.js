'use strict';

angular.module('aktivingatlanApp')
    .controller('ClientDetailController', function ($scope, $rootScope, $stateParams, entity, Client, Ownership, Statement, Contract) {
        $scope.client = entity;
        $scope.load = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
            });
        };
        $rootScope.$on('aktivingatlanApp:clientUpdate', function(event, result) {
            $scope.client = result;
        });
    });
