'use strict';

angular.module('aktivingatlanApp').controller('ClientDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Client', 'Ownership', 'Statement', 'Contract',
        function($scope, $stateParams, $modalInstance, entity, Client, Ownership, Statement, Contract) {

        $scope.client = entity;
        $scope.ownerships = Ownership.query();
        $scope.statements = Statement.query();
        $scope.contracts = Contract.query();
        $scope.load = function(id) {
            Client.get({id : id}, function(result) {
                $scope.client = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('aktivingatlanApp:clientUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.client.id != null) {
                Client.update($scope.client, onSaveFinished);
            } else {
                Client.save($scope.client, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
