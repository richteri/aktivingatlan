'use strict';

angular.module('aktivingatlanApp')
	.controller('FeatureDeleteController', function($scope, $modalInstance, entity, Feature) {

        $scope.feature = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Feature.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });