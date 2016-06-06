
angular.module('dinaeventos').controller('NewSexoController', function ($scope, $location, locationParser, flash, SexoResource , UsuarioResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.sexo = $scope.sexo || {};
    
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
            $scope.sexo.usuarios = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idusuario = selectedItem.value;
                $scope.sexo.usuarios.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The sexo was created successfully.'});
            $location.path('/Sexos');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        SexoResource.save($scope.sexo, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Sexos");
    };
});