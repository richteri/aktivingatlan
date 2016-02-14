'use strict';

angular.module('aktivingatlanApp').controller('ClientDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Client', 'Ownership', 'Statement', 'Contract',
        function($scope, $stateParams, $uibModalInstance, entity, Client, Ownership, Statement, Contract) {

        $scope.client = entity;
        $scope.ownerships = Ownership.query();
        $scope.statements = Statement.query();
        $scope.contracts = Contract.query();
        $scope.load = function(id) {
            Client.get({id : id}, function(result) {
                $scope.client = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aktivingatlanApp:clientUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.client.id != null) {
                Client.update($scope.client, onSaveSuccess, onSaveError);
            } else {
                Client.save($scope.client, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
