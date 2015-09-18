'use strict';

angular.module('aktivingatlanApp').controller('ApartmentDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Apartment', 'Property',
        function($scope, $stateParams, $modalInstance, entity, Apartment, Property) {

        $scope.apartment = entity;
        $scope.propertys = Property.query();
        $scope.load = function(id) {
            Apartment.get({id : id}, function(result) {
                $scope.apartment = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('aktivingatlanApp:apartmentUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.apartment.id != null) {
                Apartment.update($scope.apartment, onSaveFinished);
            } else {
                Apartment.save($scope.apartment, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
