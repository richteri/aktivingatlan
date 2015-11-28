'use strict';

angular.module('aktivingatlanApp')
	.controller('PhotoDeleteController', function($scope, $modalInstance, entity, Photo) {

        $scope.photo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Photo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });