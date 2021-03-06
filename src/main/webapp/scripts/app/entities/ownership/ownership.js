'use strict';

angular.module('aktivingatlanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ownership', {
                parent: 'entity',
                url: '/ownerships',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.ownership.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ownership/ownerships.html',
                        controller: 'OwnershipController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ownership');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('ownership.detail', {
                parent: 'entity',
                url: '/ownership/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.ownership.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ownership/ownership-detail.html',
                        controller: 'OwnershipDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ownership');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Ownership', function($stateParams, Ownership) {
                        return Ownership.get({id : $stateParams.id});
                    }]
                }
            })
            .state('ownership.new', {
                parent: 'ownership',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ownership/ownership-dialog.html',
                        controller: 'OwnershipDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    note: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('ownership', null, { reload: true });
                    }, function() {
                        $state.go('ownership');
                    })
                }]
            })
            .state('ownership.edit', {
                parent: 'ownership',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ownership/ownership-dialog.html',
                        controller: 'OwnershipDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Ownership', function(Ownership) {
                                return Ownership.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ownership', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('ownership.delete', {
                parent: 'ownership',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ownership/ownership-delete-dialog.html',
                        controller: 'OwnershipDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Ownership', function(Ownership) {
                                return Ownership.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ownership', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
