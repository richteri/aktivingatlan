'use strict';

angular.module('aktivingatlanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('city', {
                parent: 'entity',
                url: '/citys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.city.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/city/citys.html',
                        controller: 'CityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('city');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('city.detail', {
                parent: 'entity',
                url: '/city/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.city.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/city/city-detail.html',
                        controller: 'CityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('city');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'City', function($stateParams, City) {
                        return City.get({id : $stateParams.id});
                    }]
                }
            })
            .state('city.new', {
                parent: 'city',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/city/city-dialog.html',
                        controller: 'CityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    zip: null,
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('city', null, { reload: true });
                    }, function() {
                        $state.go('city');
                    })
                }]
            })
            .state('city.edit', {
                parent: 'city',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/city/city-dialog.html',
                        controller: 'CityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['City', function(City) {
                                return City.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('city', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
