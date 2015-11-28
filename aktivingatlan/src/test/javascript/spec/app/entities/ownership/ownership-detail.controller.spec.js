'use strict';

describe('Ownership Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockOwnership, MockProperty, MockClient;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockOwnership = jasmine.createSpy('MockOwnership');
        MockProperty = jasmine.createSpy('MockProperty');
        MockClient = jasmine.createSpy('MockClient');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Ownership': MockOwnership,
            'Property': MockProperty,
            'Client': MockClient
        };
        createController = function() {
            $injector.get('$controller')("OwnershipDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'aktivingatlanApp:ownershipUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
