angular.module('dinaeventos').factory('PermisosResource', function($resource){
    var resource = $resource('rest/permisos/:PermisosId',{PermisosId:'@idpermisos'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});