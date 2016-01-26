'use strict';

angular.module('aktivingatlanApp').controller('PropertyDialogController',
    ['$scope', '$stateParams', '$filter', '$sanitize', 'AlertService', 'entity', 'Property', 'PropertySearch', 'Category',
        'Photo', 'Statement', 'Feature', 'Ownership', 'City', 'Contract', 'User', 'Apartment', 'CitySearch', 'Upload',
        function ($scope, $stateParams, $filter, $sanitize, AlertService, entity, Property, PropertySearch, Category,
                  Photo, Statement, Feature, Ownership, City, Contract, User, Apartment, CitySearch, Upload) {

            $scope.property = entity;
            $scope.files = null;
            $scope.categorys = Category.query();
            $scope.features = Feature.query();
            $scope.users = User.query();

            $scope.load = function (id) {
                Property.get({id: id}, function (result) {
                    $scope.property = result;
                });
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('aktivingatlanApp:propertyUpdate', result);
                $scope.isSaving = false;
                $scope.propertyEditForm.$setPristine();
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.property.id != null) {
                    $scope.property = Property.update($scope.property, onSaveSuccess, onSaveError);
                } else {
                    $scope.property = Property.save($scope.property, onSaveSuccess, onSaveError);
                }
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

            $scope.uploadPhotos = function (files) {
                AlertService.warning("Képfeltöltés folyamatban");
                $scope.files = files;
                if (!$scope.files) return;
                angular.forEach(files, function (file) {
                    if (file && !file.$error) {
                        file.public_id = $filter('urlify')(file.name);
                        file.upload = Upload.upload({
                            url: 'https://api.cloudinary.com/v1_1/aktivingatlan/upload',
                            fields: {
                                upload_preset: 'n2VjU5fy',
                                tags: $scope.property.code + ',ID' + $scope.property.id,
                                public_id: file.public_id
                            },
                            file: file
                        }).progress(function (e) {
                            file.progress = Math.round((e.loaded * 100.0) / e.total);
                            file.status = "Uploading... " + file.progress + "%";
                        }).success(function (data, status, headers, config) {
                            file.result = data;
                            Photo.save({propertyId: $scope.property.id, filename: file.public_id}, function (photo) {
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

            $scope.deletePhoto = function (photo) {
                Photo.delete({id:photo.id}, function () {
                    var index = $scope.property.photos.indexOf(photo);
                    $scope.property.photos.splice(index, 1);
                });
            };

            $scope.updatePhoto = function (photo) {
                Photo.update(photo);
                delete photo.changed;
            };

            $scope.updateApartment = function (apartment) {
                var updated = null;

                if (apartment.id != null) {
                    updated = Apartment.update(apartment);
                } else {
                    updated = Apartment.save(apartment);
                }

                var index = $scope.property.apartments.indexOf(apartment);
                $scope.property.apartments[index] = updated;
            };

            $scope.deleteApartment = function (apartment) {
                var index = $scope.property.apartments.indexOf(apartment);
                if (apartment.id != null) {
                    Apartment.delete({id: apartment.id}, function () {
                        $scope.property.apartments.splice(index, 1);
                    });
                } else {
                    $scope.property.apartments.splice(index, 1);
                }
            };

            $scope.addApartment = function () {
              if ($scope.property.apartments == null) {
                  $scope.property.apartments = [];
              }
              $scope.property.apartments.push({
                  propertyId: $scope.property.id,
                  bed: null,
                  bathroom: null,
                  toilet: null,
                  rentHuf: null,
                  rentEur: null,
                  rentPeakHuf: null,
                  rentPeakEur: null,
                  descriptionHu: null,
                  descriptionEn: null,
                  descriptionDe: null,
                  id: null
              });
            };

            $scope.tabPropertyDeselect = function () {
                if ($scope.propertyEditForm.$dirty) {
                    console.log('Property form is dirty: saving');
                    $scope.save();
                }
            };

            $scope.tabImagesDeselect = function () {
                console.log('Images deselected');
            };

            $scope.tabApartmentsDeselect = function () {
                console.log('Apartments deselected');
            };

            $scope.tabContractsDeselect = function () {
                console.log('Contracts deselected');
            };

            $scope.tabStatementsDeselect = function () {
                console.log('Statements deselected');
            };


        }]);
