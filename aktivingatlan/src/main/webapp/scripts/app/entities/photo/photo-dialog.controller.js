'use strict';

angular.module('aktivingatlanApp').controller('PhotoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Photo', 'Property',
        function($scope, $stateParams, $uibModalInstance, entity, Photo, Property) {

        $scope.photo = entity;
        $scope.propertys = Property.query();
        $scope.load = function(id) {
            Photo.get({id : id}, function(result) {
                $scope.photo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aktivingatlanApp:photoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.photo.id != null) {
                Photo.update($scope.photo, onSaveSuccess, onSaveError);
            } else {
                Photo.save($scope.photo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
