'use strict';

angular.module('aktivingatlanApp').controller('CityDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'City',
        function($scope, $stateParams, $uibModalInstance, entity, City) {

        $scope.city = entity;
        $scope.load = function(id) {
            City.get({id : id}, function(result) {
                $scope.city = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aktivingatlanApp:cityUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.city.id != null) {
                City.update($scope.city, onSaveSuccess, onSaveError);
            } else {
                City.save($scope.city, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
