'use strict';

angular.module('aktivingatlanApp')
    .factory('Apartment', function ($resource, DateUtils) {
        return $resource('api/apartments/:id', {}, {
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
