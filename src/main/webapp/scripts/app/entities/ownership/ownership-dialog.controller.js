'use strict';

angular.module('aktivingatlanApp').controller('OwnershipDialogController',
    ['$scope', '$uibModalInstance', 'entity', 'Ownership',
        function($scope, $uibModalInstance, entity, Ownership) {

        $scope.ownership = entity;


        $scope.load = function(id) {
            Ownership.get({id : id}, function(result) {
                $scope.ownership = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aktivingatlanApp:ownershipUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.ownership.id != null) {
                Ownership.update($scope.ownership, onSaveSuccess, onSaveError);
            } else {
                Ownership.save($scope.ownership, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

}]);
