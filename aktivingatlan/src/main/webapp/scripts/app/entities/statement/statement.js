'use strict';

angular.module('aktivingatlanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('statement', {
                parent: 'entity',
                url: '/statements',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.statement.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/statement/statements.html',
                        controller: 'StatementController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('statement');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('statement.detail', {
                parent: 'entity',
                url: '/statement/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'aktivingatlanApp.statement.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/statement/statement-detail.html',
                        controller: 'StatementDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('statement');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Statement', function($stateParams, Statement) {
                        return Statement.get({id : $stateParams.id});
                    }]
                }
            })
            .state('statement.new', {
                parent: 'statement',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/statement/statement-dialog.html',
                        controller: 'StatementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {date: null, note: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('statement', null, { reload: true });
                    }, function() {
                        $state.go('statement');
                    })
                }]
            })
            .state('statement.edit', {
                parent: 'statement',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/statement/statement-dialog.html',
                        controller: 'StatementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Statement', function(Statement) {
                                return Statement.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('statement', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
