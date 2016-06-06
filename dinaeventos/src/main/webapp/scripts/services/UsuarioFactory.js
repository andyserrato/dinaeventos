angular.module('dinaeventos').factory('UsuarioResource', function($resource){
    var resource = $resource('rest/usuarios/:UsuarioId',{UsuarioId:'@idusuario'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});