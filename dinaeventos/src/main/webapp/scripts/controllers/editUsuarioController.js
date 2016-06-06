

angular.module('dinaeventos').controller('EditUsuarioController', function($scope, $routeParams, $location, flash, UsuarioResource , GlobalCodigopostalResource, RedessocialesResource, RolesResource, SexoResource, EntradaResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.usuario = new UsuarioResource(self.original);
            GlobalCodigopostalResource.queryAll(function(items) {
                $scope.globalCodigopostalSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idcodigopostal : item.idcodigopostal
                    };
                    var labelObject = {
                        value : item.idcodigopostal,
                        text : item.idcodigopostal
                    };
                    if($scope.usuario.globalCodigopostal && item.idcodigopostal == $scope.usuario.globalCodigopostal.idcodigopostal) {
                        $scope.globalCodigopostalSelection = labelObject;
                        $scope.usuario.globalCodigopostal = wrappedObject;
                        self.original.globalCodigopostal = $scope.usuario.globalCodigopostal;
                    }
                    return labelObject;
                });
            });
            RedessocialesResource.queryAll(function(items) {
                $scope.redessocialesSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idredessociales : item.idredessociales
                    };
                    var labelObject = {
                        value : item.idredessociales,
                        text : item.idredessociales
                    };
                    if($scope.usuario.redessociales && item.idredessociales == $scope.usuario.redessociales.idredessociales) {
                        $scope.redessocialesSelection = labelObject;
                        $scope.usuario.redessociales = wrappedObject;
                        self.original.redessociales = $scope.usuario.redessociales;
                    }
                    return labelObject;
                });
            });
            RolesResource.queryAll(function(items) {
                $scope.rolesSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idrol : item.idrol
                    };
                    var labelObject = {
                        value : item.idrol,
                        text : item.idrol
                    };
                    if($scope.usuario.roles && item.idrol == $scope.usuario.roles.idrol) {
                        $scope.rolesSelection = labelObject;
                        $scope.usuario.roles = wrappedObject;
                        self.original.roles = $scope.usuario.roles;
                    }
                    return labelObject;
                });
            });
            SexoResource.queryAll(function(items) {
                $scope.sexoSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idsexo : item.idsexo
                    };
                    var labelObject = {
                        value : item.idsexo,
                        text : item.idsexo
                    };
                    if($scope.usuario.sexo && item.idsexo == $scope.usuario.sexo.idsexo) {
                        $scope.sexoSelection = labelObject;
                        $scope.usuario.sexo = wrappedObject;
                        self.original.sexo = $scope.usuario.sexo;
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
                    if($scope.usuario.entradas){
                        $.each($scope.usuario.entradas, function(idx, element) {
                            if(item.identrada == element.identrada) {
                                $scope.entradasSelection.push(labelObject);
                                $scope.usuario.entradas.push(wrappedObject);
                            }
                        });
                        self.original.entradas = $scope.usuario.entradas;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The usuario could not be found.'});
            $location.path("/Usuarios");
        };
        UsuarioResource.get({UsuarioId:$routeParams.UsuarioId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.usuario);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The usuario was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.usuario.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Usuarios");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The usuario was deleted.'});
            $location.path("/Usuarios");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.usuario.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("globalCodigopostalSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.usuario.globalCodigopostal = {};
            $scope.usuario.globalCodigopostal.idcodigopostal = selection.value;
        }
    });
    $scope.$watch("redessocialesSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.usuario.redessociales = {};
            $scope.usuario.redessociales.idredessociales = selection.value;
        }
    });
    $scope.$watch("rolesSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.usuario.roles = {};
            $scope.usuario.roles.idrol = selection.value;
        }
    });
    $scope.$watch("sexoSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.usuario.sexo = {};
            $scope.usuario.sexo.idsexo = selection.value;
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
    $scope.entradasSelection = $scope.entradasSelection || [];
    $scope.$watch("entradasSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.usuario) {
            $scope.usuario.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.usuario.entradas.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});