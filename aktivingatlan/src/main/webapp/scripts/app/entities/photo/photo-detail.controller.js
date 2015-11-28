'use strict';

angular.module('aktivingatlanApp')
    .controller('PhotoDetailController', function ($scope, $rootScope, $stateParams, entity, Photo, Property) {
        $scope.photo = entity;
        $scope.load = function (id) {
            Photo.get({id: id}, function(result) {
                $scope.photo = result;
            });
        };
        var unsubscribe = $rootScope.$on('aktivingatlanApp:photoUpdate', function(event, result) {
            $scope.photo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
