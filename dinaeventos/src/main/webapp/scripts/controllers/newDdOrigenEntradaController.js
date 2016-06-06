
angular.module('dinaeventos').controller('NewDdOrigenEntradaController', function ($scope, $location, locationParser, flash, DdOrigenEntradaResource , EntradaResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.ddOrigenEntrada = $scope.ddOrigenEntrada || {};
    
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
            $scope.ddOrigenEntrada.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.ddOrigenEntrada.entradas.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The ddOrigenEntrada was created successfully.'});
            $location.path('/DdOrigenEntradas');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        DdOrigenEntradaResource.save($scope.ddOrigenEntrada, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/DdOrigenEntradas");
    };
});