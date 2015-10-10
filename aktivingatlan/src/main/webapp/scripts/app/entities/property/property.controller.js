'use strict';

angular.module('aktivingatlanApp')
    .controller('PropertyController', ['$scope', '$state', '$filter', 'Property', 'User', 'Category', 'uiGridConstants', '$translate', 'localStorageService', '$timeout', function ($scope, $state, $filter, Property, User, Category, uiGridConstants, $translate, localStorageService, $timeout) {
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
        
        
		function saveState() {
			var state = $scope.gridApi.saveState.save();
			localStorageService.set('property-grid-state', state);
		}
		
		function restoreState() {
			$timeout(function() {
				var state = localStorageService.get('property-grid-state');
				if (state) $scope.gridApi.saveState.restore($scope, state);
			});
		}
		
		$scope.resetState = function() {
			localStorageService.remove('property-grid-state');
			$state.reload('property');
		};
        
        
		 $scope.gridOptions = {
				 	enableGridMenu : true,
				 	enableFiltering : true,
				 	gridMenuTitleFilter : $translate,
				 	enableColumnResizing: true,

				 	data : $scope.propertys,
				 	flatEntityAccess: true,
				 	//fastWatch: true,

				 	onRegisterApi : function (gridApi) {
				 		$scope.gridApi = gridApi;

				 		// Setup events so we're notified when grid state changes.
				 		$scope.gridApi.core.on.columnVisibilityChanged($scope, saveState);
				 		$scope.gridApi.core.on.filterChanged($scope, saveState);
				 		$scope.gridApi.core.on.sortChanged($scope, saveState);
				 		$scope.gridApi.colResizable.on.columnSizeChanged($scope, saveState);

				 		// Restore previously saved state.
				 		restoreState();
				 	},

				 	gridMenuCustomItems : [{
				 			title : 'Alaphelyzet',
				 			action : $scope.resetState,
				 			order : 200
				 		}
				 	],
				 	columnDefs : [{
				 			field : 'id',
				 			displayName : '',
				 			enableFiltering : false,
				 			enableSorting : false,
				 			enableHiding : false,
				 			enableColumnMenu : false,
				 			pinnedLeft : true,
				 			width : 82,
				 			//cellTemplate: 'scripts/app/entities/property/property-grid-actions.html',
				 			cellTemplate : '<div class="ui-grid-cell-contents">' +
					 			'<button type="submit" ui-sref="property.detail({id:row.entity.id})" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-eye-open"></span></button>' +
					 			'<button type="submit" ui-sref="property.edit({id:row.entity.id})" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-pencil"></span></button>' +
					 			'<button type="submit" ng-click="grid.appScope.delete(row.entity.id)" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove-circle"></span></button>' +
					 			'</div>'
				 		}, {
				 			field : 'code',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.code',
				 			pinnedLeft : true,
				 			width : 80,
				 			filter : {
				 				type : uiGridConstants.filter.CONTAINS
				 			}
				 		}, {
				 			field : 'userLogin',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.user',
				 			width : 100,
				 			filter : {
				 				type : uiGridConstants.filter.SELECT,
				 				condition : uiGridConstants.filter.EXACT,
				 				selectOptions : $scope.users
				 			}
				 		}, {
				 			field : 'active',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.active',
				 			width : 70,
				 			filter : {
				 				type : uiGridConstants.filter.SELECT,
				 				selectOptions : [{
				 						value : true,
				 						label : 'aktív'
				 					}, {
				 						value : false,
				 						label : 'inaktív'
				 					}
				 				]
				 			}
				 		}, {
				 			field : 'featured',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.featured',
				 			width : 80,
				 			filter : {
				 				type : uiGridConstants.filter.SELECT,
				 				selectOptions : [{
				 						value : true,
				 						label : 'kiemelt'
				 					}, {
				 						value : false,
				 						label : 'nem kiemelt'
				 					}
				 				]
				 			}
				 		}, {
				 			field : 'categoryId',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.category',
				 			cellTemplate : '<div class="ui-grid-cell-contents">{{row.entity.categoryNameHu}}</div>',
				 			width : 120,
				 			filter : {
				 				type : uiGridConstants.filter.SELECT,
				 				condition : uiGridConstants.filter.EXACT,
				 				selectOptions : $scope.categories
				 			}
				 		}, {
				 			field : 'features',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.feature',
				 			cellTemplate : '<div class="ui-grid-cell-contents"><span ng-repeat="feature in row.entity.features">{{feature.nameHu}} </span></div>',
				 			width : 240,
				 			filter: {
				 		          condition: function(searchTerm, features) {
				 		        	  // filtering for multiple features with contains
				 		        	  var words = searchTerm.split(' ');
				 		        	  return $filter('filter')(features, function (feature) {
				 		        		  for (var i=0; i<words.length; i++) {
				 		        			  if (feature.nameHu.toUpperCase().indexOf(words[i].toUpperCase()) > -1) {
				 		        				  return true;
				 		        			  }
				 		        		  }
				 		        		  return false;
				 		        	  }).length >= words.length;
				 		          }
				 		    }
				 		}, {
				 			field : 'cityName',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.city',
				 			width : 120,
				 			filter : {
				 				type : uiGridConstants.filter.CONTAINS
				 			}
				 		}, {
				 			field : 'pracelNumber',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.pracelNumber',
				 			width : 124,
				 			visible: false,
				 			filter : {
				 				type : uiGridConstants.filter.CONTAINS
				 			}
				 		}, {
				 			field : 'descriptionHu',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.descriptionHu',
				 			width : 200,
				 			filter : {
				 				type : uiGridConstants.filter.CONTAINS
				 			}
				 		}, {
				 			field : 'address1',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.address1',
				 			width : 200,
				 			filter : {
				 				type : uiGridConstants.filter.CONTAINS
				 			}
				 		}, {
				 			field : 'forSale',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.forSale',
				 			width : 70,
				 			filter : {
				 				type : uiGridConstants.filter.SELECT,
				 				selectOptions : [{
				 						value : true,
				 						label : 'eladó'
				 					}, {
				 						value : false,
				 						label : 'nem eladó'
				 					}
				 				]
				 			}
				 		}, {
				 			field : 'saleHuf',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.saleHuf',
				 			width : 120,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'forRent',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.forRent',
				 			width : 70,
				 			filter : {
				 				type : uiGridConstants.filter.SELECT,
				 				selectOptions : [{
				 						value : true,
				 						label : 'kiadó'
				 					}, {
				 						value : false,
				 						label : 'nem kiadó'
				 					}
				 				]
				 			}
				 		}, {
				 			field : 'rentHuf',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.rentHuf',
				 			width : 120,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'rentPeakHuf',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.rentPeakHuf',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'forMediumTerm',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.forMediumTerm',
				 			width : 100,
				 			visible: false,
				 			filter : {
				 				type : uiGridConstants.filter.SELECT,
				 				selectOptions : [{
				 						value : true,
				 						label : 'kiadó'
				 					}, {
				 						value : false,
				 						label : 'nem kiadó'
				 					}
				 				]
				 			}
				 		}, {
				 			field : 'mediumTermHuf',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.mediumTermHuf',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'forLongTerm',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.forLongTerm',
				 			width : 100,
				 			visible: false,
				 			filter : {
				 				type : uiGridConstants.filter.SELECT,
				 				selectOptions : [{
				 						value : true,
				 						label : 'kiadó'
				 					}, {
				 						value : false,
				 						label : 'nem kiadó'
				 					}
				 				]
				 			}
				 		}, {
				 			field : 'longTermHuf',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.longTermHuf',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'room',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.room',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'halfRoom',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.halfRoom',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'floorArea',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.floorArea',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'floor',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.floor',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'parcelArea',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.parcelArea',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'kitchen',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.kitchen',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'livingroom',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.livingroom',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'bathroom',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.bathroom',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'toilet',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.toilet',
				 			width : 120,
				 			visible: false,
				 			filters : [{
				 					condition : uiGridConstants.filter.GREATER_THAN,
				 					placeholder : 'tól'
				 				}, {
				 					condition : uiGridConstants.filter.LESS_THAN,
				 					placeholder : 'ig'
				 				}
				 			]
				 		}, {
				 			field : 'furnished',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.furnished',
				 			width : 100,
				 			visible: false,
				 			filter : {
				 				type : uiGridConstants.filter.SELECT,
				 				selectOptions : [{
				 						value : true,
				 						label : 'kiadó'
				 					}, {
				 						value : false,
				 						label : 'nem kiadó'
				 					}
				 				]
				 			}
				 		}, {
				 			field : 'createdDate',
				 			headerCellFilter : 'translate',
				 			displayName : 'aktivingatlanApp.property.createdDate',
				 			cellFilter: 'date:"yyyy.MM.dd"',
				 			width : 120,
				 			filters : [{
				 					condition : function(searchTerm, value) {
				 						return parseDate(searchTerm) <= new Date(value).getTime();
				 					},
				 					placeholder : 'tól'
				 				}, {
				 					condition : function(searchTerm, value) {
				 						return parseDate(searchTerm) >= new Date(value).getTime();
				 					},
				 					placeholder : 'ig'
				 				}
				 			]
				 		}
				 	]
				 };
		 
		 function parseDate(searchTerm) {
			var parts = searchTerm.split('\\.');
			switch (parts.length) {
			case 1:
				return new Date(parts[0], 0, 1).getTime();
			case 2:
				return new Date(parts[0], parts[1]-1, 1).getTime();
			default:
				return new Date(parts[0], parts[1]-1, parts[2]).getTime();
			}
		 }
        
    }]);
