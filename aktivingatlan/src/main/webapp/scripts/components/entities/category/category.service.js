'use strict';

angular.module('aktivingatlanApp')
    .factory('Category', function ($resource, DateUtils) {
        return $resource('api/categorys/:id', {}, {
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
