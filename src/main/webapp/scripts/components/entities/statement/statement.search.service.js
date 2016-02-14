'use strict';

angular.module('aktivingatlanApp')
    .factory('StatementSearch', function ($resource) {
        return $resource('api/_search/statements/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
