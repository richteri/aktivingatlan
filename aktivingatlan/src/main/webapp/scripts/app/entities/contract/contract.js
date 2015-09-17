'use strict';

angular.module('aktivingatlanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contract', {
                parent: 'entity',
                url: '/contracts',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.contract.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contract/contracts.html',
                        controller: 'ContractController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contract');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('contract.detail', {
                parent: 'entity',
                url: '/contract/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.contract.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contract/contract-detail.html',
                        controller: 'ContractDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contract');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Contract', function($stateParams, Contract) {
                        return Contract.get({id : $stateParams.id});
                    }]
                }
            })
            .state('contract.new', {
                parent: 'contract',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/contract/contract-dialog.html',
                        controller: 'ContractDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {idNo: null, exclusive: null, startDate: null, endDate: null, note: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('contract', null, { reload: true });
                    }, function() {
                        $state.go('contract');
                    })
                }]
            })
            .state('contract.edit', {
                parent: 'contract',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/contract/contract-dialog.html',
                        controller: 'ContractDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Contract', function(Contract) {
                                return Contract.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contract', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
