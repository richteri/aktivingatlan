'use strict';

angular.module('aktivingatlanApp')
	.controller('StatementDeleteController', function($scope, $uibModalInstance, entity, Statement) {

        $scope.statement = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Statement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
