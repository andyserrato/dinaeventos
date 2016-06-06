
angular.module('dinaeventos').controller('NewEventoController', function ($scope, $location, locationParser, flash, EventoResource , DdTipoEventoResource, GlobalCodigopostalResource, OrganizadorResource, RrppJefeResource, EntradaResource, PatrocinadorResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.evento = $scope.evento || {};
    
    $scope.ddTipoEventoList = DdTipoEventoResource.queryAll(function(items){
        $scope.ddTipoEventoSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idTipoEvento,
                text : item.idTipoEvento
            });
        });
    });
    $scope.$watch("ddTipoEventoSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.evento.ddTipoEvento = {};
            $scope.evento.ddTipoEvento.idTipoEvento = selection.value;
        }
    });
    
    $scope.globalCodigopostalList = GlobalCodigopostalResource.queryAll(function(items){
        $scope.globalCodigopostalSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idcodigopostal,
                text : item.idcodigopostal
            });
        });
    });
    $scope.$watch("globalCodigopostalSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.evento.globalCodigopostal = {};
            $scope.evento.globalCodigopostal.idcodigopostal = selection.value;
        }
    });
    
    $scope.organizadorList = OrganizadorResource.queryAll(function(items){
        $scope.organizadorSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idorganizador,
                text : item.idorganizador
            });
        });
    });
    $scope.$watch("organizadorSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.evento.organizador = {};
            $scope.evento.organizador.idorganizador = selection.value;
        }
    });
    
    $scope.rrppJefesList = RrppJefeResource.queryAll(function(items){
        $scope.rrppJefesSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idrrppJefe,
                text : item.idrrppJefe
            });
        });
    });
    $scope.$watch("rrppJefesSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.evento.rrppJefes = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idrrppJefe = selectedItem.value;
                $scope.evento.rrppJefes.push(collectionItem);
            });
        }
    });

    $scope.entradasList = EntradaResource.queryAll(function(items){
        $scope.entradasSelectionList = $.map(items, function(item) {
            return ( {
                value : item.identrada,
                text : item.identrada
            });
        });
    });
    $scope.$watch("entradasSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.evento.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.evento.entradas.push(collectionItem);
            });
        }
    });

    $scope.patrocinadorsList = PatrocinadorResource.queryAll(function(items){
        $scope.patrocinadorsSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idpatrocinador,
                text : item.idpatrocinador
            });
        });
    });
    $scope.$watch("patrocinadorsSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.evento.patrocinadors = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idpatrocinador = selectedItem.value;
                $scope.evento.patrocinadors.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The evento was created successfully.'});
            $location.path('/Eventos');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        EventoResource.save($scope.evento, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Eventos");
    };
});