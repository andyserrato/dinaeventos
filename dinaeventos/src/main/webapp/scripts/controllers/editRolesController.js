

angular.module('dinaeventos').controller('EditRolesController', function($scope, $routeParams, $location, flash, RolesResource , UsuarioResource, PermisosResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.roles = new RolesResource(self.original);
            UsuarioResource.queryAll(function(items) {
                $scope.usuariosSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idusuario : item.idusuario
                    };
                    var labelObject = {
                        value : item.idusuario,
                        text : item.idusuario
                    };
                    if($scope.roles.usuarios){
                        $.each($scope.roles.usuarios, function(idx, element) {
                            if(item.idusuario == element.idusuario) {
                                $scope.usuariosSelection.push(labelObject);
                                $scope.roles.usuarios.push(wrappedObject);
                            }
                        });
                        self.original.usuarios = $scope.roles.usuarios;
                    }
                    return labelObject;
                });
            });
            PermisosResource.queryAll(function(items) {
                $scope.permisosesSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idpermisos : item.idpermisos
                    };
                    var labelObject = {
                        value : item.idpermisos,
                        text : item.idpermisos
                    };
                    if($scope.roles.permisoses){
                        $.each($scope.roles.permisoses, function(idx, element) {
                            if(item.idpermisos == element.idpermisos) {
                                $scope.permisosesSelection.push(labelObject);
                                $scope.roles.permisoses.push(wrappedObject);
                            }
                        });
                        self.original.permisoses = $scope.roles.permisoses;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The roles could not be found.'});
            $location.path("/Roles");
        };
        RolesResource.get({RolesId:$routeParams.RolesId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.roles);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The roles was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.roles.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Roles");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The roles was deleted.'});
            $location.path("/Roles");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.roles.$remove(successCallback, errorCallback);
    };
    
    $scope.usuariosSelection = $scope.usuariosSelection || [];
    $scope.$watch("usuariosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.roles) {
            $scope.roles.usuarios = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idusuario = selectedItem.value;
                $scope.roles.usuarios.push(collectionItem);
            });
        }
    });
    $scope.permisosesSelection = $scope.permisosesSelection || [];
    $scope.$watch("permisosesSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.roles) {
            $scope.roles.permisoses = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idpermisos = selectedItem.value;
                $scope.roles.permisoses.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});