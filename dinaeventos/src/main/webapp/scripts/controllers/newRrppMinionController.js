
angular.module('dinaeventos').controller('NewRrppMinionController', function ($scope, $location, locationParser, flash, RrppMinionResource , RrppJefeResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.rrppMinion = $scope.rrppMinion || {};
    
    $scope.rrppJefeList = RrppJefeResource.queryAll(function(items){
        $scope.rrppJefeSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idrrppJefe,
                text : item.idrrppJefe
            });
        });
    });
    $scope.$watch("rrppJefeSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.rrppMinion.rrppJefe = {};
            $scope.rrppMinion.rrppJefe.idrrppJefe = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The rrppMinion was created successfully.'});
            $location.path('/RrppMinions');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        RrppMinionResource.save($scope.rrppMinion, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/RrppMinions");
    };
});