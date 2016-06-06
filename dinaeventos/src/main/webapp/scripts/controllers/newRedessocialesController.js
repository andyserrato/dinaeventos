
angular.module('dinaeventos').controller('NewRedessocialesController', function ($scope, $location, locationParser, flash, RedessocialesResource , UsuarioResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.redessociales = $scope.redessociales || {};
    
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
            $scope.redessociales.usuarios = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idusuario = selectedItem.value;
                $scope.redessociales.usuarios.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The redessociales was created successfully.'});
            $location.path('/Redessociales');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        RedessocialesResource.save($scope.redessociales, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Redessociales");
    };
});