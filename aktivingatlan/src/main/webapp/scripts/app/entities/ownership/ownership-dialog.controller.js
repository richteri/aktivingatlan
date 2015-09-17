'use strict';

angular.module('aktivingatlanApp').controller('OwnershipDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Ownership', 'Property', 'Client',
        function($scope, $stateParams, $modalInstance, entity, Ownership, Property, Client) {

        $scope.ownership = entity;
        $scope.propertys = Property.query();
        $scope.clients = Client.query();
        $scope.load = function(id) {
            Ownership.get({id : id}, function(result) {
                $scope.ownership = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('aktivingatlanApp:ownershipUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.ownership.id != null) {
                Ownership.update($scope.ownership, onSaveFinished);
            } else {
                Ownership.save($scope.ownership, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
