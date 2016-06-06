
angular.module('dinaeventos').controller('NewPatrocinadorController', function ($scope, $location, locationParser, flash, PatrocinadorResource , GlobalCodigopostalResource, EventoResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.patrocinador = $scope.patrocinador || {};
    
    $scope.globalCodigopostalList = GlobalCodigopostalResource.queryAll(function(items){
        $scope.globalCodigopostalSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idcodigopostal,
                text : item.idcodigopostal
            });
        });
    });
    $scope.$watch("globalCodigopostalSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.patrocinador.globalCodigopostal = {};
            $scope.patrocinador.globalCodigopostal.idcodigopostal = selection.value;
        }
    });
    
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
            $scope.patrocinador.eventos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idevento = selectedItem.value;
                $scope.patrocinador.eventos.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The patrocinador was created successfully.'});
            $location.path('/Patrocinadors');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        PatrocinadorResource.save($scope.patrocinador, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Patrocinadors");
    };
});