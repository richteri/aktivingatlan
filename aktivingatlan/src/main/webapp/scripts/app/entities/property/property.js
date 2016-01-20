'use strict';

angular.module('aktivingatlanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('property', {
                parent: 'entity',
                url: '/propertys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.property.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/property/propertys.html',
                        controller: 'PropertyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('property');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('property.detail', {
                parent: 'entity',
                url: '/property/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.property.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/property/property-detail.html',
                        controller: 'PropertyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('property');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Property', function($stateParams, Property) {
                        return Property.get({id : $stateParams.id});
                    }]
                }
            })
            .state('property.new', {
                parent: 'property',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/property/property-dialog.html',
                        controller: 'PropertyDialogController',
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('property');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            code: null,
                            descriptionHu: null,
                            descriptionEn: null,
                            descriptionDe: null,
                            room: null,
                            halfRoom: null,
                            floorArea: null,
                            parcelArea: null,
                            pracelNumber: null,
                            address1: null,
                            address2: null,
                            active: null,
                            kitchen: null,
                            livingroom: null,
                            floor: null,
                            bathroom: null,
                            toilet: null,
                            furnished: null,
                            forSale: null,
                            saleHuf: null,
                            saleEur: null,
                            forRent: null,
                            rentHuf: null,
                            rentEur: null,
                            rentPeakHuf: null,
                            rentPeakEur: null,
                            forMediumTerm: null,
                            mediumTermHuf: null,
                            mediumTermEur: null,
                            forLongTerm: null,
                            longTermHuf: null,
                            longTermEur: null,
                            featured: null,
                            id: null
                        };
                    }
                }
            })
            .state('property.edit', {
                parent: 'entity',
                url: '/property/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.property.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/property/property-dialog.html',
                        controller: 'PropertyDialogController',
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('property');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Property', function($stateParams, Property) {
                        return Property.get({id : $stateParams.id});
                    }]
                }
            })
            .state('property.delete', {
                parent: 'property',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/property/property-delete-dialog.html',
                        controller: 'PropertyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Property', function(Property) {
                                return Property.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('property', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
