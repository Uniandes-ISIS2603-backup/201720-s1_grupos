(function (ng) {
    var mod = ng.module("usuarioModule", []);
    mod.constant("usuarioContext","Stark/usuarios");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/usuario/';
            $urlRouterProvider.otherwise("usuariosList");

            $stateProvider.state('usuarios', {
                url: '/usuarios',
                abstract: true,
                views: {
                    'mainView': {
                        controller: 'usuarioCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'usuario.html'
                    }
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
            }).state('adminsDeGrupo', {
                url: '/administradores',
                parent:'grupoDetail',
                params:{
                    grupoId:null
                },
                views: {
                    'childrenView': {
                        controller: 'grupoCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'usuarios.list.html'
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
