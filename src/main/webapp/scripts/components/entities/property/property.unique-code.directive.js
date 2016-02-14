'use strict';

angular.module('aktivingatlanApp')
	.directive('uniquePropertyCode', ['PropertySearch', function (PropertySearch) {
	    return {
	        restrict: 'A',
	        require: 'ngModel',
	        link: function (scope, element, attrs, ngModel) {
	        	element.bind('focus', function(e) {
	        		// Save the original value to know when it was not changed
	        		if (angular.isUndefined(scope.originalCodeValue)) {
	        			scope.originalCodeValue = ngModel.$modelValue;
	        		}
	        	});
	            element.bind('blur', function (e) {
	                if (!ngModel || !element.val()) return;
	                var currentValue = element.val();
	                
	                // If the value was reset, set validity to true
	                if (scope.originalCodeValue == currentValue) {
	                	ngModel.$setValidity('unique', true);
	                	return;
	                }
	                
	                console.log('blur', ngModel.$modelValue, ngModel.$viewValue, scope.originalCodeValue);
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