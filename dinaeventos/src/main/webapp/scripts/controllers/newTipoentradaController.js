
angular.module('dinaeventos').controller('NewTipoentradaController', function ($scope, $location, locationParser, flash, TipoentradaResource , EntradaResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.tipoentrada = $scope.tipoentrada || {};
    
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
            $scope.tipoentrada.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.tipoentrada.entradas.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The tipoentrada was created successfully.'});
            $location.path('/Tipoentradas');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        TipoentradaResource.save($scope.tipoentrada, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Tipoentradas");
    };
});