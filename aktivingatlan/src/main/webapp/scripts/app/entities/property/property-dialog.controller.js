'use strict';

angular.module('aktivingatlanApp').controller('PropertyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Property', 'Category', 'Photo', 'Statement', 'Feature', 'Ownership', 'City', 'Contract', 'User',
        function($scope, $stateParams, $modalInstance, entity, Property, Category, Photo, Statement, Feature, Ownership, City, Contract, User) {

        $scope.property = entity;
        $scope.categorys = Category.query();
        $scope.photos = Photo.query();
        $scope.statements = Statement.query();
        $scope.features = Feature.query();
        $scope.ownerships = Ownership.query();
        $scope.citys = City.query();
        $scope.contracts = Contract.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Property.get({id : id}, function(result) {
                $scope.property = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('aktivingatlanApp:propertyUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.property.id != null) {
                Property.update($scope.property, onSaveFinished);
            } else {
                Property.save($scope.property, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
