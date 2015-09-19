'use strict';

angular.module('aktivingatlanApp')
    .factory('ApartmentSearch', function ($resource) {
        return $resource('api/_search/apartments/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
