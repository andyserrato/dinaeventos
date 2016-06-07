
angular.module('dinaeventos').controller('NewUsuarioController', function ($scope, $location, locationParser, flash, UsuarioResource , DdSexoResource, GlobalCodigopostalResource, RedessocialesResource, RolesResource, EntradaResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.usuario = $scope.usuario || {};
    
    $scope.ddSexoList = DdSexoResource.queryAll(function(items){
        $scope.ddSexoSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idsexo,
                text : item.idsexo
            });
        });
    });
    $scope.$watch("ddSexoSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.usuario.ddSexo = {};
            $scope.usuario.ddSexo.idsexo = selection.value;
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
            $scope.usuario.globalCodigopostal = {};
            $scope.usuario.globalCodigopostal.idcodigopostal = selection.value;
        }
    });
    
    $scope.redessocialesList = RedessocialesResource.queryAll(function(items){
        $scope.redessocialesSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idredessociales,
                text : item.idredessociales
            });
        });
    });
    $scope.$watch("redessocialesSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.usuario.redessociales = {};
            $scope.usuario.redessociales.idredessociales = selection.value;
        }
    });
    
    $scope.rolesList = RolesResource.queryAll(function(items){
        $scope.rolesSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idrol,
                text : item.idrol
            });
        });
    });
    $scope.$watch("rolesSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.usuario.roles = {};
            $scope.usuario.roles.idrol = selection.value;
        }
    });
    
    $scope.bloqueadoList = [
        "true",
        "false"
    ];

    $scope.activoList = [
        "true",
        "false"
    ];

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
            $scope.usuario.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.usuario.entradas.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The usuario was created successfully.'});
            $location.path('/Usuarios');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        UsuarioResource.save($scope.usuario, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Usuarios");
    };
});