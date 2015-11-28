'use strict';

angular.module('aktivingatlanApp')
    .controller('CategoryController', function ($scope, $state, $modal, Category) {
      
        $scope.categorys = [];
        $scope.loadAll = function() {
            Category.query(function(result) {
               $scope.categorys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.category = {
                nameHu: null,
                nameEn: null,
                nameDe: null,
                id: null
            };
        };
    });
