'use strict';

angular.module('aktivingatlanApp').controller('FeatureDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Feature',
        function($scope, $stateParams, $uibModalInstance, entity, Feature) {

        $scope.feature = entity;
        $scope.load = function(id) {
            Feature.get({id : id}, function(result) {
                $scope.feature = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aktivingatlanApp:featureUpdate', result);
            $uibModalInstance.close(result);
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
            $uibModalInstance.dismiss('cancel');
        };
}]);
