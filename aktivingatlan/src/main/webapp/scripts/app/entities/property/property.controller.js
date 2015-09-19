'use strict';

angular.module('aktivingatlanApp')
    .controller('PropertyController', function ($scope, Property, PropertySearch, ParseLinks) {
        $scope.propertys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Property.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.propertys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Property.get({id: id}, function(result) {
                $scope.property = result;
                $('#deletePropertyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Property.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePropertyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PropertySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.propertys = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.property = {code: null, descriptionHu: null, descriptionEn: null, descriptionDe: null, room: null, halfRoom: null, floorArea: null, parcelArea: null, pracelNumber: null, address1: null, address2: null, active: null, kitchen: null, livingroom: null, floor: null, bathroom: null, toilet: null, furnished: null, forSale: null, saleHuf: null, saleEur: null, forRent: null, rentHuf: null, rentEur: null, rentPeakHuf: null, rentPeakEur: null, forMediumTerm: null, mediumTermHuf: null, mediumTermEur: null, forLongTerm: null, longTermHuf: null, longTermEur: null, featured: null, id: null};
        };
    });
