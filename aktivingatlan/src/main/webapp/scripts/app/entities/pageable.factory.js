'use strict';

angular.module('aktivingatlanApp')
    .factory('Pageable', ['localStorageService', function (localStorageService) {

    	var DEF_PROPERTY = "id";
    	var DEF_DIRECTION = "ASC";
    	var DEF_PER_PAGE = 20;

        // Define the constructor function.
        function Pageable(scope, key) {
        	// Set defaults
            this.property = DEF_PROPERTY;
            this.direction = DEF_DIRECTION;
            this.page = 1;
            this.query = "";
            this.per_page = DEF_PER_PAGE;
            this.filtered = false;

            // Load persisted state and bind to provided scope
        	localStorageService.bind(scope, key, this, scope.toState.url);
        }

        // Define the "instance" methods using the prototype
        // and standard prototypal inheritance.
        Pageable.prototype = {
            sortBy: function (property) {
                if (this.property != property) {
                    this.property = property;
                    this.direction = "ASC";
                } else if (this.direction == "ASC") {
                    this.direction = "DESC";
                } else {
                    this.property = DEF_PROPERTY;
                    this.direction = DEF_DIRECTION;
                }
            }
        };

        // Return constructor - this is what defines the actual
        // injectable in the DI framework.
        return( Pageable );
    }]);
