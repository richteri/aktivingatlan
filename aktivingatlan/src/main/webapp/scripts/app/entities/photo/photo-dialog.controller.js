'use strict';

angular.module('aktivingatlanApp').controller('PhotoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Photo', 'Property',
        function($scope, $stateParams, $modalInstance, entity, Photo, Property) {

        $scope.photo = entity;
        $scope.propertys = Property.query();
        $scope.load = function(id) {
            Photo.get({id : id}, function(result) {
                $scope.photo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aktivingatlanApp:photoUpdate', result);
            $modalInstance.close(result);
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
            $modalInstance.dismiss('cancel');
        };
}]);
