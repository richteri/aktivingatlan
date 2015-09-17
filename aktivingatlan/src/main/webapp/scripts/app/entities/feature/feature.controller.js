'use strict';

angular.module('aktivingatlanApp')
    .controller('FeatureController', function ($scope, Feature) {
        $scope.features = [];
        $scope.loadAll = function() {
            Feature.query(function(result) {
               $scope.features = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Feature.get({id: id}, function(result) {
                $scope.feature = result;
                $('#deleteFeatureConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Feature.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFeatureConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.feature = {nameHu: null, nameEn: null, nameDe: null, id: null};
        };
    });
