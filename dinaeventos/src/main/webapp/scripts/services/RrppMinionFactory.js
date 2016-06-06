angular.module('dinaeventos').factory('RrppMinionResource', function($resource){
    var resource = $resource('rest/rrppminions/:RrppMinionId',{RrppMinionId:'@idrrppMinion'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});