'use strict';

angular.module('aktivingatlanApp')
    .factory('errorHandlerInterceptor', function ($q, $rootScope) {
        return {
            'responseError': function (response) {
                if (!(response.status == 401 && response.data.path.indexOf("/api/account") == 0 )){
	                $rootScope.$emit('aktivingatlanApp.httpError', response);
	            }
                return $q.reject(response);
            }
        };
    });