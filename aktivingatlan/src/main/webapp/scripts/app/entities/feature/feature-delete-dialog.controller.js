'use strict';

angular.module('aktivingatlanApp')
	.controller('FeatureDeleteController', function($scope, $uibModalInstance, entity, Feature) {

        $scope.feature = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Feature.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
