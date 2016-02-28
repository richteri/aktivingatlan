'use strict';

angular.module('aktivingatlanApp')
    .factory('PropertyPublic', ['$http', '$httpParamSerializer', function ($http, $httpParamSerializer) {
        var apiUrl = 'api/_public/propertys';

        var PropertyPublic = {};

        PropertyPublic.findFeatured = function () {
            return $http.get(apiUrl + '/findFeatured/');
        };
        
        PropertyPublic.find = function (search) {
            return $http.get(apiUrl + '/find?' + $httpParamSerializer(search));
        };

        return PropertyPublic;
    }]);
