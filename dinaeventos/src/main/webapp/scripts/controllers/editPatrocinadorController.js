

angular.module('dinaeventos').controller('EditPatrocinadorController', function($scope, $routeParams, $location, flash, PatrocinadorResource , GlobalCodigopostalResource, EventoResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.patrocinador = new PatrocinadorResource(self.original);
            GlobalCodigopostalResource.queryAll(function(items) {
                $scope.globalCodigopostalSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idcodigopostal : item.idcodigopostal
                    };
                    var labelObject = {
                        value : item.idcodigopostal,
                        text : item.idcodigopostal
                    };
                    if($scope.patrocinador.globalCodigopostal && item.idcodigopostal == $scope.patrocinador.globalCodigopostal.idcodigopostal) {
                        $scope.globalCodigopostalSelection = labelObject;
                        $scope.patrocinador.globalCodigopostal = wrappedObject;
                        self.original.globalCodigopostal = $scope.patrocinador.globalCodigopostal;
                    }
                    return labelObject;
                });
            });
            EventoResource.queryAll(function(items) {
                $scope.eventosSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idevento : item.idevento
                    };
                    var labelObject = {
                        value : item.idevento,
                        text : item.idevento
                    };
                    if($scope.patrocinador.eventos){
                        $.each($scope.patrocinador.eventos, function(idx, element) {
                            if(item.idevento == element.idevento) {
                                $scope.eventosSelection.push(labelObject);
                                $scope.patrocinador.eventos.push(wrappedObject);
                            }
                        });
                        self.original.eventos = $scope.patrocinador.eventos;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The patrocinador could not be found.'});
            $location.path("/Patrocinadors");
        };
        PatrocinadorResource.get({PatrocinadorId:$routeParams.PatrocinadorId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.patrocinador);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The patrocinador was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.patrocinador.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Patrocinadors");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The patrocinador was deleted.'});
            $location.path("/Patrocinadors");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.patrocinador.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("globalCodigopostalSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.patrocinador.globalCodigopostal = {};
            $scope.patrocinador.globalCodigopostal.idcodigopostal = selection.value;
        }
    });
    $scope.eventosSelection = $scope.eventosSelection || [];
    $scope.$watch("eventosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.patrocinador) {
            $scope.patrocinador.eventos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idevento = selectedItem.value;
                $scope.patrocinador.eventos.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});