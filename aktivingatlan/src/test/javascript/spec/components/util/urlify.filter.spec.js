'use strict';

describe('Urlify Filter Tests ', function () {

    beforeEach(module('aktivingatlanApp'));

    var filter;
    
    describe('urlifyFilter', function () {
	  beforeEach(inject(function ($filter) {
	    filter = $filter('urlify');
	  }));

	  it('should return the correct representation of the input', function() {
		    expect(filter("kajsdhgf kajshdfgkajhsd gf.jpg")).toEqual("kajsdhgf-kajshdfgkajhsd-gf.jpg");
		    expect(filter("ÓÜÖÚŐŰÁÉÍ óüöúőűáéí.jpg")).toEqual("ouououaei-ouououaei.jpg");
	  });
    });
});
