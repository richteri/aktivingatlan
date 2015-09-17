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

        var onSaveFinished = function (result) {
            $scope.$emit('aktivingatlanApp:featureUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.feature.id != null) {
                Feature.update($scope.feature, onSaveFinished);
            } else {
                Feature.save($scope.feature, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
