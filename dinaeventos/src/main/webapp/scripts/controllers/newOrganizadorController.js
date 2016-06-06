
angular.module('dinaeventos').controller('NewOrganizadorController', function ($scope, $location, locationParser, flash, OrganizadorResource , GlobalCodigopostalResource, RrppJefeResource, EventoResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.organizador = $scope.organizador || {};
    
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
            $scope.organizador.globalCodigopostal = {};
            $scope.organizador.globalCodigopostal.idcodigopostal = selection.value;
        }
    });
    
    $scope.rrppJefesList = RrppJefeResource.queryAll(function(items){
        $scope.rrppJefesSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idrrppJefe,
                text : item.idrrppJefe
            });
        });
    });
    $scope.$watch("rrppJefesSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.organizador.rrppJefes = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idrrppJefe = selectedItem.value;
                $scope.organizador.rrppJefes.push(collectionItem);
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
            $scope.organizador.eventos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idevento = selectedItem.value;
                $scope.organizador.eventos.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The organizador was created successfully.'});
            $location.path('/Organizadors');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        OrganizadorResource.save($scope.organizador, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Organizadors");
    };
});