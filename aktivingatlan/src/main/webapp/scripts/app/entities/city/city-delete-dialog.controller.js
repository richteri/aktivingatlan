'use strict';

angular.module('aktivingatlanApp')
	.controller('CityDeleteController', function($scope, $modalInstance, entity, City) {

        $scope.city = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            City.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });