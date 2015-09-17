'use strict';

angular.module('aktivingatlanApp')
    .factory('Property', function ($resource, DateUtils) {
        return $resource('api/propertys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
