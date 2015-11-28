'use strict';

describe('Apartment Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockApartment, MockProperty;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockApartment = jasmine.createSpy('MockApartment');
        MockProperty = jasmine.createSpy('MockProperty');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Apartment': MockApartment,
            'Property': MockProperty
        };
        createController = function() {
            $injector.get('$controller')("ApartmentDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'aktivingatlanApp:apartmentUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
