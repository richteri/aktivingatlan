'use strict';

angular.module('aktivingatlanApp')
    .controller('ApartmentController', function ($scope, $state, Apartment, ParseLinks) {

        $scope.apartments = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Apartment.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.apartments = result;
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
