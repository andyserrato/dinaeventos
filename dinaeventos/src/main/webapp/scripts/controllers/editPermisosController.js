

angular.module('dinaeventos').controller('EditPermisosController', function($scope, $routeParams, $location, flash, PermisosResource , RolesResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.permisos = new PermisosResource(self.original);
            RolesResource.queryAll(function(items) {
                $scope.rolesesSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        idrol : item.idrol
                    };
                    var labelObject = {
                        value : item.idrol,
                        text : item.idrol
                    };
                    if($scope.permisos.roleses){
                        $.each($scope.permisos.roleses, function(idx, element) {
                            if(item.idrol == element.idrol) {
                                $scope.rolesesSelection.push(labelObject);
                                $scope.permisos.roleses.push(wrappedObject);
                            }
                        });
                        self.original.roleses = $scope.permisos.roleses;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The permisos could not be found.'});
            $location.path("/Permisos");
        };
        PermisosResource.get({PermisosId:$routeParams.PermisosId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.permisos);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The permisos was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.permisos.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Permisos");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The permisos was deleted.'});
            $location.path("/Permisos");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.permisos.$remove(successCallback, errorCallback);
    };
    
    $scope.rolesesSelection = $scope.rolesesSelection || [];
    $scope.$watch("rolesesSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.permisos) {
            $scope.permisos.roleses = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idrol = selectedItem.value;
                $scope.permisos.roleses.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});