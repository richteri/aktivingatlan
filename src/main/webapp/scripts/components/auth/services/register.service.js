'use strict';

angular.module('aktivingatlanApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


