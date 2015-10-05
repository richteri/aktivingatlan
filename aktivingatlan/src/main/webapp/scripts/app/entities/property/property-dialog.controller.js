'use strict';

angular.module('aktivingatlanApp').controller('PropertyDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$filter', 'entity', 'Property', 'Category', 'Photo', 'Statement', 'Feature', 'Ownership', 'City', 'Contract', 'User', 'Apartment', 'CitySearch',
        function($scope, $stateParams, $modalInstance, $filter, entity, Property, Category, Photo, Statement, Feature, Ownership, City, Contract, User, Apartment, CitySearch) {

        $scope.property = entity;
        $scope.categorys = Category.query();
        $scope.photos = Photo.query();
        $scope.statements = Statement.query();
        $scope.features = Feature.query();
        $scope.ownerships = Ownership.query();
        $scope.contracts = Contract.query();
        $scope.users = User.query();
        $scope.apartments = Apartment.query();
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
        
        $scope.formatCityName = function (city) {
        	if (angular.isObject(city)) {
        		return city.zip + ' ' + city.name;
        	} else {
        		return city;
        	}
        };
        
        $scope.findCity = function (param) {
        	return CitySearch.query({
        		query: param
        	}).$promise;
        };
        
        $scope.onCitySelect = function ($item, $model, $label) {
        	$scope.property.cityId = $item.id;
        	$scope.property.cityName = $item.name;
        };
        
        $scope.filterAlreadyAddedFeatures = function (value, index, array) {
        	return !$filter('filter')($scope.property.features, {id: value.id})[0];
        };

}]);
