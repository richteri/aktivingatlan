'use strict';

angular.module('aktivingatlanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('feature', {
                parent: 'entity',
                url: '/features',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.feature.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feature/features.html',
                        controller: 'FeatureController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feature');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('feature.detail', {
                parent: 'entity',
                url: '/feature/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.feature.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feature/feature-detail.html',
                        controller: 'FeatureDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feature');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Feature', function($stateParams, Feature) {
                        return Feature.get({id : $stateParams.id});
                    }]
                }
            })
            .state('feature.new', {
                parent: 'feature',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feature/feature-dialog.html',
                        controller: 'FeatureDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nameHu: null,
                                    nameEn: null,
                                    nameDe: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('feature', null, { reload: true });
                    }, function() {
                        $state.go('feature');
                    })
                }]
            })
            .state('feature.edit', {
                parent: 'feature',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feature/feature-dialog.html',
                        controller: 'FeatureDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Feature', function(Feature) {
                                return Feature.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feature', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('feature.delete', {
                parent: 'feature',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feature/feature-delete-dialog.html',
                        controller: 'FeatureDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Feature', function(Feature) {
                                return Feature.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feature', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
