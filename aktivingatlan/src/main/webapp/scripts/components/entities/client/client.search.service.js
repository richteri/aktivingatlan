'use strict';

angular.module('aktivingatlanApp')
    .factory('ClientSearch', ['$http', function ($http) {
        var apiUrl = 'api/_search/clients';

        var ClientSearch = {};

        ClientSearch.findByAny = function (query) {
            return $http.get(apiUrl + '/findByAny/' + query).then(function (response) {
                return response.data;
            });
        };

        return ClientSearch;
    }]);
