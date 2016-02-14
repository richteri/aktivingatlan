'use strict';

angular.module('aktivingatlanApp')
	.controller('CityDeleteController', function($scope, $uibModalInstance, entity, City) {

        $scope.city = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            City.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
