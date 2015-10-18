'use strict';

angular.module('aktivingatlanApp')
    .factory('PropertySearch', function ($resource) {
        return $resource('api/_search/propertys', {}, {
            'query': {
            	method: 'GET', 
            	isArray: true
            }
        });
    });
