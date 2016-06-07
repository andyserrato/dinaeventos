

angular.module('dinaeventos').controller('EditDdSexoController', function($scope, $routeParams, $location, flash, DdSexoResource , UsuarioResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.ddSexo = new DdSexoResource(self.original);
            UsuarioResource.queryAll(function(items) {
                $scope.usuariosSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idusuario : item.idusuario
                    };
                    var labelObject = {
                        value : item.idusuario,
                        text : item.idusuario
                    };
                    if($scope.ddSexo.usuarios){
                        $.each($scope.ddSexo.usuarios, function(idx, element) {
                            if(item.idusuario == element.idusuario) {
                                $scope.usuariosSelection.push(labelObject);
                                $scope.ddSexo.usuarios.push(wrappedObject);
                            }
                        });
                        self.original.usuarios = $scope.ddSexo.usuarios;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The ddSexo could not be found.'});
            $location.path("/DdSexos");
        };
        DdSexoResource.get({DdSexoId:$routeParams.DdSexoId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.ddSexo);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The ddSexo was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.ddSexo.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/DdSexos");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The ddSexo was deleted.'});
            $location.path("/DdSexos");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.ddSexo.$remove(successCallback, errorCallback);
    };
    
    $scope.usuariosSelection = $scope.usuariosSelection || [];
    $scope.$watch("usuariosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.ddSexo) {
            $scope.ddSexo.usuarios = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idusuario = selectedItem.value;
                $scope.ddSexo.usuarios.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});