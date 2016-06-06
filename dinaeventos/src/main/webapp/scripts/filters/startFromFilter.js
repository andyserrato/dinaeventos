'use strict';

angular.module('dinaeventos').filter('startFrom', function() {
    return function(input, start) {
        start = +start; //parse to int
        return input.slice(start);
    };
});