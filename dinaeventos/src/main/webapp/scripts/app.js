'use strict';

angular.module('dinaeventos',['ngRoute','ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/DdFormapagos',{templateUrl:'views/DdFormapago/search.html',controller:'SearchDdFormapagoController'})
      .when('/DdFormapagos/new',{templateUrl:'views/DdFormapago/detail.html',controller:'NewDdFormapagoController'})
      .when('/DdFormapagos/edit/:DdFormapagoId',{templateUrl:'views/DdFormapago/detail.html',controller:'EditDdFormapagoController'})
      .when('/DdOrigenEntradas',{templateUrl:'views/DdOrigenEntrada/search.html',controller:'SearchDdOrigenEntradaController'})
      .when('/DdOrigenEntradas/new',{templateUrl:'views/DdOrigenEntrada/detail.html',controller:'NewDdOrigenEntradaController'})
      .when('/DdOrigenEntradas/edit/:DdOrigenEntradaId',{templateUrl:'views/DdOrigenEntrada/detail.html',controller:'EditDdOrigenEntradaController'})
      .when('/DdSexos',{templateUrl:'views/DdSexo/search.html',controller:'SearchDdSexoController'})
      .when('/DdSexos/new',{templateUrl:'views/DdSexo/detail.html',controller:'NewDdSexoController'})
      .when('/DdSexos/edit/:DdSexoId',{templateUrl:'views/DdSexo/detail.html',controller:'EditDdSexoController'})
      .when('/DdTipoEntradas',{templateUrl:'views/DdTipoEntrada/search.html',controller:'SearchDdTipoEntradaController'})
      .when('/DdTipoEntradas/new',{templateUrl:'views/DdTipoEntrada/detail.html',controller:'NewDdTipoEntradaController'})
      .when('/DdTipoEntradas/edit/:DdTipoEntradaId',{templateUrl:'views/DdTipoEntrada/detail.html',controller:'EditDdTipoEntradaController'})
      .when('/DdTipoEventos',{templateUrl:'views/DdTipoEvento/search.html',controller:'SearchDdTipoEventoController'})
      .when('/DdTipoEventos/new',{templateUrl:'views/DdTipoEvento/detail.html',controller:'NewDdTipoEventoController'})
      .when('/DdTipoEventos/edit/:DdTipoEventoId',{templateUrl:'views/DdTipoEvento/detail.html',controller:'EditDdTipoEventoController'})
      .when('/DdTiposIvas',{templateUrl:'views/DdTiposIva/search.html',controller:'SearchDdTiposIvaController'})
      .when('/DdTiposIvas/new',{templateUrl:'views/DdTiposIva/detail.html',controller:'NewDdTiposIvaController'})
      .when('/DdTiposIvas/edit/:DdTiposIvaId',{templateUrl:'views/DdTiposIva/detail.html',controller:'EditDdTiposIvaController'})
      .when('/Entradas',{templateUrl:'views/Entrada/search.html',controller:'SearchEntradaController'})
      .when('/Entradas/new',{templateUrl:'views/Entrada/detail.html',controller:'NewEntradaController'})
      .when('/Entradas/edit/:EntradaId',{templateUrl:'views/Entrada/detail.html',controller:'EditEntradaController'})
      .when('/Eventos',{templateUrl:'views/Evento/search.html',controller:'SearchEventoController'})
      .when('/Eventos/new',{templateUrl:'views/Evento/detail.html',controller:'NewEventoController'})
      .when('/Eventos/edit/:EventoId',{templateUrl:'views/Evento/detail.html',controller:'EditEventoController'})
      .when('/GlobalCodigopostals',{templateUrl:'views/GlobalCodigopostal/search.html',controller:'SearchGlobalCodigopostalController'})
      .when('/GlobalCodigopostals/new',{templateUrl:'views/GlobalCodigopostal/detail.html',controller:'NewGlobalCodigopostalController'})
      .when('/GlobalCodigopostals/edit/:GlobalCodigopostalId',{templateUrl:'views/GlobalCodigopostal/detail.html',controller:'EditGlobalCodigopostalController'})
      .when('/GlobalProvincia',{templateUrl:'views/GlobalProvincia/search.html',controller:'SearchGlobalProvinciaController'})
      .when('/GlobalProvincia/new',{templateUrl:'views/GlobalProvincia/detail.html',controller:'NewGlobalProvinciaController'})
      .when('/GlobalProvincia/edit/:GlobalProvinciaId',{templateUrl:'views/GlobalProvincia/detail.html',controller:'EditGlobalProvinciaController'})
      .when('/Organizadors',{templateUrl:'views/Organizador/search.html',controller:'SearchOrganizadorController'})
      .when('/Organizadors/new',{templateUrl:'views/Organizador/detail.html',controller:'NewOrganizadorController'})
      .when('/Organizadors/edit/:OrganizadorId',{templateUrl:'views/Organizador/detail.html',controller:'EditOrganizadorController'})
      .when('/Patrocinadors',{templateUrl:'views/Patrocinador/search.html',controller:'SearchPatrocinadorController'})
      .when('/Patrocinadors/new',{templateUrl:'views/Patrocinador/detail.html',controller:'NewPatrocinadorController'})
      .when('/Patrocinadors/edit/:PatrocinadorId',{templateUrl:'views/Patrocinador/detail.html',controller:'EditPatrocinadorController'})
      .when('/Permisos',{templateUrl:'views/Permisos/search.html',controller:'SearchPermisosController'})
      .when('/Permisos/new',{templateUrl:'views/Permisos/detail.html',controller:'NewPermisosController'})
      .when('/Permisos/edit/:PermisosId',{templateUrl:'views/Permisos/detail.html',controller:'EditPermisosController'})
      .when('/Redessociales',{templateUrl:'views/Redessociales/search.html',controller:'SearchRedessocialesController'})
      .when('/Redessociales/new',{templateUrl:'views/Redessociales/detail.html',controller:'NewRedessocialesController'})
      .when('/Redessociales/edit/:RedessocialesId',{templateUrl:'views/Redessociales/detail.html',controller:'EditRedessocialesController'})
      .when('/Roles',{templateUrl:'views/Roles/search.html',controller:'SearchRolesController'})
      .when('/Roles/new',{templateUrl:'views/Roles/detail.html',controller:'NewRolesController'})
      .when('/Roles/edit/:RolesId',{templateUrl:'views/Roles/detail.html',controller:'EditRolesController'})
      .when('/RrppJeves',{templateUrl:'views/RrppJefe/search.html',controller:'SearchRrppJefeController'})
      .when('/RrppJeves/new',{templateUrl:'views/RrppJefe/detail.html',controller:'NewRrppJefeController'})
      .when('/RrppJeves/edit/:RrppJefeId',{templateUrl:'views/RrppJefe/detail.html',controller:'EditRrppJefeController'})
      .when('/RrppMinions',{templateUrl:'views/RrppMinion/search.html',controller:'SearchRrppMinionController'})
      .when('/RrppMinions/new',{templateUrl:'views/RrppMinion/detail.html',controller:'NewRrppMinionController'})
      .when('/RrppMinions/edit/:RrppMinionId',{templateUrl:'views/RrppMinion/detail.html',controller:'EditRrppMinionController'})
      .when('/Usuarios',{templateUrl:'views/Usuario/search.html',controller:'SearchUsuarioController'})
      .when('/Usuarios/new',{templateUrl:'views/Usuario/detail.html',controller:'NewUsuarioController'})
      .when('/Usuarios/edit/:UsuarioId',{templateUrl:'views/Usuario/detail.html',controller:'EditUsuarioController'})
      .otherwise({
        redirectTo: '/'
      });
  }])
  .controller('LandingPageController', function LandingPageController() {
  })
  .controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
  });
