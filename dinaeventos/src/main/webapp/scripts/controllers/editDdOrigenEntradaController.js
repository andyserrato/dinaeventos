

angular.module('dinaeventos').controller('EditDdOrigenEntradaController', function($scope, $routeParams, $location, flash, DdOrigenEntradaResource , EntradaResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.ddOrigenEntrada = new DdOrigenEntradaResource(self.original);
            EntradaResource.queryAll(function(items) {
                $scope.entradasSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        identrada : item.identrada
                    };
                    var labelObject = {
                        value : item.identrada,
                        text : item.identrada
                    };
                    if($scope.ddOrigenEntrada.entradas){
                        $.each($scope.ddOrigenEntrada.entradas, function(idx, element) {
                            if(item.identrada == element.identrada) {
                                $scope.entradasSelection.push(labelObject);
                                $scope.ddOrigenEntrada.entradas.push(wrappedObject);
                            }
                        });
                        self.original.entradas = $scope.ddOrigenEntrada.entradas;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The ddOrigenEntrada could not be found.'});
            $location.path("/DdOrigenEntradas");
        };
        DdOrigenEntradaResource.get({DdOrigenEntradaId:$routeParams.DdOrigenEntradaId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.ddOrigenEntrada);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The ddOrigenEntrada was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.ddOrigenEntrada.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/DdOrigenEntradas");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The ddOrigenEntrada was deleted.'});
            $location.path("/DdOrigenEntradas");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.ddOrigenEntrada.$remove(successCallback, errorCallback);
    };
    
    $scope.entradasSelection = $scope.entradasSelection || [];
    $scope.$watch("entradasSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.ddOrigenEntrada) {
            $scope.ddOrigenEntrada.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.ddOrigenEntrada.entradas.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});