'use strict';

angular.module('aktivingatlanApp').controller('StatementDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Statement', 'Client', 'Property',
        function($scope, $stateParams, $uibModalInstance, entity, Statement, Client, Property) {

        $scope.statement = entity;
        $scope.clients = Client.query();
        $scope.propertys = Property.query();
        $scope.load = function(id) {
            Statement.get({id : id}, function(result) {
                $scope.statement = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aktivingatlanApp:statementUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.statement.id != null) {
                Statement.update($scope.statement, onSaveSuccess, onSaveError);
            } else {
                Statement.save($scope.statement, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
