angular.module('dinaeventos').factory('RedessocialesResource', function($resource){
    var resource = $resource('rest/redessociales/:RedessocialesId',{RedessocialesId:'@idredessociales'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});