

angular.module('dinaeventos').controller('EditEventoController', function($scope, $routeParams, $location, flash, EventoResource , DdTipoEventoResource, GlobalCodigopostalResource, OrganizadorResource, RrppJefeResource, EntradaResource, PatrocinadorResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.evento = new EventoResource(self.original);
            DdTipoEventoResource.queryAll(function(items) {
                $scope.ddTipoEventoSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idTipoEvento : item.idTipoEvento
                    };
                    var labelObject = {
                        value : item.idTipoEvento,
                        text : item.idTipoEvento
                    };
                    if($scope.evento.ddTipoEvento && item.idTipoEvento == $scope.evento.ddTipoEvento.idTipoEvento) {
                        $scope.ddTipoEventoSelection = labelObject;
                        $scope.evento.ddTipoEvento = wrappedObject;
                        self.original.ddTipoEvento = $scope.evento.ddTipoEvento;
                    }
                    return labelObject;
                });
            });
            GlobalCodigopostalResource.queryAll(function(items) {
                $scope.globalCodigopostalSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idcodigopostal : item.idcodigopostal
                    };
                    var labelObject = {
                        value : item.idcodigopostal,
                        text : item.idcodigopostal
                    };
                    if($scope.evento.globalCodigopostal && item.idcodigopostal == $scope.evento.globalCodigopostal.idcodigopostal) {
                        $scope.globalCodigopostalSelection = labelObject;
                        $scope.evento.globalCodigopostal = wrappedObject;
                        self.original.globalCodigopostal = $scope.evento.globalCodigopostal;
                    }
                    return labelObject;
                });
            });
            OrganizadorResource.queryAll(function(items) {
                $scope.organizadorSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idorganizador : item.idorganizador
                    };
                    var labelObject = {
                        value : item.idorganizador,
                        text : item.idorganizador
                    };
                    if($scope.evento.organizador && item.idorganizador == $scope.evento.organizador.idorganizador) {
                        $scope.organizadorSelection = labelObject;
                        $scope.evento.organizador = wrappedObject;
                        self.original.organizador = $scope.evento.organizador;
                    }
                    return labelObject;
                });
            });
            RrppJefeResource.queryAll(function(items) {
                $scope.rrppJefesSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idrrppJefe : item.idrrppJefe
                    };
                    var labelObject = {
                        value : item.idrrppJefe,
                        text : item.idrrppJefe
                    };
                    if($scope.evento.rrppJefes){
                        $.each($scope.evento.rrppJefes, function(idx, element) {
                            if(item.idrrppJefe == element.idrrppJefe) {
                                $scope.rrppJefesSelection.push(labelObject);
                                $scope.evento.rrppJefes.push(wrappedObject);
                            }
                        });
                        self.original.rrppJefes = $scope.evento.rrppJefes;
                    }
                    return labelObject;
                });
            });
            EntradaResource.queryAll(function(items) {
                $scope.entradasSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        identrada : item.identrada
                    };
                    var labelObject = {
                        value : item.identrada,
                        text : item.identrada
                    };
                    if($scope.evento.entradas){
                        $.each($scope.evento.entradas, function(idx, element) {
                            if(item.identrada == element.identrada) {
                                $scope.entradasSelection.push(labelObject);
                                $scope.evento.entradas.push(wrappedObject);
                            }
                        });
                        self.original.entradas = $scope.evento.entradas;
                    }
                    return labelObject;
                });
            });
            PatrocinadorResource.queryAll(function(items) {
                $scope.patrocinadorsSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idpatrocinador : item.idpatrocinador
                    };
                    var labelObject = {
                        value : item.idpatrocinador,
                        text : item.idpatrocinador
                    };
                    if($scope.evento.patrocinadors){
                        $.each($scope.evento.patrocinadors, function(idx, element) {
                            if(item.idpatrocinador == element.idpatrocinador) {
                                $scope.patrocinadorsSelection.push(labelObject);
                                $scope.evento.patrocinadors.push(wrappedObject);
                            }
                        });
                        self.original.patrocinadors = $scope.evento.patrocinadors;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The evento could not be found.'});
            $location.path("/Eventos");
        };
        EventoResource.get({EventoId:$routeParams.EventoId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.evento);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The evento was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.evento.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Eventos");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The evento was deleted.'});
            $location.path("/Eventos");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.evento.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("ddTipoEventoSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.evento.ddTipoEvento = {};
            $scope.evento.ddTipoEvento.idTipoEvento = selection.value;
        }
    });
    $scope.$watch("globalCodigopostalSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.evento.globalCodigopostal = {};
            $scope.evento.globalCodigopostal.idcodigopostal = selection.value;
        }
    });
    $scope.$watch("organizadorSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.evento.organizador = {};
            $scope.evento.organizador.idorganizador = selection.value;
        }
    });
    $scope.rrppJefesSelection = $scope.rrppJefesSelection || [];
    $scope.$watch("rrppJefesSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.evento) {
            $scope.evento.rrppJefes = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idrrppJefe = selectedItem.value;
                $scope.evento.rrppJefes.push(collectionItem);
            });
        }
    });
    $scope.entradasSelection = $scope.entradasSelection || [];
    $scope.$watch("entradasSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.evento) {
            $scope.evento.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.evento.entradas.push(collectionItem);
            });
        }
    });
    $scope.patrocinadorsSelection = $scope.patrocinadorsSelection || [];
    $scope.$watch("patrocinadorsSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.evento) {
            $scope.evento.patrocinadors = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idpatrocinador = selectedItem.value;
                $scope.evento.patrocinadors.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});