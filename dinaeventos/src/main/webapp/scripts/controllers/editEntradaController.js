

angular.module('dinaeventos').controller('EditEntradaController', function($scope, $routeParams, $location, flash, EntradaResource , DdFormapagoResource, DdOrigenEntradaResource, DdTiposIvaResource, EventoResource, TipoentradaResource, UsuarioResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.entrada = new EntradaResource(self.original);
            DdFormapagoResource.queryAll(function(items) {
                $scope.ddFormapagoSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idformapago : item.idformapago
                    };
                    var labelObject = {
                        value : item.idformapago,
                        text : item.idformapago
                    };
                    if($scope.entrada.ddFormapago && item.idformapago == $scope.entrada.ddFormapago.idformapago) {
                        $scope.ddFormapagoSelection = labelObject;
                        $scope.entrada.ddFormapago = wrappedObject;
                        self.original.ddFormapago = $scope.entrada.ddFormapago;
                    }
                    return labelObject;
                });
            });
            DdOrigenEntradaResource.queryAll(function(items) {
                $scope.ddOrigenEntradaSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idorigenEntrada : item.idorigenEntrada
                    };
                    var labelObject = {
                        value : item.idorigenEntrada,
                        text : item.idorigenEntrada
                    };
                    if($scope.entrada.ddOrigenEntrada && item.idorigenEntrada == $scope.entrada.ddOrigenEntrada.idorigenEntrada) {
                        $scope.ddOrigenEntradaSelection = labelObject;
                        $scope.entrada.ddOrigenEntrada = wrappedObject;
                        self.original.ddOrigenEntrada = $scope.entrada.ddOrigenEntrada;
                    }
                    return labelObject;
                });
            });
            DdTiposIvaResource.queryAll(function(items) {
                $scope.ddTiposIvaSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idtipoiva : item.idtipoiva
                    };
                    var labelObject = {
                        value : item.idtipoiva,
                        text : item.idtipoiva
                    };
                    if($scope.entrada.ddTiposIva && item.idtipoiva == $scope.entrada.ddTiposIva.idtipoiva) {
                        $scope.ddTiposIvaSelection = labelObject;
                        $scope.entrada.ddTiposIva = wrappedObject;
                        self.original.ddTiposIva = $scope.entrada.ddTiposIva;
                    }
                    return labelObject;
                });
            });
            EventoResource.queryAll(function(items) {
                $scope.eventoSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idevento : item.idevento
                    };
                    var labelObject = {
                        value : item.idevento,
                        text : item.idevento
                    };
                    if($scope.entrada.evento && item.idevento == $scope.entrada.evento.idevento) {
                        $scope.eventoSelection = labelObject;
                        $scope.entrada.evento = wrappedObject;
                        self.original.evento = $scope.entrada.evento;
                    }
                    return labelObject;
                });
            });
            TipoentradaResource.queryAll(function(items) {
                $scope.tipoentradaSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idtipoentrada : item.idtipoentrada
                    };
                    var labelObject = {
                        value : item.idtipoentrada,
                        text : item.idtipoentrada
                    };
                    if($scope.entrada.tipoentrada && item.idtipoentrada == $scope.entrada.tipoentrada.idtipoentrada) {
                        $scope.tipoentradaSelection = labelObject;
                        $scope.entrada.tipoentrada = wrappedObject;
                        self.original.tipoentrada = $scope.entrada.tipoentrada;
                    }
                    return labelObject;
                });
            });
            UsuarioResource.queryAll(function(items) {
                $scope.usuarioSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idusuario : item.idusuario
                    };
                    var labelObject = {
                        value : item.idusuario,
                        text : item.idusuario
                    };
                    if($scope.entrada.usuario && item.idusuario == $scope.entrada.usuario.idusuario) {
                        $scope.usuarioSelection = labelObject;
                        $scope.entrada.usuario = wrappedObject;
                        self.original.usuario = $scope.entrada.usuario;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The entrada could not be found.'});
            $location.path("/Entradas");
        };
        EntradaResource.get({EntradaId:$routeParams.EntradaId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.entrada);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The entrada was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.entrada.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Entradas");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The entrada was deleted.'});
            $location.path("/Entradas");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.entrada.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("ddFormapagoSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.entrada.ddFormapago = {};
            $scope.entrada.ddFormapago.idformapago = selection.value;
        }
    });
    $scope.$watch("ddOrigenEntradaSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.entrada.ddOrigenEntrada = {};
            $scope.entrada.ddOrigenEntrada.idorigenEntrada = selection.value;
        }
    });
    $scope.$watch("ddTiposIvaSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.entrada.ddTiposIva = {};
            $scope.entrada.ddTiposIva.idtipoiva = selection.value;
        }
    });
    $scope.$watch("eventoSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.entrada.evento = {};
            $scope.entrada.evento.idevento = selection.value;
        }
    });
    $scope.$watch("tipoentradaSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.entrada.tipoentrada = {};
            $scope.entrada.tipoentrada.idtipoentrada = selection.value;
        }
    });
    $scope.$watch("usuarioSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.entrada.usuario = {};
            $scope.entrada.usuario.idusuario = selection.value;
        }
    });
    $scope.validadaList = [
        "true",
        "false"
    ];
    $scope.ticketgeneradoList = [
        "true",
        "false"
    ];
    $scope.activaList = [
        "true",
        "false"
    ];
    $scope.dentroFueraList = [
        "true",
        "false"
    ];
    $scope.vendidaList = [
        "true",
        "false"
    ];
    
    $scope.get();
});