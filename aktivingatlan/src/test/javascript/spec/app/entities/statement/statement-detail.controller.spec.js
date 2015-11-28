'use strict';

describe('Statement Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockStatement, MockClient, MockProperty;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockStatement = jasmine.createSpy('MockStatement');
        MockClient = jasmine.createSpy('MockClient');
        MockProperty = jasmine.createSpy('MockProperty');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Statement': MockStatement,
            'Client': MockClient,
            'Property': MockProperty
        };
        createController = function() {
            $injector.get('$controller')("StatementDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'aktivingatlanApp:statementUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
