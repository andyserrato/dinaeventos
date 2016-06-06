

angular.module('dinaeventos').controller('EditRedessocialesController', function($scope, $routeParams, $location, flash, RedessocialesResource , UsuarioResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.redessociales = new RedessocialesResource(self.original);
            UsuarioResource.queryAll(function(items) {
                $scope.usuariosSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idusuario : item.idusuario
                    };
                    var labelObject = {
                        value : item.idusuario,
                        text : item.idusuario
                    };
                    if($scope.redessociales.usuarios){
                        $.each($scope.redessociales.usuarios, function(idx, element) {
                            if(item.idusuario == element.idusuario) {
                                $scope.usuariosSelection.push(labelObject);
                                $scope.redessociales.usuarios.push(wrappedObject);
                            }
                        });
                        self.original.usuarios = $scope.redessociales.usuarios;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The redessociales could not be found.'});
            $location.path("/Redessociales");
        };
        RedessocialesResource.get({RedessocialesId:$routeParams.RedessocialesId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.redessociales);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The redessociales was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.redessociales.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Redessociales");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The redessociales was deleted.'});
            $location.path("/Redessociales");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.redessociales.$remove(successCallback, errorCallback);
    };
    
    $scope.usuariosSelection = $scope.usuariosSelection || [];
    $scope.$watch("usuariosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.redessociales) {
            $scope.redessociales.usuarios = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idusuario = selectedItem.value;
                $scope.redessociales.usuarios.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});