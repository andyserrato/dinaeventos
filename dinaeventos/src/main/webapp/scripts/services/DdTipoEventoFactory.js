angular.module('dinaeventos').factory('DdTipoEventoResource', function($resource){
    var resource = $resource('rest/ddtipoeventos/:DdTipoEventoId',{DdTipoEventoId:'@idTipoEvento'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});