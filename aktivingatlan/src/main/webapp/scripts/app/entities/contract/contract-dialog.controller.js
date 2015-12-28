'use strict';

angular.module('aktivingatlanApp').controller('ContractDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contract', 'Property', 'Client',
        function($scope, $stateParams, $uibModalInstance, entity, Contract, Property, Client) {

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
            $uibModalInstance.close(result);
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
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForStartDate = {};

        $scope.datePickerForStartDate.status = {
            opened: false
        };

        $scope.datePickerForStartDateOpen = function($event) {
            $scope.datePickerForStartDate.status.opened = true;
        };
        $scope.datePickerForEndDate = {};

        $scope.datePickerForEndDate.status = {
            opened: false
        };

        $scope.datePickerForEndDateOpen = function($event) {
            $scope.datePickerForEndDate.status.opened = true;
        };
}]);
