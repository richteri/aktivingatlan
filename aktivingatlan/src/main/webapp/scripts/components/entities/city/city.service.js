'use strict';

angular.module('aktivingatlanApp')
    .factory('City', function ($resource, DateUtils) {
        return $resource('api/citys/:id', {}, {
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
