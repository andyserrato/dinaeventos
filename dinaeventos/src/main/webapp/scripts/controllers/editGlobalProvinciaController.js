

angular.module('dinaeventos').controller('EditGlobalProvinciaController', function($scope, $routeParams, $location, flash, GlobalProvinciaResource , GlobalCodigopostalResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.globalProvincia = new GlobalProvinciaResource(self.original);
            GlobalCodigopostalResource.queryAll(function(items) {
                $scope.globalCodigopostalsSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idcodigopostal : item.idcodigopostal
                    };
                    var labelObject = {
                        value : item.idcodigopostal,
                        text : item.idcodigopostal
                    };
                    if($scope.globalProvincia.globalCodigopostals){
                        $.each($scope.globalProvincia.globalCodigopostals, function(idx, element) {
                            if(item.idcodigopostal == element.idcodigopostal) {
                                $scope.globalCodigopostalsSelection.push(labelObject);
                                $scope.globalProvincia.globalCodigopostals.push(wrappedObject);
                            }
                        });
                        self.original.globalCodigopostals = $scope.globalProvincia.globalCodigopostals;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The globalProvincia could not be found.'});
            $location.path("/GlobalProvincia");
        };
        GlobalProvinciaResource.get({GlobalProvinciaId:$routeParams.GlobalProvinciaId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.globalProvincia);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The globalProvincia was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.globalProvincia.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/GlobalProvincia");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The globalProvincia was deleted.'});
            $location.path("/GlobalProvincia");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.globalProvincia.$remove(successCallback, errorCallback);
    };
    
    $scope.globalCodigopostalsSelection = $scope.globalCodigopostalsSelection || [];
    $scope.$watch("globalCodigopostalsSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.globalProvincia) {
            $scope.globalProvincia.globalCodigopostals = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idcodigopostal = selectedItem.value;
                $scope.globalProvincia.globalCodigopostals.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});