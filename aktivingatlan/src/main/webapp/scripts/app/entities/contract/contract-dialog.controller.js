'use strict';

angular.module('aktivingatlanApp').controller('ContractDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Contract', 'Property', 'Client',
        function($scope, $stateParams, $modalInstance, entity, Contract, Property, Client) {

        $scope.contract = entity;
        $scope.propertys = Property.query();
        $scope.clients = Client.query();
        $scope.load = function(id) {
            Contract.get({id : id}, function(result) {
                $scope.contract = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aktivingatlanApp:contractUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.contract.id != null) {
                Contract.update($scope.contract, onSaveSuccess, onSaveError);
            } else {
                Contract.save($scope.contract, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
