(function (ng) {
    //variable que define el modulo actual
    var mod = ng.module("usuarioModule", ["loginModule"]);
    //se determine el contexto de usuarios
    mod.constant("usuarioContext","Stark/usuarios");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/usuario/';
            $urlRouterProvider.otherwise("usuariosList");
            //Estados: 
            //usuarios - estado base
            //usuarioDetail - ver un usuario
            //usuariosList - todos los usuarios en lista
            //updateUsuario - actualizar un usuario
            //adminsDeGrupo - ver los administradores de un grupo
            //miembrosDeGrupo ver todos los miembros de un grupo
            //createUsuario - crear un usuario nuevo
            $stateProvider.state('usuarios', {
                url: '/usuarios',
                abstract: true,
                views: {
                    'mainView': {
                        controller: 'usuarioCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'usuario.html'
                    }
                },
                data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('usuarioDetail',{
                url: '/{usuarioId}/detail',
                parent: 'usuarios',
                param:{
                    usuarioId: null
                },
                views: {
                    'detailView': {
                        controller: 'usuarioCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'usuario.detail.html'
                    }
                }
            }).state('usuariosList',{
                url: '/list',
                parent: 'usuarios',
                views: {
                    'listView': {
                        templateUrl: basePath + 'usuarios.list.html',
                    }
                }
            }).state('updateUsuario',{
                url: '/update/{usuarioId}',
                parent: 'usuarios',
                param:{
                    usuarioId: null
                },
                views: {
                    'detailView':{
                        templateUrl: basePath + 'new/usuario.new.html',
                        controller: 'usuarioUpdateCtrl'
                    }
                }
            }).state('asociarAdmins', {
                //Estado que permite seleccionar categorías para asociar a un grupo
                url: '/administradores/asociar',
                parent:'grupoDetail',
                views: {
                    'childrenView':{
                        templateUrl: basePath + 'deGrupo/adminsNoMios.list.html'
                    }
                }
            }).state('adminsDeGrupo', {
                url: '/administradores',
                parent:'grupoDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'admins.list.html'
                    }
                }
            }).state('miembrosDeGrupo', {
                url: '/miembros',
                parent:'grupoDetail',
                params:{
                    grupoId:null
                },
                views: {
                    'childrenView': {
                        controller: 'grupoCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'miembros.list.html'
                    }
                }
            }).state('createUsuario',{
                url: '/create',
                parent: 'usuarios',
                views: {
                    'detailView':{
                        templateUrl: basePath + 'new/usuario.new.html',
                        controller: 'usuarioNewCtrl'
                    }
                }
            })
        }]);
    
})(window.angular);
