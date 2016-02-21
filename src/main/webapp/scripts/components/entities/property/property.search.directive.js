/* globals $ */
'use strict';

angular.module('aktivingatlanApp')
    .directive('propertySearchTypeahead', ['$templateRequest', '$compile', 'PropertySearch', function($templateRequest, $compile, PropertySearch) {
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
                    		return entity.code + ' ' + entity.cityName;
                    	} else {
                    		return entity;
                    	}
                    };
                    
                    scope.find = function (query) {
                    	return PropertySearch.findByCode(query);
                    };
                    
                    scope.onSelect = function($item, $model, $label) {
                    	scope.idModel = $item.id;
                    };
                	
                    $templateRequest('scripts/components/entities/property/property.search.directive.html')
                    	.then(function (html) {
	                        var template = angular.element(html);
	                        element.html(template);
	                        $compile(element.contents())(scope);
                     });
                   
                };
            }
        };
    }]);
