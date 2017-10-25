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
                    'mainview': {
                        controller: 'usuarioCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'usuario.html'
                    }
                }
            }).state('usuarioDetail',{
                url: 'usuarios/{:usuarioId}/detail',
                parent: 'usuarios',
                param:{
                    usuarioId: null
                },
                views: {
                    'mainview': {
                        controller: 'usuariosCtrl',
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
            })
        }]);
    
})(window.angular);
