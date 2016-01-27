'use strict';

angular.module('aktivingatlanApp').controller('OwnershipDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$sanitize', 'entity', 'Ownership', 'Property', 'Client', 'PropertySearch', 'ClientSearch',
        function($scope, $stateParams, $uibModalInstance, $sanitize, entity, Ownership, Property, Client, PropertySearch, ClientSearch) {

        $scope.ownership = entity;

        $scope.clientEditable = true;
        $scope.propertyEditable = true;

        // if client id was provided, it cannot be modified
        if ($stateParams.clientId) {
        	$scope.ownership.clientId = $stateParams.clientId;
        	$scope.clientEditable = false;

        	Client.get({id: $stateParams.clientId}, function (result) {
        		$scope.ownership.clientName = result.name;
        	});
        }

        // if property id was provided, it cannot be modified
        if ($stateParams.propertyId) {
        	$scope.ownership.propertyId = $stateParams.propertyId;
        	$scope.propertyEditable = false;

        	Property.get({id: $stateParams.propertyId}, function (result) {
        		$scope.ownership.propertyCode = result.code;
        	});
        }


        $scope.load = function(id) {
            Ownership.get({id : id}, function(result) {
                $scope.ownership = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aktivingatlanApp:ownershipUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.ownership.id != null) {
                Ownership.update($scope.ownership, onSaveSuccess, onSaveError);
            } else {
                Ownership.save($scope.ownership, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.formatPropertyName = function (property) {
        	if (angular.isObject(property)) {
        		return property.code + ' ' + property.cityName;
        	} else {
        		return property;
        	}
        };

        $scope.findPropertyByCode = function (code) {
        	return PropertySearch.findByCode(code);
        };

        $scope.onPropertySelect = function ($item, $model, $label) {
        	$scope.ownership.propertyId = $item.id;
        	$scope.ownership.propertyCode = $item.code;
        };

        $scope.formatClientName = function (client) {
        	if (angular.isObject(client)) {
        		return client.name + ' ' + client.phone1 + ' ' + client.address1 + ' ' + client.idNo;
        	} else {
        		return client;
        	}
        };

        $scope.findClient = function (query) {
        	return ClientSearch.findByAny(query);
        };

        $scope.onClientSelect = function ($item, $model, $label) {
        	$scope.ownership.clientId = $item.id;
        	$scope.ownership.clientName = $item.name;
        };

}]);
