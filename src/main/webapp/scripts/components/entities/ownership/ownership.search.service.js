'use strict';

angular.module('aktivingatlanApp')
    .factory('OwnershipSearch', function ($resource) {
        return $resource('api/_search/ownerships/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
