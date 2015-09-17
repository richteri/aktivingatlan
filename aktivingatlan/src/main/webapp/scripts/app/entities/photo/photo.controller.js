'use strict';

angular.module('aktivingatlanApp')
    .controller('PhotoController', function ($scope, Photo, ParseLinks) {
        $scope.photos = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Photo.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.photos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Photo.get({id: id}, function(result) {
                $scope.photo = result;
                $('#deletePhotoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Photo.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePhotoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.photo = {header: null, descriptionHu: null, descriptionEn: null, descriptionDe: null, filename: null, id: null};
        };
    });
