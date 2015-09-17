'use strict';

angular.module('aktivingatlanApp')
    .factory('Client', function ($resource, DateUtils) {
        return $resource('api/clients/:id', {}, {
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
