angular.module('dinaeventos').factory('SexoResource', function($resource){
    var resource = $resource('rest/sexos/:SexoId',{SexoId:'@idsexo'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});