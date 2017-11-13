(function (ng) {
    //Módulo de la aplicación con sus respectivas dependencias
var mod = ng.module("noticiasModule", ['ui.router','grupoModule','usuarioModule']);
    //Constante global
    mod.constant("globalContext","Stark");
    //Contante de noticias
    mod.constant("noticiasContext", "noticias");
    //Constante de usuarios
    mod.constant("noticiaUsuarioContext","usuarios");
    //Constante de grupos
    mod.constant("noticiaGrupoContext","grupos");
    //Definición de la configuración con un definidor de estados ($stateProvider) y un controlador de estado predefinido ($urlRouterProvider)
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/noticias/';
            $urlRouterProvider.otherwise("/noticiasList");
            /**
             * Diferentes estados
             * noticiaNoEditableList
             * -ERRORNOTICIA
             * -usuarioNoticiaMultimediList
             * -usuarioNoticiaCreate
             * -usuarioNoticiaDelete
             * -usuarioNoticiaEdit
             * -ERRORUSUARIONOTICIA
             * -grupoNoticiaList
             * -grupoNoticiaCreate
             * -grupoNoticiaDelete
             * -grupoNoticiaDelete
             * -ERRORGRUPONOTTICIA
             * 
             */
            $stateProvider.state('noticias', {
                url: '/noticias',
                abstract:true,
                param:{
                    usuarioId:null
                },
                views: {
                    'mainView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.html'
                    }
                },
                data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('ERRORNOTICIA', {
                url: '/error',
                parent:'noticias',
                param:{
                    mensaje:null
                },
                views: {
                    'noticiaDetailView': {
                        templateUrl: basePath + 'noticias.error.html'
                    }
                }
            }).state('usuarioNoticias', {
                url: '/noticias',
                abstract:true,
                parent:'usuarioDetail',
                param:{
                    usuarioId:null
                },
                views: {
                    'childrenView': {
                        controller: 'usuarioNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.html'
                    }
                },
                data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('usuarioNoticiasList', {
                url: '/list',
                parent:'usuarioNoticias',
                param:{
                    usuarioId:null
                },
                views: {
                    'noticiaListView': {
                        controller: 'usuarioNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticiasEditables.list.html'
                    }
                }
            }).state('usuarioNoticiaDetail',{
                url:'/:noticiaId/detail',
                parent:'usuarioNoticias',
                param: {
                    usuarioId:null,
                    noticiaId: null
                },
                views: {
                    'noticiaDetailView': {
                        controller: 'usuarioNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.detail.html'
                    }
                }
            }).state('usuarioNoticiaEdit', {
                url: '/update/:noticiaId',
                parent:'usuarioNoticias',
                param: {
                    usuarioId:null,
                    noticiaId: null
                },
                views: {
                    'noticiaDetailView': {
                        controller: 'usuarioNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.update.html'
                    }
                }
            }).state('usuarioNoticiaDelete', {
                url: '/delete/:noticiaId',
                parent:'usuarioNoticias',
                param: {
                    noticiaId: null
                },
                views: {
                    'noticiaDetailView': {
                        controller: 'usuarioNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.delete.html'
                    }
                }
            }).state('ERRORUSUARIONOTICIA', {
                url: '/error',
                parent:'usuarioNoticias',
                param:{
                    mensaje:null
                },
                views: {
                    'noticiaDetailView': {
                        templateUrl: basePath + 'noticias.error.html'
                    }
                }
            }).state('grupoNoticias', {
                url: '/noticias',
                abstract:true,
                parent:'grupoDetail',
                param:{
                    grupoId:null
                },
                views: {
                    'childrenView': {
                        controller: 'grupoNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.html'
                    }
                },
                data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('grupoNoticiasList', {
                url: '/list',
                parent:'grupoNoticias',
                param:{
                    grupoId:null
                },
                views: {
                    'noticiaListView': {
                        controller: 'grupoNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticiasEditables.list.html'
                    }
                }
            }).state('grupoNoticiaDetail',{
                url:'/:noticiaId/detail',
                parent:'grupoNoticias',
                param: {
                    grupoId:null,
                    noticiaId: null
                },
                views: {
                    'noticiaDetailView': {
                        controller: 'grupoNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.detail.html'
                    }
                }
            }).state('grupoNoticiaCreate', {
                url: '/create',
                parent:'grupoNoticias',
                param:{
                    grupoId:null
                },
                views: {
                    'noticiaDetailView': {
                        controller: 'grupoNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.create.html'
                    }
                }

            }).state('grupoNoticiaEdit', {
                url: '/update/:noticiaId',
                parent:'grupoNoticias',
                param: {
                    grupoId:null,
                    noticiaId: null
                },
                views: {
                    'noticiaDetailView': {
                       controller: 'grupoNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.update.html'
                    }
                }
            }).state('grupoNoticiaDelete', {
                url: '/delete/:noticiaId',
                parent:'grupoNoticias',
                param: {
                    noticiaId: null
                },
                views: {
                    'noticiaDetailView': {
                        controller: 'grupoNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.delete.html'
                    }
                }
            }).state('ERRORGRUPONOTICIA', {
                url: '/error',
                parent:'grupoNoticias',
                param:{
                    mensaje:null
                },
                views: {
                    'noticiaDetailView': {
                        templateUrl: basePath + 'noticias.error.html'
                    }
                }
            }).state('noticiasExhibicion',{
                url:'/noticias/exhibicion',
                parent:'noticias',
                param:{
                    usuarioId:null,
                    grupoId:null
                },
                views: {
                    'noticiaListView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticiasEditables.list.html'
                    }
                }
            }).state('noticiaNoEditableDetail',{
                url:'/:noticiaId/exhibicion',
                parent:'noticias',
                param: {
                    
                },
                views: {
                    'noticiaDetailView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.detail.html'
                    }
                }
            });
        }]);

})(window.angular);

