'use strict';

angular.module('aktivingatlanApp')
	.controller('ApartmentDeleteController', function($scope, $uibModalInstance, entity, Apartment) {

        $scope.apartment = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Apartment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
