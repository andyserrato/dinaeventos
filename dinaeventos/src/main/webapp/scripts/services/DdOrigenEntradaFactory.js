angular.module('dinaeventos').factory('DdOrigenEntradaResource', function($resource){
    var resource = $resource('rest/ddorigenentradas/:DdOrigenEntradaId',{DdOrigenEntradaId:'@idorigenEntrada'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});