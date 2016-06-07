angular.module('dinaeventos').factory('DdTipoEntradaResource', function($resource){
    var resource = $resource('rest/ddtipoentradas/:DdTipoEntradaId',{DdTipoEntradaId:'@idtipoentrada'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});