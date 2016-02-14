'use strict';

angular.module('aktivingatlanApp')
	.controller('PhotoDeleteController', function($scope, $uibModalInstance, entity, Photo) {

        $scope.photo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Photo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
