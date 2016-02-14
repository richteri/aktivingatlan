'use strict';

angular.module('aktivingatlanApp')
	.controller('PropertyDeleteController', function($scope, $uibModalInstance, entity, Property) {

        $scope.property = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Property.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
