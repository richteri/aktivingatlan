 'use strict';

angular.module('aktivingatlanApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-aktivingatlanApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-aktivingatlanApp-params')});
                }
                return response;
            }
        };
    });
