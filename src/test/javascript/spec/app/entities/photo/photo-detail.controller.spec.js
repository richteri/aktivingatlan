'use strict';

describe('Photo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPhoto, MockProperty;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPhoto = jasmine.createSpy('MockPhoto');
        MockProperty = jasmine.createSpy('MockProperty');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Photo': MockPhoto,
            'Property': MockProperty
        };
        createController = function() {
            $injector.get('$controller')("PhotoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'aktivingatlanApp:photoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
