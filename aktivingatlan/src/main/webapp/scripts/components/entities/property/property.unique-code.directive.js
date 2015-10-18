'use strict';

angular.module('aktivingatlanApp')
	.directive('uniquePropertyCode', ['PropertySearch', function (PropertySearch) {
	    return {
	        restrict: 'A',
	        require: 'ngModel',
	        link: function (scope, element, attrs, ngModel) {
	            element.bind('blur', function (e) {
	                if (!ngModel || !element.val()) return;
	                var currentValue = element.val();
	                PropertySearch.query({query: 'FIND_BY_CODE', param: currentValue},
	                	function (properties) {
	                        //Ensure value that being checked hasn't changed
	                        //since the Ajax call was made
	                        if (currentValue == element.val()) { 
	                            ngModel.$setValidity('unique', properties.length == 0);
	                        }
	                    }, function () {
	                        //Probably want a more robust way to handle an error
	                        //For this demo we'll set unique to true though
	                        ngModel.$setValidity('unique', true);
	                    });
	            });
	        }
	    }
	}]);