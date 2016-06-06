angular.module('dinaeventos').factory('EntradaResource', function($resource){
    var resource = $resource('rest/entradas/:EntradaId',{EntradaId:'@identrada'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});