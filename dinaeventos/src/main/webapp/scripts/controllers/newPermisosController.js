
angular.module('dinaeventos').controller('NewPermisosController', function ($scope, $location, locationParser, flash, PermisosResource , RolesResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.permisos = $scope.permisos || {};
    
    $scope.rolesesList = RolesResource.queryAll(function(items){
        $scope.rolesesSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idrol,
                text : item.idrol
            });
        });
    });
    $scope.$watch("rolesesSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.permisos.roleses = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idrol = selectedItem.value;
                $scope.permisos.roleses.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The permisos was created successfully.'});
            $location.path('/Permisos');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        PermisosResource.save($scope.permisos, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Permisos");
    };
});