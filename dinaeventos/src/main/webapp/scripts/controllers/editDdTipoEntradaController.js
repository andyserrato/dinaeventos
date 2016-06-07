

angular.module('dinaeventos').controller('EditDdTipoEntradaController', function($scope, $routeParams, $location, flash, DdTipoEntradaResource , EntradaResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.ddTipoEntrada = new DdTipoEntradaResource(self.original);
            EntradaResource.queryAll(function(items) {
                $scope.entradasSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        identrada : item.identrada
                    };
                    var labelObject = {
                        value : item.identrada,
                        text : item.identrada
                    };
                    if($scope.ddTipoEntrada.entradas){
                        $.each($scope.ddTipoEntrada.entradas, function(idx, element) {
                            if(item.identrada == element.identrada) {
                                $scope.entradasSelection.push(labelObject);
                                $scope.ddTipoEntrada.entradas.push(wrappedObject);
                            }
                        });
                        self.original.entradas = $scope.ddTipoEntrada.entradas;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The ddTipoEntrada could not be found.'});
            $location.path("/DdTipoEntradas");
        };
        DdTipoEntradaResource.get({DdTipoEntradaId:$routeParams.DdTipoEntradaId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.ddTipoEntrada);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The ddTipoEntrada was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.ddTipoEntrada.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/DdTipoEntradas");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The ddTipoEntrada was deleted.'});
            $location.path("/DdTipoEntradas");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.ddTipoEntrada.$remove(successCallback, errorCallback);
    };
    
    $scope.entradasSelection = $scope.entradasSelection || [];
    $scope.$watch("entradasSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.ddTipoEntrada) {
            $scope.ddTipoEntrada.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.ddTipoEntrada.entradas.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});