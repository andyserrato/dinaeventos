

angular.module('dinaeventos').controller('EditOrganizadorController', function($scope, $routeParams, $location, flash, OrganizadorResource , GlobalCodigopostalResource, RrppJefeResource, EventoResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.organizador = new OrganizadorResource(self.original);
            GlobalCodigopostalResource.queryAll(function(items) {
                $scope.globalCodigopostalSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idcodigopostal : item.idcodigopostal
                    };
                    var labelObject = {
                        value : item.idcodigopostal,
                        text : item.idcodigopostal
                    };
                    if($scope.organizador.globalCodigopostal && item.idcodigopostal == $scope.organizador.globalCodigopostal.idcodigopostal) {
                        $scope.globalCodigopostalSelection = labelObject;
                        $scope.organizador.globalCodigopostal = wrappedObject;
                        self.original.globalCodigopostal = $scope.organizador.globalCodigopostal;
                    }
                    return labelObject;
                });
            });
            RrppJefeResource.queryAll(function(items) {
                $scope.rrppJefesSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idrrppJefe : item.idrrppJefe
                    };
                    var labelObject = {
                        value : item.idrrppJefe,
                        text : item.idrrppJefe
                    };
                    if($scope.organizador.rrppJefes){
                        $.each($scope.organizador.rrppJefes, function(idx, element) {
                            if(item.idrrppJefe == element.idrrppJefe) {
                                $scope.rrppJefesSelection.push(labelObject);
                                $scope.organizador.rrppJefes.push(wrappedObject);
                            }
                        });
                        self.original.rrppJefes = $scope.organizador.rrppJefes;
                    }
                    return labelObject;
                });
            });
            EventoResource.queryAll(function(items) {
                $scope.eventosSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idevento : item.idevento
                    };
                    var labelObject = {
                        value : item.idevento,
                        text : item.idevento
                    };
                    if($scope.organizador.eventos){
                        $.each($scope.organizador.eventos, function(idx, element) {
                            if(item.idevento == element.idevento) {
                                $scope.eventosSelection.push(labelObject);
                                $scope.organizador.eventos.push(wrappedObject);
                            }
                        });
                        self.original.eventos = $scope.organizador.eventos;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The organizador could not be found.'});
            $location.path("/Organizadors");
        };
        OrganizadorResource.get({OrganizadorId:$routeParams.OrganizadorId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.organizador);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The organizador was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.organizador.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Organizadors");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The organizador was deleted.'});
            $location.path("/Organizadors");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.organizador.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("globalCodigopostalSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.organizador.globalCodigopostal = {};
            $scope.organizador.globalCodigopostal.idcodigopostal = selection.value;
        }
    });
    $scope.rrppJefesSelection = $scope.rrppJefesSelection || [];
    $scope.$watch("rrppJefesSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.organizador) {
            $scope.organizador.rrppJefes = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idrrppJefe = selectedItem.value;
                $scope.organizador.rrppJefes.push(collectionItem);
            });
        }
    });
    $scope.eventosSelection = $scope.eventosSelection || [];
    $scope.$watch("eventosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.organizador) {
            $scope.organizador.eventos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idevento = selectedItem.value;
                $scope.organizador.eventos.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});