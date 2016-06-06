
angular.module('dinaeventos').controller('NewRolesController', function ($scope, $location, locationParser, flash, RolesResource , UsuarioResource, PermisosResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.roles = $scope.roles || {};
    
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
            $scope.roles.usuarios = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idusuario = selectedItem.value;
                $scope.roles.usuarios.push(collectionItem);
            });
        }
    });

    $scope.permisosesList = PermisosResource.queryAll(function(items){
        $scope.permisosesSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idpermisos,
                text : item.idpermisos
            });
        });
    });
    $scope.$watch("permisosesSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.roles.permisoses = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idpermisos = selectedItem.value;
                $scope.roles.permisoses.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The roles was created successfully.'});
            $location.path('/Roles');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        RolesResource.save($scope.roles, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Roles");
    };
});