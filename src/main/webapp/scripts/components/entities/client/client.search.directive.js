/* globals $ */
'use strict';

angular.module('aktivingatlanApp')
    .directive('clientSearchTypeahead', ['$templateRequest', '$compile', 'ClientSearch', function($templateRequest, $compile, ClientSearch) {
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
                    		return entity.name + ' ' + entity.phone1 + ' ' + entity.address1 + ' ' + entity.idNo;
                    	} else {
                    		return entity;
                    	}
                    };
                    
                    scope.find = function (query) {
                    	return ClientSearch.findByAny(query);
                    };
                    
                    scope.onSelect = function($item, $model, $label) {
                    	scope.idModel = $item.id;
                    };
                	
                    $templateRequest('scripts/components/entities/client/client.search.directive.html')
                    	.then(function (html) {
	                        var template = angular.element(html);
	                        element.html(template);
	                        $compile(element.contents())(scope);
                     });
                   
                };
            }
        };
    }]);
