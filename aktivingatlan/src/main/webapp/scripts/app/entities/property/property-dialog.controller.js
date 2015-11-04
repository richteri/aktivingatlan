'use strict';

angular.module('aktivingatlanApp').controller('PropertyDialogController',
    ['$scope', '$stateParams', '$filter', 'entity', 'Property', 'PropertySearch', 'Category', 'Photo', 'Statement', 'Feature', 'Ownership', 'City', 'Contract', 'User', 'Apartment', 'CitySearch', 'Upload',
        function($scope, $stateParams, $filter, entity, Property, PropertySearch, Category, Photo, Statement, Feature, Ownership, City, Contract, User, Apartment, CitySearch, Upload) {

        $scope.files = null;
        $scope.property = entity;
        $scope.categorys = Category.query();
        $scope.features = Feature.query();
        $scope.users = User.query();
        
        $scope.load = function(id) {
            Property.get({id : id}, function(result) {
                $scope.property = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('aktivingatlanApp:propertyUpdate', result);
            // TODO: $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.property.id != null) {
                Property.update($scope.property, onSaveFinished);
            } else {
                Property.save($scope.property, onSaveFinished);
            }
        };

        $scope.clear = function() {
        	// TODO: $modalInstance.dismiss('cancel');
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
        	var found = $filter('filter')($scope.property.features, {id: value.id});
        	if (angular.isArray(found) && found.length > 0) {
        		return false;
        	}
        	return true;
        };
        
        $scope.uploadFiles = function (files) {
            $scope.files = files;
            if (!$scope.files) return;
            angular.forEach(files, function (file) {
              if (file && !file.$error) {
            	file.public_id = $filter('urlify')(file.name);
                file.upload = Upload.upload({
                  url: 'https://api.cloudinary.com/v1_1/aktivingatlan/upload',
                  fields: {
                    upload_preset: 'n2VjU5fy',
                    tags: $scope.property.code,
      			  	public_id: file.public_id
                  },
                  file: file
                }).progress(function (e) {
                  file.progress = Math.round((e.loaded * 100.0) / e.total);
                  file.status = "Uploading... " + file.progress + "%";
                }).success(function (data, status, headers, config) {
                  file.result = data;
                  Photo.save({propertyId: $scope.property.id, filename: file.public_id}, function(photo) {
                	  $scope.property.photos.push(photo);
                  });
                }).error(function (data, status, headers, config) {
                  file.result = data;
                  //TODO error handling... message?
                  console.log(data);
                });
              }
            });
          };

}]);
