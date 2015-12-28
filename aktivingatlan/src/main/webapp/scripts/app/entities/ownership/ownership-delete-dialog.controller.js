'use strict';

angular.module('aktivingatlanApp')
	.controller('OwnershipDeleteController', function($scope, $uibModalInstance, entity, Ownership) {

        $scope.ownership = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Ownership.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
