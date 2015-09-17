'use strict';

angular.module('aktivingatlanApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
