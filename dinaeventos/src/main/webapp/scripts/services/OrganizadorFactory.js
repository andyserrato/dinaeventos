angular.module('dinaeventos').factory('OrganizadorResource', function($resource){
    var resource = $resource('rest/organizadors/:OrganizadorId',{OrganizadorId:'@idorganizador'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});