angular.module('dinaeventos').factory('DdFormapagoResource', function($resource){
    var resource = $resource('rest/ddformapagos/:DdFormapagoId',{DdFormapagoId:'@idformapago'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});