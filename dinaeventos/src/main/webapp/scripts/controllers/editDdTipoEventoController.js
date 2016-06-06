

angular.module('dinaeventos').controller('EditDdTipoEventoController', function($scope, $routeParams, $location, flash, DdTipoEventoResource , EventoResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.ddTipoEvento = new DdTipoEventoResource(self.original);
            EventoResource.queryAll(function(items) {
                $scope.eventosSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idevento : item.idevento
                    };
                    var labelObject = {
                        value : item.idevento,
                        text : item.idevento
                    };
                    if($scope.ddTipoEvento.eventos){
                        $.each($scope.ddTipoEvento.eventos, function(idx, element) {
                            if(item.idevento == element.idevento) {
                                $scope.eventosSelection.push(labelObject);
                                $scope.ddTipoEvento.eventos.push(wrappedObject);
                            }
                        });
                        self.original.eventos = $scope.ddTipoEvento.eventos;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The ddTipoEvento could not be found.'});
            $location.path("/DdTipoEventos");
        };
        DdTipoEventoResource.get({DdTipoEventoId:$routeParams.DdTipoEventoId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.ddTipoEvento);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The ddTipoEvento was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.ddTipoEvento.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/DdTipoEventos");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The ddTipoEvento was deleted.'});
            $location.path("/DdTipoEventos");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.ddTipoEvento.$remove(successCallback, errorCallback);
    };
    
    $scope.eventosSelection = $scope.eventosSelection || [];
    $scope.$watch("eventosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.ddTipoEvento) {
            $scope.ddTipoEvento.eventos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idevento = selectedItem.value;
                $scope.ddTipoEvento.eventos.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});