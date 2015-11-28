'use strict';

angular.module('aktivingatlanApp')
	.controller('ApartmentDeleteController', function($scope, $modalInstance, entity, Apartment) {

        $scope.apartment = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Apartment.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });