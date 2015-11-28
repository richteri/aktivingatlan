'use strict';

describe('Property Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProperty, MockCategory, MockPhoto, MockStatement, MockFeature, MockOwnership, MockCity, MockContract, MockUser, MockApartment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProperty = jasmine.createSpy('MockProperty');
        MockCategory = jasmine.createSpy('MockCategory');
        MockPhoto = jasmine.createSpy('MockPhoto');
        MockStatement = jasmine.createSpy('MockStatement');
        MockFeature = jasmine.createSpy('MockFeature');
        MockOwnership = jasmine.createSpy('MockOwnership');
        MockCity = jasmine.createSpy('MockCity');
        MockContract = jasmine.createSpy('MockContract');
        MockUser = jasmine.createSpy('MockUser');
        MockApartment = jasmine.createSpy('MockApartment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Property': MockProperty,
            'Category': MockCategory,
            'Photo': MockPhoto,
            'Statement': MockStatement,
            'Feature': MockFeature,
            'Ownership': MockOwnership,
            'City': MockCity,
            'Contract': MockContract,
            'User': MockUser,
            'Apartment': MockApartment
        };
        createController = function() {
            $injector.get('$controller')("PropertyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'aktivingatlanApp:propertyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
