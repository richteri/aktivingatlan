'use strict';

angular.module('aktivingatlanApp')
	.controller('ContractDeleteController', function($scope, $uibModalInstance, entity, Contract) {

        $scope.contract = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Contract.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
