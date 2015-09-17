'use strict';

angular.module('aktivingatlanApp').controller('CityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'City',
        function($scope, $stateParams, $modalInstance, entity, City) {

        $scope.city = entity;
        $scope.load = function(id) {
            City.get({id : id}, function(result) {
                $scope.city = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('aktivingatlanApp:cityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.city.id != null) {
                City.update($scope.city, onSaveFinished);
            } else {
                City.save($scope.city, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
