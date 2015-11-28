'use strict';

angular.module('aktivingatlanApp').controller('FeatureDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Feature',
        function($scope, $stateParams, $modalInstance, entity, Feature) {

        $scope.feature = entity;
        $scope.load = function(id) {
            Feature.get({id : id}, function(result) {
                $scope.feature = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aktivingatlanApp:featureUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.feature.id != null) {
                Feature.update($scope.feature, onSaveSuccess, onSaveError);
            } else {
                Feature.save($scope.feature, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
