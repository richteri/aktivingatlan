'use strict';

angular.module('aktivingatlanApp')
    .controller('PropertyController', ['$scope', 'Property', 'User', 'Category', 'uiGridConstants', '$translate', function ($scope, Property, User, Category, uiGridConstants, $translate) {
        $scope.propertys = [];
        $scope.users = [];
        $scope.categories = [];
        
        User.query(function (result) {
        	angular.forEach(result, function(value) {
        		$scope.users.push({value: value.login, label: value.firstName + ' ' + value.lastName});
        	});
        });
        
        Category.query(function (result) {
        	angular.forEach(result, function(value) {
        		$scope.categories.push({value: value.id, label: value.nameHu});
        	});
        });
        
        $scope.page = 1;
        
        $scope.loadAll = function() {
            Property.query(function(result, headers) {
                $scope.propertys = result;
                $scope.gridOptions.data = result;
            });
        };
        
        $scope.loadAll();

        $scope.delete = function (id) {
            Property.get({id: id}, function(result) {
                $scope.property = result;
                $('#deletePropertyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Property.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePropertyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.property = {code: null, descriptionHu: null, descriptionEn: null, descriptionDe: null, room: null, halfRoom: null, floorArea: null, parcelArea: null, pracelNumber: null, address1: null, address2: null, active: null, kitchen: null, livingroom: null, floor: null, bathroom: null, toilet: null, furnished: null, forSale: null, saleHuf: null, saleEur: null, forRent: null, rentHuf: null, rentEur: null, rentPeakHuf: null, rentPeakEur: null, forMediumTerm: null, mediumTermHuf: null, mediumTermEur: null, forLongTerm: null, longTermHuf: null, longTermEur: null, featured: null, id: null};
        };
        
        
        
        $scope.gridOptions = {
        	    enableFiltering: true,
        	    onRegisterApi: function(gridApi) {
        	      $scope.gridApi = gridApi;
        	    },
        	    data: $scope.propertys,
        	    columnDefs: [{
        	    		field: 'id',
        	    		displayName: '',
        	    		enableFiltering: false,
        	    		enableSorting: false,
        	    		enableHiding: false,
        	    		enableColumnMenu: false,
        	    		pinnedLeft: true,
        	    		width:82,
        	    		cellTemplate: '<div class="ui-grid-cell-contents">' + 
	        	    		'<button type="submit" ui-sref="property.detail({id:row.entity.id})" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-eye-open"></span></button>' +
	                        '<button type="submit" ui-sref="property.edit({id:row.entity.id})" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-pencil"></span></button>' +
	                        '<button type="submit" ng-click="grid.appScope.delete(row.entity.id)" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove-circle"></span></button>' +
	                        '</div>'
        	    	}, { 
	        	    	field: 'code',
	        	    	headerCellFilter: 'translate',
	        	    	displayName: 'aktivingatlanApp.property.code',
	        	    	pinnedLeft: true,
	        	    	filter: {
	        	    		type: uiGridConstants.filter.CONTAINS
	        	    	}
	        	    }, {
	        	    	field: 'userLogin', 
	        	    	filter: {
							type: uiGridConstants.filter.SELECT,
							condition: uiGridConstants.filter.EXACT,
							selectOptions: $scope.users
						}
					}, {
						field: 'active',
						filter: {
							type: uiGridConstants.filter.SELECT,
							selectOptions: [ {value:true, label:'active'}, {value:false, label:'inactive'} ]
						}
					}, {
						field: 'featured',
						filter: {
							type: uiGridConstants.filter.SELECT,
							selectOptions: [ {value:true, label:'featured'}, {value:false, label:'regular'} ]
						}
					}, {
						field: 'categoryId',
						cellTemplate: '<div class="ui-grid-cell-contents">{{row.entity.categoryNameHu}}</div>',
						filter: {
							type: uiGridConstants.filter.SELECT,
							condition: uiGridConstants.filter.EXACT,
							selectOptions: $scope.categories
						}
					}, {
						field: 'cityName',
						filter: {
							type: uiGridConstants.filter.CONTAINS
						}
					}
        	    ]
        	  };
        
        
        
    }]);
