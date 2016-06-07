angular.module('dinaeventos').factory('DdSexoResource', function($resource){
    var resource = $resource('rest/ddsexos/:DdSexoId',{DdSexoId:'@idsexo'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});