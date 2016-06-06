
angular.module('dinaeventos').controller('NewGlobalProvinciaController', function ($scope, $location, locationParser, flash, GlobalProvinciaResource , GlobalCodigopostalResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.globalProvincia = $scope.globalProvincia || {};
    
    $scope.globalCodigopostalsList = GlobalCodigopostalResource.queryAll(function(items){
        $scope.globalCodigopostalsSelectionList = $.map(items, function(item) {
            return ( {
                value : item.idcodigopostal,
                text : item.idcodigopostal
            });
        });
    });
    $scope.$watch("globalCodigopostalsSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.globalProvincia.globalCodigopostals = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.idcodigopostal = selectedItem.value;
                $scope.globalProvincia.globalCodigopostals.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The globalProvincia was created successfully.'});
            $location.path('/GlobalProvincia');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        GlobalProvinciaResource.save($scope.globalProvincia, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/GlobalProvincia");
    };
});