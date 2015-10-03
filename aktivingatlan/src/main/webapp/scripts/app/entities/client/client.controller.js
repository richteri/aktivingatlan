'use strict';

angular.module('aktivingatlanApp')
    .controller('ClientController', function ($scope, Client, ClientSearch, ParseLinks, Pageable, Ownership) {
        $scope.clients = [];
        $scope.pageable = new Pageable($scope, 'pageable');

        $scope.load = function() {
            if ($scope.pageable.filtered) {
                // Search
                ClientSearch.query($scope.pageable, function(result, headers) {
                    $scope.clients = result;
                    $scope.links = ParseLinks.parse(headers('link'));
                    if ($scope.links['last'] < $scope.pageable.page) {
                        $scope.loadPage($scope.links['last']);
                    }
                });
            } else {
                // Load
                Client.query($scope.pageable, function(result, headers) {
                    $scope.clients = result;
                    $scope.links = ParseLinks.parse(headers('link'));
                });
            }
        }

        // Load one page
        $scope.loadPage = function(page) {
            $scope.pageable.page = page;
            $scope.load();
        };

        // Search
        $scope.search = function() {
            $scope.pageable.filtered = ($scope.pageable.query != "");
            $scope.load();
        }

        // Clear search
        $scope.clearSearch = function () {
            $scope.pageable.query = "";
            $scope.pageable.filtered = false;
            $scope.loadPage(1);
        };

        // Modify sort criteria
        $scope.sortBy = function (property) {
            $scope.pageable.sortBy(property);
            $scope.load();
        }


        // Show delete confirmation window
        $scope.delete = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
                $('#deleteClientConfirmation').modal('show');
            });
        };

        // Confirm delete
        $scope.confirmDelete = function (id) {
            Client.delete({id: id},
                function () {
                    $scope.load();
                    $('#deleteClientConfirmation').modal('hide');
                    $scope.clear();
                });
        };
        
        $scope.clear = function () {
            $scope.client = {name: null, email: null, phone1: null, phone2: null, address1: null, address2: null, idNo: null, note: null, id: null};
        };
        
        $scope.load();

    });
