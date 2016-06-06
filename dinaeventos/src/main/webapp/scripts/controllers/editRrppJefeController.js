

angular.module('dinaeventos').controller('EditRrppJefeController', function($scope, $routeParams, $location, flash, RrppJefeResource , OrganizadorResource, RrppMinionResource, EventoResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.rrppJefe = new RrppJefeResource(self.original);
            OrganizadorResource.queryAll(function(items) {
                $scope.organizadorSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idorganizador : item.idorganizador
                    };
                    var labelObject = {
                        value : item.idorganizador,
                        text : item.idorganizador
                    };
                    if($scope.rrppJefe.organizador && item.idorganizador == $scope.rrppJefe.organizador.idorganizador) {
                        $scope.organizadorSelection = labelObject;
                        $scope.rrppJefe.organizador = wrappedObject;
                        self.original.organizador = $scope.rrppJefe.organizador;
                    }
                    return labelObject;
                });
            });
            RrppMinionResource.queryAll(function(items) {
                $scope.rrppMinionsSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idrrppMinion : item.idrrppMinion
                    };
                    var labelObject = {
                        value : item.idrrppMinion,
                        text : item.idrrppMinion
                    };
                    if($scope.rrppJefe.rrppMinions){
                        $.each($scope.rrppJefe.rrppMinions, function(idx, element) {
                            if(item.idrrppMinion == element.idrrppMinion) {
                                $scope.rrppMinionsSelection.push(labelObject);
                                $scope.rrppJefe.rrppMinions.push(wrappedObject);
                            }
                        });
                        self.original.rrppMinions = $scope.rrppJefe.rrppMinions;
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
                    if($scope.rrppJefe.eventos){
                        $.each($scope.rrppJefe.eventos, function(idx, element) {
                            if(item.idevento == element.idevento) {
                                $scope.eventosSelection.push(labelObject);
                                $scope.rrppJefe.eventos.push(wrappedObject);
                            }
                        });
                        self.original.eventos = $scope.rrppJefe.eventos;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The rrppJefe could not be found.'});
            $location.path("/RrppJeves");
        };
        RrppJefeResource.get({RrppJefeId:$routeParams.RrppJefeId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.rrppJefe);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The rrppJefe was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.rrppJefe.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/RrppJeves");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The rrppJefe was deleted.'});
            $location.path("/RrppJeves");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.rrppJefe.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("organizadorSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.rrppJefe.organizador = {};
            $scope.rrppJefe.organizador.idorganizador = selection.value;
        }
    });
    $scope.rrppMinionsSelection = $scope.rrppMinionsSelection || [];
    $scope.$watch("rrppMinionsSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.rrppJefe) {
            $scope.rrppJefe.rrppMinions = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idrrppMinion = selectedItem.value;
                $scope.rrppJefe.rrppMinions.push(collectionItem);
            });
        }
    });
    $scope.eventosSelection = $scope.eventosSelection || [];
    $scope.$watch("eventosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.rrppJefe) {
            $scope.rrppJefe.eventos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idevento = selectedItem.value;
                $scope.rrppJefe.eventos.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});