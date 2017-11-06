(function (ng) {
    var mod = ng.module("patrocinioModule", ['usuarioModule','ui.router']);
    //Constante global
    mod.constant("globalContext","Stark");
    //Constante de usuarios
    mod.constant("noticiaUsuarioContext","usuarios");
    //Constante de Patrocinios
    mod.constant("patrocinioContext","Stark/patrocinios");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/patrocinio/';
            $urlRouterProvider.otherwise("patrociniosList");

            $stateProvider.state('patrocinios', {
                url: '/patrocinios',
                abstract: true,
                views: {
                    'mainView': {
                        controller: 'patrocinioCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'patrocinio.html'
                    }
                }
            }).state('patrociniosList',{
                url: '/list',
                parent: 'patrocinios',
                views: {
                    'listView': {
                        templateUrl: basePath + 'patrocinios.list.html',
                    }
                }
            }).state('usuarioPatrocinios',{
                url: '/patrocinios',
                abstract: true,
                parent: 'usuarioDetail',
                views: {
                    'childrenView': {
                        controller: 'patrociniosUCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'patrocinio.html'
                    }
                }
            }).state('patrocinioListDetail', {
                url: '/list',
                parent: 'usuarioPatrocinios',
                views: {
                    'listView': {
                        templateUrl: basePath + 'patrocinios.list.html',
                        controller: 'patrociniosUCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state('patrocinioDetail', {
                url: '/{patrocinioId}/detail',
                parent: 'usuarioPatrocinios',
                param: {
                    patrocinioId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'patrocinio.detail.html',
                        controller: 'patrociniosUCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state('patrocinioCreate', {
                url: '/create',
                parent: 'usuarioPatrocinios',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'new/patrocinio.new.html',
                        controller: 'patrocinioNewCtrl'
                    }
                }
            }).state('patrocinioUpdate', {
                url: '/update/{patrocinioId}',
                parent: 'empresasUsuario',
                param: {
                    patrocinioId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'update/patrocinio.update.html',
                        controller: 'patrocinioNewCtrl'
                    }
                }
            })
        }]);
    
})(window.angular);