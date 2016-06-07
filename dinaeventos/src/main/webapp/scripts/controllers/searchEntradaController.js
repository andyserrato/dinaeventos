

angular.module('dinaeventos').controller('SearchEntradaController', function($scope, $http, $filter, EntradaResource , DdFormapagoResource, DdOrigenEntradaResource, DdTipoEntradaResource, DdTiposIvaResource, EventoResource, UsuarioResource) {

    $scope.search={};
    $scope.currentPage = 0;
    $scope.pageSize= 10;
    $scope.searchResults = [];
    $scope.filteredResults = [];
    $scope.pageRange = [];
    $scope.numberOfPages = function() {
        var result = Math.ceil($scope.filteredResults.length/$scope.pageSize);
        var max = (result == 0) ? 1 : result;
        $scope.pageRange = [];
        for(var ctr=0;ctr<max;ctr++) {
            $scope.pageRange.push(ctr);
        }
        return max;
    };
    $scope.ddFormapagoList = DdFormapagoResource.queryAll();
    $scope.ddOrigenEntradaList = DdOrigenEntradaResource.queryAll();
    $scope.ddTipoEntradaList = DdTipoEntradaResource.queryAll();
    $scope.ddTiposIvaList = DdTiposIvaResource.queryAll();
    $scope.eventoList = EventoResource.queryAll();
    $scope.usuarioList = UsuarioResource.queryAll();
    $scope.validadaList = [
        "true",
        "false"
    ];
    $scope.ticketgeneradoList = [
        "true",
        "false"
    ];
    $scope.activaList = [
        "true",
        "false"
    ];
    $scope.dentrofueraList = [
        "true",
        "false"
    ];
    $scope.vendidaList = [
        "true",
        "false"
    ];

    $scope.performSearch = function() {
        $scope.searchResults = EntradaResource.queryAll(function(){
            $scope.filteredResults = $filter('searchFilter')($scope.searchResults, $scope);
            $scope.currentPage = 0;
        });
    };
    
    $scope.previous = function() {
       if($scope.currentPage > 0) {
           $scope.currentPage--;
       }
    };
    
    $scope.next = function() {
       if($scope.currentPage < ($scope.numberOfPages() - 1) ) {
           $scope.currentPage++;
       }
    };
    
    $scope.setPage = function(n) {
       $scope.currentPage = n;
    };

    $scope.performSearch();
});