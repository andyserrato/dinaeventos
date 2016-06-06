
angular.module('dinaeventos').controller('NewDdFormapagoController', function ($scope, $location, locationParser, flash, DdFormapagoResource , EntradaResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.ddFormapago = $scope.ddFormapago || {};
    
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
            $scope.ddFormapago.entradas = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.identrada = selectedItem.value;
                $scope.ddFormapago.entradas.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The ddFormapago was created successfully.'});
            $location.path('/DdFormapagos');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        DdFormapagoResource.save($scope.ddFormapago, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/DdFormapagos");
    };
});