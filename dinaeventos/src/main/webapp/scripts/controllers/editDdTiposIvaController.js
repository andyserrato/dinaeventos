

angular.module('dinaeventos').controller('EditDdTiposIvaController', function($scope, $routeParams, $location, flash, DdTiposIvaResource , EntradaResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.ddTiposIva = new DdTiposIvaResource(self.original);
            EntradaResource.queryAll(function(items) {
                $scope.entradasSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        identrada : item.identrada
                    };
                    var labelObject = {
                        value : item.identrada,
                        text : item.identrada
                    };
                    if($scope.ddTiposIva.entradas){
                        $.each($scope.ddTiposIva.entradas, function(idx, element) {
                            if(item.identrada == element.identrada) {
                                $scope.entradasSelection.push(labelObject);
                                $scope.ddTiposIva.entradas.push(wrappedObject);
                            }
                        });
                        self.original.entradas = $scope.ddTiposIva.entradas;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The ddTiposIva could not be found.'});
            $location.path("/DdTiposIvas");
        };
        DdTiposIvaResource.get({DdTiposIvaId:$routeParams.DdTiposIvaId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.ddTiposIva);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The ddTiposIva was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.ddTiposIva.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/DdTiposIvas");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The ddTiposIva was deleted.'});
            $location.path("/DdTiposIvas");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.ddTiposIva.$remove(successCallback, errorCallback);
    };
    
    $scope.entradasSelection = $scope.entradasSelection || [];
    $scope.$watch("entradasSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.ddTiposIva) {
            $scope.ddTiposIva.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.ddTiposIva.entradas.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});