'use strict';

angular.module('aktivingatlanApp').controller('PropertyDialogController',
    ['$scope', '$stateParams', '$filter', '$sanitize', 'AlertService', 'entity', 'Property', 'PropertySearch', 'Category',
        'Photo', 'Statement', 'Feature', 'Ownership', 'City', 'Contract', 'User', 'Apartment', 'CitySearch', 'Upload',
        'ClientSearch',
        function ($scope, $stateParams, $filter, $sanitize, AlertService, entity, Property, PropertySearch, Category,
                  Photo, Statement, Feature, Ownership, City, Contract, User, Apartment, CitySearch, Upload,
            ClientSearch) {

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
                var index = $scope.property.apartments.indexOf(apartment);

                if (apartment.id != null) {
                    Apartment.update(apartment);
                } else {
                    Apartment.save(apartment).$promise.then(function(entity){
                        $scope.property.apartments[index].id = entity.id;
                    });
                }

                delete $scope.property.apartments[index].$dirty;
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

            $scope.updateOwnership = function (ownership) {
                var index = $scope.property.ownerships.indexOf(ownership);
                if (ownership.id != null) {
                    Ownership.update(ownership);
                } else {
                    Ownership.save(ownership).$promise.then(function(entity){
                        $scope.property.ownerships[index].id = entity.id;
                    });
                }
                delete $scope.property.ownerships[index].$dirty;
            };

            $scope.deleteOwnership = function (ownership) {
                var index = $scope.property.ownerships.indexOf(ownership);
                if (ownership.id != null) {
                    Ownership.delete({id: ownership.id}, function () {
                        $scope.property.ownerships.splice(index, 1);
                    });
                } else {
                    $scope.property.ownerships.splice(index, 1);
                }
            };

            $scope.addOwnership = function () {
                if ($scope.property.ownerships == null) {
                    $scope.property.ownerships = [];
                }
                $scope.property.ownerships.push({
                    propertyId: $scope.property.id,
                    note: null,
                    clientId: null,
                    clientName: null,
                    id: null
                });
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

            $scope.onClientSelect = function ($item, ownership) {
                ownership.clientId = $item.id;
                ownership.clientName = $item.name;
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


            // Contract Date pickers
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
