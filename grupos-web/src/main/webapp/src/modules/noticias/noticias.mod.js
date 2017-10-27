(function (ng) {
var mod = ng.module("noticiasModule", ['ui.router']);
    mod.constant("globalContext","Stark");
    mod.constant("noticiasContext", "noticias");
    mod.constant("usuarioContext","usuarios");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/noticias/';
            $urlRouterProvider.otherwise("/noticiasList");

            $stateProvider.state('noticiasList', {
                url: '/noticias',
                params:{
                    usuarioId:null,
                    grupoId:null
                },
                views: {
                    'mainView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticiasEditables.list.html'
                    }
                }
            }).state('noticiaCreate', {
                url: '/noticias/create',
                params:{
                    usuarioId:null,
                    grupoId:null
                },
                views: {
                    'mainView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.create.html'
                    }
                }

            }).state('noticiaEdit', {
                url: '/noticias/update/:noticiaId',
                params: {
                    usuarioId:null,
                    grupoId:null,
                    noticiaId: null
                },
                views: {
                    'mainView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.update.html'
                    }
                }
            }).state('noticiaDetail',{
                url:'/:noticiaId/detail',
                parama: {
                    noticiaId: null
                },
                views: {
                    'mainView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.detail.html'
                    }
                }
            }).state('noticiaNoEditableDetail',{
                url:'/noticias/:noticiaId/exhibicion',
                param: {
                    usuarioId:null,
                    grupoId:null,
                    noticiaId: null
                },
                views: {
                    'mainView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticias.detail.html'
                    }
                }
            }).state('noticiasExhibicion',{
                url:'/noticias/exhibicion',
                params:{
                    usuarioId:null,
                    grupoId:null
                },
                views: {
                    'mainView': {
                        controller: 'noticiasCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'noticiasEditables.list.html'
                    }
                }
            });
        }]);

})(window.angular);

