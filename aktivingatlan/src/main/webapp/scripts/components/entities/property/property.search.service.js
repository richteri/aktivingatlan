'use strict';

angular.module('aktivingatlanApp')
    .factory('PropertySearch', function ($resource) {
        return $resource('api/_search/propertys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
