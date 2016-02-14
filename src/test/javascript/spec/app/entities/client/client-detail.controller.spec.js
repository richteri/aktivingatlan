'use strict';

describe('Client Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockClient, MockOwnership, MockStatement, MockContract;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockClient = jasmine.createSpy('MockClient');
        MockOwnership = jasmine.createSpy('MockOwnership');
        MockStatement = jasmine.createSpy('MockStatement');
        MockContract = jasmine.createSpy('MockContract');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Client': MockClient,
            'Ownership': MockOwnership,
            'Statement': MockStatement,
            'Contract': MockContract
        };
        createController = function() {
            $injector.get('$controller')("ClientDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'aktivingatlanApp:clientUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
