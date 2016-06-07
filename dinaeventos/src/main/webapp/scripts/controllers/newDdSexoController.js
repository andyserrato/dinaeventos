
angular.module('dinaeventos').controller('NewDdSexoController', function ($scope, $location, locationParser, flash, DdSexoResource , UsuarioResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.ddSexo = $scope.ddSexo || {};
    
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
            $scope.ddSexo.usuarios = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idusuario = selectedItem.value;
                $scope.ddSexo.usuarios.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The ddSexo was created successfully.'});
            $location.path('/DdSexos');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        DdSexoResource.save($scope.ddSexo, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/DdSexos");
    };
});