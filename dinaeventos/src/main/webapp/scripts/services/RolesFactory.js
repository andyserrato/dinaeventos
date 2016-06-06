angular.module('dinaeventos').factory('RolesResource', function($resource){
    var resource = $resource('rest/roles/:RolesId',{RolesId:'@idrol'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});