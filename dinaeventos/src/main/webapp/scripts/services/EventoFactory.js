angular.module('dinaeventos').factory('EventoResource', function($resource){
    var resource = $resource('rest/eventos/:EventoId',{EventoId:'@idevento'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});