(function (ng) {
    var mod = ng.module("grupoModule", []);
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/grupo/';
             var basePathCategorias = 'src/modules/categoria/';
            $urlRouterProvider.otherwise("/grupos");
            
    
            $stateProvider.state('grupos', {
                url: '/grupos',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'grupos.html',
                        controller: 'grupoCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('listaGrupos', {
                url: '/list',
                parent: 'grupos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'grupos.list.html'
                    }
                }
            }).state('grupoDetail', {
                url: '/{grupoId:int}/detail',
                parent: 'grupos',
                param: {
                    grupoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'grupos.detail.html',
                        controller: 'grupoCtrl',
                        controllerAs: 'ctrl'
                    },
                    'listView': {
                        templateUrl: basePathCategorias + 'categorias.list.html',
                        controller: 'grupoCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('grupoCreate', {
                url: '/create',
                parent: 'grupos',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'new/grupos.new.html',
                        controller: 'grupoNewCtrl'
                    }
                }
            }).state('grupoUpdate', {
                url: '/update/{grupoId:int}',
                parent: 'grupos',
                param: {
                    grupoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'new/grupos.new.html',
                        controller: 'grupoUpdateCtrl'
                    }
                }
            }).state('grupoDelete', {
                url: '/delete/{grupoId:int}',
                parent: 'grupos',
                param: {
                    grupoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'delete/grupos.delete.html',
                        controller: 'grupoDeleteCtrl'
                    }
                }
            });
        }]);
})(window.angular);
