'use strict';

angular.module('aktivingatlanApp')
    .controller('StatementDetailController', function ($scope, $rootScope, $stateParams, entity, Statement, Client, Property) {
        $scope.statement = entity;
        $scope.load = function (id) {
            Statement.get({id: id}, function(result) {
                $scope.statement = result;
            });
        };
        var unsubscribe = $rootScope.$on('aktivingatlanApp:statementUpdate', function(event, result) {
            $scope.statement = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
