
angular.module('dinaeventos').controller('NewGlobalCodigopostalController', function ($scope, $location, locationParser, flash, GlobalCodigopostalResource , GlobalProvinciaResource, PatrocinadorResource, UsuarioResource, EventoResource, OrganizadorResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.globalCodigopostal = $scope.globalCodigopostal || {};
    
    $scope.globalProvinciaList = GlobalProvinciaResource.queryAll(function(items){
        $scope.globalProvinciaSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idprovincia,
                text : item.idprovincia
            });
        });
    });
    $scope.$watch("globalProvinciaSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.globalCodigopostal.globalProvincia = {};
            $scope.globalCodigopostal.globalProvincia.idprovincia = selection.value;
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
            $scope.globalCodigopostal.patrocinadors = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idpatrocinador = selectedItem.value;
                $scope.globalCodigopostal.patrocinadors.push(collectionItem);
            });
        }
    });

    $scope.usuariosList = UsuarioResource.queryAll(function(items){
        $scope.usuariosSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idusuario,
                text : item.idusuario
            });
        });
    });
    $scope.$watch("usuariosSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.globalCodigopostal.usuarios = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idusuario = selectedItem.value;
                $scope.globalCodigopostal.usuarios.push(collectionItem);
            });
        }
    });

    $scope.eventosList = EventoResource.queryAll(function(items){
        $scope.eventosSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idevento,
                text : item.idevento
            });
        });
    });
    $scope.$watch("eventosSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.globalCodigopostal.eventos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idevento = selectedItem.value;
                $scope.globalCodigopostal.eventos.push(collectionItem);
            });
        }
    });

    $scope.organizadorsList = OrganizadorResource.queryAll(function(items){
        $scope.organizadorsSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idorganizador,
                text : item.idorganizador
            });
        });
    });
    $scope.$watch("organizadorsSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.globalCodigopostal.organizadors = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idorganizador = selectedItem.value;
                $scope.globalCodigopostal.organizadors.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The globalCodigopostal was created successfully.'});
            $location.path('/GlobalCodigopostals');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        GlobalCodigopostalResource.save($scope.globalCodigopostal, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/GlobalCodigopostals");
    };
});