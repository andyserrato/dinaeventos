

angular.module('dinaeventos').controller('EditSexoController', function($scope, $routeParams, $location, flash, SexoResource , UsuarioResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.sexo = new SexoResource(self.original);
            UsuarioResource.queryAll(function(items) {
                $scope.usuariosSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idusuario : item.idusuario
                    };
                    var labelObject = {
                        value : item.idusuario,
                        text : item.idusuario
                    };
                    if($scope.sexo.usuarios){
                        $.each($scope.sexo.usuarios, function(idx, element) {
                            if(item.idusuario == element.idusuario) {
                                $scope.usuariosSelection.push(labelObject);
                                $scope.sexo.usuarios.push(wrappedObject);
                            }
                        });
                        self.original.usuarios = $scope.sexo.usuarios;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The sexo could not be found.'});
            $location.path("/Sexos");
        };
        SexoResource.get({SexoId:$routeParams.SexoId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.sexo);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The sexo was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.sexo.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Sexos");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The sexo was deleted.'});
            $location.path("/Sexos");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.sexo.$remove(successCallback, errorCallback);
    };
    
    $scope.usuariosSelection = $scope.usuariosSelection || [];
    $scope.$watch("usuariosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.sexo) {
            $scope.sexo.usuarios = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idusuario = selectedItem.value;
                $scope.sexo.usuarios.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});