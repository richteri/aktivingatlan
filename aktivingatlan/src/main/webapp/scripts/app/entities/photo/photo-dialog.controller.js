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

        var onSaveFinished = function (result) {
            $scope.$emit('aktivingatlanApp:photoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.photo.id != null) {
                Photo.update($scope.photo, onSaveFinished);
            } else {
                Photo.save($scope.photo, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
