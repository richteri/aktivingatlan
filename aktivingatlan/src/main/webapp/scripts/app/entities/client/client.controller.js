'use strict';

angular.module('aktivingatlanApp')
    .controller('ClientController', function ($scope, Client, ParseLinks) {
        $scope.clients = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Client.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.clients = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
                $('#deleteClientConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Client.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteClientConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.client = {name: null, email: null, phone1: null, phone2: null, address1: null, address2: null, idNo: null, note: null, id: null};
        };
    });
