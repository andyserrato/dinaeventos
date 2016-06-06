angular.module('dinaeventos').factory('PatrocinadorResource', function($resource){
    var resource = $resource('rest/patrocinadors/:PatrocinadorId',{PatrocinadorId:'@idpatrocinador'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});