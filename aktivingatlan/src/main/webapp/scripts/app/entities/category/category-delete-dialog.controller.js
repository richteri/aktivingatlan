'use strict';

angular.module('aktivingatlanApp')
	.controller('CategoryDeleteController', function($scope, $modalInstance, entity, Category) {

        $scope.category = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Category.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });