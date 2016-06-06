

angular.module('dinaeventos').controller('EditGlobalCodigopostalController', function($scope, $routeParams, $location, flash, GlobalCodigopostalResource , GlobalProvinciaResource, PatrocinadorResource, UsuarioResource, EventoResource, OrganizadorResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.globalCodigopostal = new GlobalCodigopostalResource(self.original);
            GlobalProvinciaResource.queryAll(function(items) {
                $scope.globalProvinciaSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idprovincia : item.idprovincia
                    };
                    var labelObject = {
                        value : item.idprovincia,
                        text : item.idprovincia
                    };
                    if($scope.globalCodigopostal.globalProvincia && item.idprovincia == $scope.globalCodigopostal.globalProvincia.idprovincia) {
                        $scope.globalProvinciaSelection = labelObject;
                        $scope.globalCodigopostal.globalProvincia = wrappedObject;
                        self.original.globalProvincia = $scope.globalCodigopostal.globalProvincia;
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
                    if($scope.globalCodigopostal.patrocinadors){
                        $.each($scope.globalCodigopostal.patrocinadors, function(idx, element) {
                            if(item.idpatrocinador == element.idpatrocinador) {
                                $scope.patrocinadorsSelection.push(labelObject);
                                $scope.globalCodigopostal.patrocinadors.push(wrappedObject);
                            }
                        });
                        self.original.patrocinadors = $scope.globalCodigopostal.patrocinadors;
                    }
                    return labelObject;
                });
            });
            UsuarioResource.queryAll(function(items) {
                $scope.usuariosSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idusuario : item.idusuario
                    };
                    var labelObject = {
                        value : item.idusuario,
                        text : item.idusuario
                    };
                    if($scope.globalCodigopostal.usuarios){
                        $.each($scope.globalCodigopostal.usuarios, function(idx, element) {
                            if(item.idusuario == element.idusuario) {
                                $scope.usuariosSelection.push(labelObject);
                                $scope.globalCodigopostal.usuarios.push(wrappedObject);
                            }
                        });
                        self.original.usuarios = $scope.globalCodigopostal.usuarios;
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
                    if($scope.globalCodigopostal.eventos){
                        $.each($scope.globalCodigopostal.eventos, function(idx, element) {
                            if(item.idevento == element.idevento) {
                                $scope.eventosSelection.push(labelObject);
                                $scope.globalCodigopostal.eventos.push(wrappedObject);
                            }
                        });
                        self.original.eventos = $scope.globalCodigopostal.eventos;
                    }
                    return labelObject;
                });
            });
            OrganizadorResource.queryAll(function(items) {
                $scope.organizadorsSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idorganizador : item.idorganizador
                    };
                    var labelObject = {
                        value : item.idorganizador,
                        text : item.idorganizador
                    };
                    if($scope.globalCodigopostal.organizadors){
                        $.each($scope.globalCodigopostal.organizadors, function(idx, element) {
                            if(item.idorganizador == element.idorganizador) {
                                $scope.organizadorsSelection.push(labelObject);
                                $scope.globalCodigopostal.organizadors.push(wrappedObject);
                            }
                        });
                        self.original.organizadors = $scope.globalCodigopostal.organizadors;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The globalCodigopostal could not be found.'});
            $location.path("/GlobalCodigopostals");
        };
        GlobalCodigopostalResource.get({GlobalCodigopostalId:$routeParams.GlobalCodigopostalId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.globalCodigopostal);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The globalCodigopostal was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.globalCodigopostal.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/GlobalCodigopostals");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The globalCodigopostal was deleted.'});
            $location.path("/GlobalCodigopostals");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.globalCodigopostal.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("globalProvinciaSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.globalCodigopostal.globalProvincia = {};
            $scope.globalCodigopostal.globalProvincia.idprovincia = selection.value;
        }
    });
    $scope.patrocinadorsSelection = $scope.patrocinadorsSelection || [];
    $scope.$watch("patrocinadorsSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.globalCodigopostal) {
            $scope.globalCodigopostal.patrocinadors = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idpatrocinador = selectedItem.value;
                $scope.globalCodigopostal.patrocinadors.push(collectionItem);
            });
        }
    });
    $scope.usuariosSelection = $scope.usuariosSelection || [];
    $scope.$watch("usuariosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.globalCodigopostal) {
            $scope.globalCodigopostal.usuarios = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idusuario = selectedItem.value;
                $scope.globalCodigopostal.usuarios.push(collectionItem);
            });
        }
    });
    $scope.eventosSelection = $scope.eventosSelection || [];
    $scope.$watch("eventosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.globalCodigopostal) {
            $scope.globalCodigopostal.eventos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idevento = selectedItem.value;
                $scope.globalCodigopostal.eventos.push(collectionItem);
            });
        }
    });
    $scope.organizadorsSelection = $scope.organizadorsSelection || [];
    $scope.$watch("organizadorsSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.globalCodigopostal) {
            $scope.globalCodigopostal.organizadors = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idorganizador = selectedItem.value;
                $scope.globalCodigopostal.organizadors.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});