(function (ng) {
    var mod = ng.module("categoriaModule", ['ui.router']);
    mod.constant("categoriasContext", "Stark/categorias");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/categoria/';
            var basePathGrupos = 'src/modules/grupo/';
            $urlRouterProvider.otherwise("/categoriasList");

            $stateProvider.state('categorias', {
                url: '/categorias',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'categorias.html',
                        controller: 'categoriaCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('carruselCategorias', {
                url: '/carrusel',
                parent: 'categorias',
                views: {
                    'listView': {
                        templateUrl: basePath + 'categorias.carousel.html'
                    }
                }
            }).state('listaCategorias', {
                url: '/list',
                parent: 'categorias',
                views: {
                    'listView': {
                        templateUrl: basePath + 'categorias.list.html'
                    }
                }
            }).state('categoriaDetail', {
                url: '/{categoriaId:int}/detail',
                parent: 'categorias',
                param: {
                    categoriaId: null
                },
                views: {
                    'listView': {
                        templateUrl: basePathGrupos + 'grupos.list.html',
                        controller: 'categoriaCtrl',
                        controllerAs: 'ctrl'
                    },
                    'detailView': {
                        templateUrl: basePath + 'categorias.detail.html',
                        controller: 'categoriaCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('categoriaCreate', {
                url: '/create',
                parent: 'categorias',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'new/categorias.new.html',
                        controller: 'categoriaNewCtrl'
                    }
                }
            }).state('categoriaUpdate', {
                url: '/update/{categoriaId:int}',
                parent: 'categorias',
                param: {
                    categoriaId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'new/categorias.new.html',
                        controller: 'categoriaUpdateCtrl'
                    }
                }
            }).state('categoriaDelete', {
                url: '/delete/{categoriaId:int}',
                parent: 'categorias',
                param: {
                    categoriaId: null
                },
                views: {
                    //Comentario para el push al master
                    'detailView': {
                        templateUrl: basePath + 'delete/categorias.delete.html',
                        controller: 'categoriaDeleteCtrl'
                    }
                }
            });
        }]);
})(window.angular);