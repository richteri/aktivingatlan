'use strict';

angular.module('aktivingatlanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('client', {
                parent: 'entity',
                url: '/clients',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.client.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/client/clients.html',
                        controller: 'ClientController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('client');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('client.detail', {
                parent: 'entity',
                url: '/client/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.client.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/client/client-detail.html',
                        controller: 'ClientDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('client');
                        $translatePartialLoader.addPart('ownership');
                        $translatePartialLoader.addPart('statement');
                        $translatePartialLoader.addPart('contract');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Client', function($stateParams, Client) {
                        return Client.get({id : $stateParams.id});
                    }]
                }
            })
            .state('client.new', {
                parent: 'client',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/client/client-dialog.html',
                        controller: 'ClientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, email: null, phone1: null, phone2: null, address1: null, address2: null, idNo: null, note: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('client', null, { reload: true });
                    }, function() {
                        $state.go('client');
                    })
                }]
            })
            .state('client.edit', {
                parent: 'client',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/client/client-dialog.html',
                        controller: 'ClientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Client', function(Client) {
                                return Client.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('client', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            }).state('client.detail.ownershipNew', {
                parent: 'client.detail',
                url: '/client/{id}/ownership/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ownership/ownership-dialog.html',
                        controller: 'OwnershipDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {note: null, id: null, clientId: $stateParams.clientId};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('client.detail', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            }).state('client.detail.ownershipEdit', {
                parent: 'client.detail',
                url: '/client/{id}/ownership/{ownershipId}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ownership/ownership-dialog.html',
                        controller: 'OwnershipDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Ownership', function(Ownership) {
                                return Ownership.get({id : $stateParams.ownershipId});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('client.detail', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
