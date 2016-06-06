angular.module('dinaeventos').factory('DdTiposIvaResource', function($resource){
    var resource = $resource('rest/ddtiposivas/:DdTiposIvaId',{DdTiposIvaId:'@idtipoiva'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});