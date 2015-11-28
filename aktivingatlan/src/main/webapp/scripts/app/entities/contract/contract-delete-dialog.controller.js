'use strict';

angular.module('aktivingatlanApp')
	.controller('ContractDeleteController', function($scope, $modalInstance, entity, Contract) {

        $scope.contract = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Contract.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });