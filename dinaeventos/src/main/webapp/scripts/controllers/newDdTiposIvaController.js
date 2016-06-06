
angular.module('dinaeventos').controller('NewDdTiposIvaController', function ($scope, $location, locationParser, flash, DdTiposIvaResource , EntradaResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.ddTiposIva = $scope.ddTiposIva || {};
    
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
            $scope.ddTiposIva.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.ddTiposIva.entradas.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The ddTiposIva was created successfully.'});
            $location.path('/DdTiposIvas');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        DdTiposIvaResource.save($scope.ddTiposIva, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/DdTiposIvas");
    };
});