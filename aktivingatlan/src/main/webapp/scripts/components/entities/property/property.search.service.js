'use strict';

angular.module('aktivingatlanApp')
    .factory('PropertySearch', ['$http', function ($http) {
        var apiUrl = 'api/_search/propertys';

        var PropertySearch = {};

        PropertySearch.findByCode = function (code) {
            return $http.get(apiUrl + '/findByCode/' + code);
        }

        return PropertySearch;
    }]);
