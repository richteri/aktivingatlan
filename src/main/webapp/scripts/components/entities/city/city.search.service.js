'use strict';

angular.module('aktivingatlanApp')
    .factory('CitySearch', function ($resource) {
        return $resource('api/_search/citys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
