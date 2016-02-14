'use strict';

angular.module('aktivingatlanApp').controller('ApartmentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Apartment', 'Property',
        function($scope, $stateParams, $uibModalInstance, entity, Apartment, Property) {

        $scope.apartment = entity;
        $scope.propertys = Property.query();
        $scope.load = function(id) {
            Apartment.get({id : id}, function(result) {
                $scope.apartment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aktivingatlanApp:apartmentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.apartment.id != null) {
                Apartment.update($scope.apartment, onSaveSuccess, onSaveError);
            } else {
                Apartment.save($scope.apartment, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
