'use strict';

angular.module('aktivingatlanApp')
	.directive('backButton', ['$window', function($window) {
        return {
            restrict: 'A',
            link: function (scope, elem, attrs) {
                elem.bind('click', function () {
                    $window.history.back();
                    scope.$apply();
                });
            }
        };
    }]);