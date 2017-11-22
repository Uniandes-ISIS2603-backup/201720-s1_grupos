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

            //estados:
            //patrocinios - estado base
            //patrociniosList - lista de patrocinios
            //usuarioPatrocinios - estado conectado con usuarios
            //patrocinioListDetail - lista de patrocinios para un usuario x
            //patrocinioUpdate - actualizar un patrocinio
            //patrocinioCreate - crea un nuevo patrocinio
            $stateProvider.state('patrocinios', {
                url: '/patrocinios',
                abstract: true,
                views: {
                    'mainView': {
                        controller: 'patrocinioCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'patrocinio.html'
                    }
                },
                data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('patrociniosList',{
                url: '/list',
                parent: 'patrocinios',
                views: {
                    'listView': {
                        templateUrl: basePath + 'patrocinios.list.html',
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Administrador']              
                }
            }).state('usuarioPatrocinios',{
                url: '/patrocinios',
                abstract: true,
                parent: 'usuarioDetail',
                param:{
                    usuarioId:null,
                },
                views: {
                    'childrenView': {
                        controller: 'patrociniosUCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'patrocinio.html'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']              
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
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']              
                }
            }).state('patrocinioUpdate', {
                url: '/update/{patrocinioId}',
                parent: 'usuarioPatrocinios',
                param: {
                    patrocinioId: null

                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'new/patrocinio.new.html',
                        controller: 'patrocinioUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']              
                }
            }).state('patrocinioCreate', {
                url: '/create',
                parent: 'usuarioPatrocinios',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'new/patrocinio.new.html',
                        controller: 'patrocinioNewCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']              
                }
            }).state('patrociniosEvento',{
                url: '/patrocinios',
                abstract: true,
                parent: 'eventoDetail',
                views: {
                    'childrenView': {
                        controller: 'eventoPatrocinioCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'patrocinio.html'
                    }
                }
            }).state('patrocinioEventoListDetail', {
                url: '/list',
                parent: 'patrociniosEvento',
                views: {
                    'listView': {
                        templateUrl: basePath + 'patrocinios.eventos.list.html',
                        controller: 'eventoPatrocinioCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']              
                }
            }).state('eventoPatrocinioCreate', {
                url: '/create',
                parent: 'patrociniosEvento',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'new/patrocinio.evento.new.html',
                        controller: 'patrocinioEventoNewCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']              
                }
            }).state('eventoPatrocinioDelete', {
                url: '/delete/{patrocinioId}',
                parent: 'patrociniosEvento',
                param: {
                    patrocinioId : null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'delete/patrocinio.eventos.delete.html',
                        controller: 'patrocinioEventosDeleteCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']              
                }
            }).state('patrocinioDetail',{
                url: '/{patrocinioId}/detail',
                parent: 'usuarioPatrocinios',
                param: {
                    patrocinioId : null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'patrocinio.detail.html',
                        controller: 'patrocinioCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']              
                }
            }).state('patrocinioEventoDetail',{
                url: '/{patrocinioId}/detail',
                parent: 'patrociniosEvento',
                param: {
                    patrocinioId : null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'patrocinio.detail.html',
                        controller: 'eventoPatrocinioCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']              
                }
              })
        }]);
    
})(window.angular);