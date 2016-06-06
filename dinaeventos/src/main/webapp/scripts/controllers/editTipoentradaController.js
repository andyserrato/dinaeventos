

angular.module('dinaeventos').controller('EditTipoentradaController', function($scope, $routeParams, $location, flash, TipoentradaResource , EntradaResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.tipoentrada = new TipoentradaResource(self.original);
            EntradaResource.queryAll(function(items) {
                $scope.entradasSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        identrada : item.identrada
                    };
                    var labelObject = {
                        value : item.identrada,
                        text : item.identrada
                    };
                    if($scope.tipoentrada.entradas){
                        $.each($scope.tipoentrada.entradas, function(idx, element) {
                            if(item.identrada == element.identrada) {
                                $scope.entradasSelection.push(labelObject);
                                $scope.tipoentrada.entradas.push(wrappedObject);
                            }
                        });
                        self.original.entradas = $scope.tipoentrada.entradas;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The tipoentrada could not be found.'});
            $location.path("/Tipoentradas");
        };
        TipoentradaResource.get({TipoentradaId:$routeParams.TipoentradaId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.tipoentrada);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The tipoentrada was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.tipoentrada.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Tipoentradas");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The tipoentrada was deleted.'});
            $location.path("/Tipoentradas");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.tipoentrada.$remove(successCallback, errorCallback);
    };
    
    $scope.entradasSelection = $scope.entradasSelection || [];
    $scope.$watch("entradasSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.tipoentrada) {
            $scope.tipoentrada.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.tipoentrada.entradas.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});