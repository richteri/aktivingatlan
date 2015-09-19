'use strict';

angular.module('aktivingatlanApp')
    .factory('CategorySearch', function ($resource) {
        return $resource('api/_search/categorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
