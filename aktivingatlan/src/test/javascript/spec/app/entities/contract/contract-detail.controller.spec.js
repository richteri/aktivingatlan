'use strict';

describe('Contract Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockContract, MockProperty, MockClient;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockContract = jasmine.createSpy('MockContract');
        MockProperty = jasmine.createSpy('MockProperty');
        MockClient = jasmine.createSpy('MockClient');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Contract': MockContract,
            'Property': MockProperty,
            'Client': MockClient
        };
        createController = function() {
            $injector.get('$controller')("ContractDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'aktivingatlanApp:contractUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
