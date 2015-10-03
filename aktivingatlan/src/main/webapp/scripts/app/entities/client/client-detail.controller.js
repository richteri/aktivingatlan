'use strict';

angular.module('aktivingatlanApp')
    .controller('ClientDetailController', function ($scope, $rootScope, $stateParams, entity, Client, Ownership, Statement, Contract) {
        $scope.client = entity;
        $scope.load = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
            });
        };
        
        $rootScope.$on('aktivingatlanApp:clientUpdate', function(event, result) {
            $scope.client = result;
        });
        
        // Show ownership delete confirmation window
        $scope.deleteOwnership = function (id) {
        	console.log('deleteOwnership called with id:', id);
            Ownership.get({id: id}, function(result) {
                $scope.ownership = result;
                $('#deleteOwnershipConfirmation').modal('show');
            });
        };

        // Confirm ownership delete
        $scope.confirmDeleteOwnership = function (id) {
            Ownership.delete({id: id},
                function () {
                    $scope.load($scope.client.id);
                    $('#deleteOwnershipConfirmation').modal('hide');
                    $scope.clearOwnership();
                });
        };
        
        $scope.clearOwnership = function () {
        	$scope.ownership = {note: null, id: null, clientId: null, propertyId: null};
        }

    });
