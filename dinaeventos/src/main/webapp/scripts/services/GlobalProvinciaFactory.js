angular.module('dinaeventos').factory('GlobalProvinciaResource', function($resource){
    var resource = $resource('rest/globalprovincia/:GlobalProvinciaId',{GlobalProvinciaId:'@idprovincia'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});