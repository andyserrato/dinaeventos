
angular.module('dinaeventos').controller('NewDdTipoEventoController', function ($scope, $location, locationParser, flash, DdTipoEventoResource , EventoResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.ddTipoEvento = $scope.ddTipoEvento || {};
    
    $scope.eventosList = EventoResource.queryAll(function(items){
        $scope.eventosSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idevento,
                text : item.idevento
            });
        });
    });
    $scope.$watch("eventosSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.ddTipoEvento.eventos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idevento = selectedItem.value;
                $scope.ddTipoEvento.eventos.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The ddTipoEvento was created successfully.'});
            $location.path('/DdTipoEventos');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        DdTipoEventoResource.save($scope.ddTipoEvento, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/DdTipoEventos");
    };
});