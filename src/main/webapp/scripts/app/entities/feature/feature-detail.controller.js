'use strict';

angular.module('aktivingatlanApp')
    .controller('FeatureDetailController', function ($scope, $rootScope, $stateParams, entity, Feature) {
        $scope.feature = entity;
        $scope.load = function (id) {
            Feature.get({id: id}, function(result) {
                $scope.feature = result;
            });
        };
        var unsubscribe = $rootScope.$on('aktivingatlanApp:featureUpdate', function(event, result) {
            $scope.feature = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
