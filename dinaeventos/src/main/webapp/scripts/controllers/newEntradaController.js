
angular.module('dinaeventos').controller('NewEntradaController', function ($scope, $location, locationParser, flash, EntradaResource , DdFormapagoResource, DdOrigenEntradaResource, DdTipoEntradaResource, DdTiposIvaResource, EventoResource, UsuarioResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.entrada = $scope.entrada || {};
    
    $scope.ddFormapagoList = DdFormapagoResource.queryAll(function(items){
        $scope.ddFormapagoSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idformapago,
                text : item.idformapago
            });
        });
    });
    $scope.$watch("ddFormapagoSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.entrada.ddFormapago = {};
            $scope.entrada.ddFormapago.idformapago = selection.value;
        }
    });
    
    $scope.ddOrigenEntradaList = DdOrigenEntradaResource.queryAll(function(items){
        $scope.ddOrigenEntradaSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idorigenEntrada,
                text : item.idorigenEntrada
            });
        });
    });
    $scope.$watch("ddOrigenEntradaSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.entrada.ddOrigenEntrada = {};
            $scope.entrada.ddOrigenEntrada.idorigenEntrada = selection.value;
        }
    });
    
    $scope.ddTipoEntradaList = DdTipoEntradaResource.queryAll(function(items){
        $scope.ddTipoEntradaSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idtipoentrada,
                text : item.idtipoentrada
            });
        });
    });
    $scope.$watch("ddTipoEntradaSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.entrada.ddTipoEntrada = {};
            $scope.entrada.ddTipoEntrada.idtipoentrada = selection.value;
        }
    });
    
    $scope.ddTiposIvaList = DdTiposIvaResource.queryAll(function(items){
        $scope.ddTiposIvaSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idtipoiva,
                text : item.idtipoiva
            });
        });
    });
    $scope.$watch("ddTiposIvaSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.entrada.ddTiposIva = {};
            $scope.entrada.ddTiposIva.idtipoiva = selection.value;
        }
    });
    
    $scope.eventoList = EventoResource.queryAll(function(items){
        $scope.eventoSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idevento,
                text : item.idevento
            });
        });
    });
    $scope.$watch("eventoSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.entrada.evento = {};
            $scope.entrada.evento.idevento = selection.value;
        }
    });
    
    $scope.usuarioList = UsuarioResource.queryAll(function(items){
        $scope.usuarioSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idusuario,
                text : item.idusuario
            });
        });
    });
    $scope.$watch("usuarioSelection", function(selection) {
        if ( typeof selection != 'undefined') {
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

    $scope.dentrofueraList = [
        "true",
        "false"
    ];

    $scope.vendidaList = [
        "true",
        "false"
    ];


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The entrada was created successfully.'});
            $location.path('/Entradas');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        EntradaResource.save($scope.entrada, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Entradas");
    };
});