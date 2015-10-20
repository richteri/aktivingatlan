'use strict';

angular.module('aktivingatlanApp')
    .controller('ApartmentController', function ($scope, Apartment, ParseLinks) {
        $scope.apartments = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Apartment.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.apartments = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Apartment.get({id: id}, function(result) {
                $scope.apartment = result;
                $('#deleteApartmentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Apartment.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteApartmentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.apartment = {
                bed: null,
                bathroom: null,
                toilet: null,
                rentHuf: null,
                rentEur: null,
                rentPeakHuf: null,
                rentPeakEur: null,
                descriptionHu: null,
                descriptionEn: null,
                descriptionDe: null,
                id: null
            };
        };
    });
