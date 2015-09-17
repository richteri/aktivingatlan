'use strict';

angular.module('aktivingatlanApp').controller('StatementDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Statement', 'Client', 'Property',
        function($scope, $stateParams, $modalInstance, entity, Statement, Client, Property) {

        $scope.statement = entity;
        $scope.clients = Client.query();
        $scope.propertys = Property.query();
        $scope.load = function(id) {
            Statement.get({id : id}, function(result) {
                $scope.statement = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('aktivingatlanApp:statementUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.statement.id != null) {
                Statement.update($scope.statement, onSaveFinished);
            } else {
                Statement.save($scope.statement, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
