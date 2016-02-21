/* globals $ */
'use strict';

angular.module('aktivingatlanApp')
    .directive('citySearchTypeahead', ['$templateRequest', '$compile', 'CitySearch', function($templateRequest, $compile, CitySearch) {
        return {
        	restrict: 'E',
        	scope: {
                ngModel: '=',
                idModel: '='
            },
            compile: function(element, attrs) {
                return function(scope, element, attrs) {
                	scope.format = function (entity) {
                    	if (angular.isObject(entity)) {
                    		return entity.zip + ' ' + entity.name;
                    	} else {
                    		return entity;
                    	}
                    };
                    
                    scope.find = function (query) {
                    	return CitySearch.query({
                            query: query
                        }).$promise;
                    };
                    
                    scope.onSelect = function($item, $model, $label) {
                    	scope.idModel = $item.id;
                    };
                	
                    $templateRequest('scripts/components/entities/city/city.search.directive.html')
                    	.then(function (html) {
	                        var template = angular.element(html);
	                        element.html(template);
	                        $compile(element.contents())(scope);
                     });
                   
                };
            }
        };
    }]);
