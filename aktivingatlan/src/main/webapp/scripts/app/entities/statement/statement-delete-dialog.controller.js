'use strict';

angular.module('aktivingatlanApp')
	.controller('StatementDeleteController', function($scope, $modalInstance, entity, Statement) {

        $scope.statement = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Statement.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });