
angular.module('dinaeventos').controller('NewRrppJefeController', function ($scope, $location, locationParser, flash, RrppJefeResource , OrganizadorResource, RrppMinionResource, EventoResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.rrppJefe = $scope.rrppJefe || {};
    
    $scope.organizadorList = OrganizadorResource.queryAll(function(items){
        $scope.organizadorSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idorganizador,
                text : item.idorganizador
            });
        });
    });
    $scope.$watch("organizadorSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.rrppJefe.organizador = {};
            $scope.rrppJefe.organizador.idorganizador = selection.value;
        }
    });
    
    $scope.rrppMinionsList = RrppMinionResource.queryAll(function(items){
        $scope.rrppMinionsSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idrrppMinion,
                text : item.idrrppMinion
            });
        });
    });
    $scope.$watch("rrppMinionsSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.rrppJefe.rrppMinions = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idrrppMinion = selectedItem.value;
                $scope.rrppJefe.rrppMinions.push(collectionItem);
            });
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
            $scope.rrppJefe.eventos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idevento = selectedItem.value;
                $scope.rrppJefe.eventos.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The rrppJefe was created successfully.'});
            $location.path('/RrppJeves');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        RrppJefeResource.save($scope.rrppJefe, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/RrppJeves");
    };
});