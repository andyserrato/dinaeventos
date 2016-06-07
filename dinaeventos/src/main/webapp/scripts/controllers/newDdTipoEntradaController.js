
angular.module('dinaeventos').controller('NewDdTipoEntradaController', function ($scope, $location, locationParser, flash, DdTipoEntradaResource , EntradaResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.ddTipoEntrada = $scope.ddTipoEntrada || {};
    
    $scope.entradasList = EntradaResource.queryAll(function(items){
        $scope.entradasSelectionList = $.map(items, function(item) {
            return ( {
                value : item.identrada,
                text : item.identrada
            });
        });
    });
    $scope.$watch("entradasSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.ddTipoEntrada.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.ddTipoEntrada.entradas.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The ddTipoEntrada was created successfully.'});
            $location.path('/DdTipoEntradas');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        DdTipoEntradaResource.save($scope.ddTipoEntrada, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/DdTipoEntradas");
    };
});