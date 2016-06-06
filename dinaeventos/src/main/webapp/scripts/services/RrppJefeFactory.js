angular.module('dinaeventos').factory('RrppJefeResource', function($resource){
    var resource = $resource('rest/rrppjeves/:RrppJefeId',{RrppJefeId:'@idrrppJefe'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});