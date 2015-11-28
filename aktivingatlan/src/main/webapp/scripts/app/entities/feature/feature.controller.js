'use strict';

angular.module('aktivingatlanApp')
    .controller('FeatureController', function ($scope, $state, $modal, Feature) {
      
        $scope.features = [];
        $scope.loadAll = function() {
            Feature.query(function(result) {
               $scope.features = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.feature = {
                nameHu: null,
                nameEn: null,
                nameDe: null,
                id: null
            };
        };
    });
