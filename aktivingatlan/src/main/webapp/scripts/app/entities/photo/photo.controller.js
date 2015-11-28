'use strict';

angular.module('aktivingatlanApp')
    .controller('PhotoController', function ($scope, $state, $modal, Photo, ParseLinks) {
      
        $scope.photos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Photo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.photos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.photo = {
                header: null,
                descriptionHu: null,
                descriptionEn: null,
                descriptionDe: null,
                filename: null,
                id: null
            };
        };
    });
