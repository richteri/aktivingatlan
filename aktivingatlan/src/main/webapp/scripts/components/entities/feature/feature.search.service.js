'use strict';

angular.module('aktivingatlanApp')
    .factory('FeatureSearch', function ($resource) {
        return $resource('api/_search/features/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
