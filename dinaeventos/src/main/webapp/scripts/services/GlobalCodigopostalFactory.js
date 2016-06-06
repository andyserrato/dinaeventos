angular.module('dinaeventos').factory('GlobalCodigopostalResource', function($resource){
    var resource = $resource('rest/globalcodigopostals/:GlobalCodigopostalId',{GlobalCodigopostalId:'@idcodigopostal'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});