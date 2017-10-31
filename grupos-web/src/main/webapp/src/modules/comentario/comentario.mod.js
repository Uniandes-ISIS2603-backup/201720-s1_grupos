(function (ng) {
    var mod = ng.module("comentarioModule", ['blogModule', 'ui.router','noticiasModule']);
    mod.constant("comentarioContext", "comentarios");
    mod.constant("blogContext", "blogs");
    mod.constant("grupoContext", "Stark/grupos");
    mod.constant("noticiaContext", "Stark/noticias");
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/comentario/';
            $urlRouterProvider.otherwise("/comentarioList");
            
            $stateProvider.state('comentarioList', {
                url: '/comentarios',
                parent: 'blogDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'comentario.list.html',
                        controller: 'comentarioCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioNoticiaList', {
                url: '/comentarios',
                parent: 'noticiaDetail',
                views: {
                    'comentarioView': {
                        templateUrl: basePath + 'comentario.list.html',
                        controller: 'comentarioNoticiaCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('comentarioDelete', {
                url: '/{comentarioId:int}/delete',
                parent: 'comentarioList',
                param: {
                    comentarioId: null
                },
                views: {
                    'comentarioBlogView': {
                        templateUrl: basePath + 'delete/comentario.delete.html',
                        controller: 'comentarioDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }]);
    
})(window.angular);
