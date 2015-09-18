'use strict';

angular.module('aktivingatlanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('apartment', {
                parent: 'entity',
                url: '/apartments',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.apartment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/apartment/apartments.html',
                        controller: 'ApartmentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('apartment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('apartment.detail', {
                parent: 'entity',
                url: '/apartment/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.apartment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/apartment/apartment-detail.html',
                        controller: 'ApartmentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('apartment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Apartment', function($stateParams, Apartment) {
                        return Apartment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('apartment.new', {
                parent: 'apartment',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/apartment/apartment-dialog.html',
                        controller: 'ApartmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {bed: null, bathroom: null, toilet: null, rentHuf: null, rentEur: null, rentPeakHuf: null, rentPeakEur: null, descriptionHu: null, descriptionEn: null, descriptionDe: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('apartment', null, { reload: true });
                    }, function() {
                        $state.go('apartment');
                    })
                }]
            })
            .state('apartment.edit', {
                parent: 'apartment',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/apartment/apartment-dialog.html',
                        controller: 'ApartmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Apartment', function(Apartment) {
                                return Apartment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('apartment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
