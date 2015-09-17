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

        var onSaveFinished = function (result) {
            $scope.$emit('aktivingatlanApp:contractUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.contract.id != null) {
                Contract.update($scope.contract, onSaveFinished);
            } else {
                Contract.save($scope.contract, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
