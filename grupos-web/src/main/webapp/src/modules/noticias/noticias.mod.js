(function (ng) {
var mod = ng.module("noticiasModule", ['ui.router','grupoModule','usuarioModule']);
    mod.constant("globalContext","Stark");
    mod.constant("noticiasContext", "noticias");
    mod.constant("noticiaUsuarioContext","usuarios");
    mod.constant("noticiaGrupoContext","grupos");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/noticias/';
            $urlRouterProvider.otherwise("/noticiasList");

            $stateProvider.state('usuarioNoticias', {
                url: '/noticias',
                abstract:true,
                parent:'usuarioDetail',
                params:{
                    usuarioId:null
                },
                views: {
                    'mainView': {
                        controller: 'usuarioNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.html'
                    }
                }
            }).state('usuarioNoticiasList', {
                url: '/list',
                parent:'usuarioNoticias',
                params:{
                    usuarioId:null
                },
                views: {
                    'noticiaListView': {
                        controller: 'usuarioNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticiasEditables.list.html'
                    }
                }
            }).state('usuarioNoticiaCreate', {
                url: '/create',
                parent:'usuarioNoticias',
                params:{
                    usuarioId:null,
                    grupoId:null
                },
                views: {
                    'noticiaDetailView': {
                        controller: 'usuarioNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.create.html'
                    }
                }

            }).state('usuarioNoticiaEdit', {
                url: '/update/:noticiaId',
                parent:'usuarioNoticias',
                params: {
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
            }).state('usuarioNoticiaDetail',{
                url:'/:noticiaId/detail',
                parent:'usuarioNoticias',
                params: {
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
            }).state('grupoNoticias', {
                url: '/noticias',
                abstract:true,
                parent:'grupoDetail',
                params:{
                    grupoId:null
                },
                views: {
                    'childrenView': {
                        controller: 'grupoNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.html'
                    }
                }
            }).state('grupoNoticiasList', {
                url: '/list',
                parent:'grupoNoticias',
                params:{
                    grupoId:null
                },
                views: {
                    'noticiaListView': {
                        controller: 'grupoNoticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticiasEditables.list.html'
                    }
                }
            }).state('grupoNoticiaCreate', {
                url: '/create',
                parent:'grupoNoticias',
                params:{
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
                params: {
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
            }).state('grupoNoticiaDetail',{
                url:'/:noticiaId/detail',
                parent:'grupoNoticias',
                params: {
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
            }).state('noticiaNoEditableDetail',{
                url:'/:noticiaId/exhibicion',
                parent:'noticias',
                params: {
                    
                },
                views: {
                    'noticiaDetailView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.detail.html'
                    }
                }
            }).state('noticiasExhibicion',{
                url:'/noticias/exhibicion',
                parent:'noticias',
                params:{
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
            });
        }]);

})(window.angular);

