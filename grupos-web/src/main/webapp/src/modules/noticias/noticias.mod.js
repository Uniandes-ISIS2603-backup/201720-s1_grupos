(function (ng) {
var mod = ng.module("noticiasModule", ['ui.router']);
    mod.constant("globalContext","Stark");
    mod.constant("noticiasContext", "noticias");
    mod.constant("usuarioContext","usuarios");
    mod.constant("grupoContext","grupos");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/noticias/';
            $urlRouterProvider.otherwise("/noticiasList");

            $stateProvider.state('noticias', {
                url: '/noticias',
                abstract:true,
                params:{
                    usuarioId:null,
                    grupoId:null
                },
                views: {
                    'mainView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.html'
                    }
                }
            }).state('noticiasList', {
                url: '/list',
                parent:'noticias',
                params:{
                    usuarioId:null,
                    grupoId:null
                },
                views: {
                    'listView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticiasEditables.list.html'
                    }
                }
            }).state('noticiaCreate', {
                url: '/create',
                parent:'noticias',
                params:{
                    usuarioId:null,
                    grupoId:null
                },
                views: {
                    'detailView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.create.html'
                    }
                }

            }).state('noticiaEdit', {
                url: '/update/:noticiaId',
                parent:'noticias',
                params: {
                    usuarioId:null,
                    grupoId:null,
                    noticiaId: null
                },
                views: {
                    'detailView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.update.html'
                    }
                }
            }).state('noticiaDetail',{
                url:'/:noticiaId/detail',
                parent:'noticias',
                params: {
                    noticiaId: null
                },
                views: {
                    'detailView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.detail.html'
                    }
                }
            }).state('noticiaNoEditableDetail',{
                url:'/:noticiaId/exhibicion',
                parent:'noticias',
                params: {
                    usuarioId:null,
                    grupoId:null,
                    noticiaId: null
                },
                views: {
                    'detailView': {
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
                    'listView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticiasEditables.list.html'
                    }
                }
            });
        }]);

})(window.angular);

