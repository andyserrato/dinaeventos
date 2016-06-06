

angular.module('dinaeventos').controller('EditRrppMinionController', function($scope, $routeParams, $location, flash, RrppMinionResource , RrppJefeResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.rrppMinion = new RrppMinionResource(self.original);
            RrppJefeResource.queryAll(function(items) {
                $scope.rrppJefeSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idrrppJefe : item.idrrppJefe
                    };
                    var labelObject = {
                        value : item.idrrppJefe,
                        text : item.idrrppJefe
                    };
                    if($scope.rrppMinion.rrppJefe && item.idrrppJefe == $scope.rrppMinion.rrppJefe.idrrppJefe) {
                        $scope.rrppJefeSelection = labelObject;
                        $scope.rrppMinion.rrppJefe = wrappedObject;
                        self.original.rrppJefe = $scope.rrppMinion.rrppJefe;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The rrppMinion could not be found.'});
            $location.path("/RrppMinions");
        };
        RrppMinionResource.get({RrppMinionId:$routeParams.RrppMinionId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.rrppMinion);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The rrppMinion was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.rrppMinion.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/RrppMinions");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The rrppMinion was deleted.'});
            $location.path("/RrppMinions");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.rrppMinion.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("rrppJefeSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.rrppMinion.rrppJefe = {};
            $scope.rrppMinion.rrppJefe.idrrppJefe = selection.value;
        }
    });
    
    $scope.get();
});