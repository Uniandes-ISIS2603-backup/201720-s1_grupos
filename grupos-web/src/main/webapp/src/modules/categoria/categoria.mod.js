/**
 * Modulo con los estados de categorías
 */
(function (ng) {
    var mod = ng.module("categoriaModule", ['ui.router']);
    mod.constant("categoriasContext", "Stark/categorias");
    mod.config(['$stateProvider', function ($stateProvider) {
            //Paths útiles
            var basePath = 'src/modules/categoria/';
            var basePathGrupos = 'src/modules/grupo/';
            
            //Estado general del que los demás heredan
            $stateProvider.state('categorias', {
                url: '/categorias',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'categorias.html',
                        controller: 'categoriaCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Administrador','Ciudadano']
                }
            }).state('carruselCategorias', {
                //Estado para mostrar el carrusel de las categorías
                url: '/carrusel',
                parent: 'categorias',
                views: {
                    'listView': {
                        templateUrl: basePath + 'categorias.carousel.html'
                    }
                }
            }).state('listaCategorias', {
                //Estado que muestra las categorías como una lista
                url: '/list',
                parent: 'categorias',
                views: {
                    'listView': {
                        templateUrl: basePath + 'categorias.list.html'
                    }
                }
            }).state('categoriasDeGrupo', {
                //Estado que muestra las categorías actuales de un grupo
                url: '/categorias',
                parent:'grupoDetail',
                views: {
                    'childrenView':{
                        templateUrl: basePath + 'categorias.list.html'
                    }
                }
            }).state('asociarCategorias', {
                //Estado que permite seleccionar categorías para asociar a un grupo
                url: '/categorias/asociar',
                parent:'grupoDetail',
                views: {
                    'childrenView':{
                        templateUrl: basePath + 'deGrupo/categoriasNoMias.list.html'
                    }
                }
            }).state('categoriaDetail', {
                //Estado que muestra la información detallada de una categoría
                abstract:true,
                parent: 'categorias',
                param: {
                    categoriaId: null
                },
                views: {                    
                    'detailView': {
                        templateUrl: basePath + 'categorias.detail.html',
                        controller: 'categoriaCtrl',
                        controllerAs: 'ctrl'                       
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Administrador','Ciudadano']
                }
            }).state('categoriaDetail2', {
                //Estado que muestra la información detallada de una categoría
                url: '/{categoriaId:int}/detail',
                parent: 'categoriaDetail',
                param: {
                    categoriaId: null
                },
                views:{ 
                    'listGruposView': {
                        templateUrl: basePathGrupos + 'grupos.list.html',
                        controller: 'categoriaCtrl',
                        controllerAs: 'ctrl'
                    }
                }                   
            
        }).state('categoriaCreate', {
            //Estado al crear una categoría
            url: '/create',
            parent: 'categorias',
            views: {
                'detailView': {
                    templateUrl: basePath + 'new/categorias.new.html',
                    controller: 'categoriaNewCtrl'
                }
            }
        }).state('categoriaUpdate', {
            //Estado al actualizar una categoría
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
            //Estado al borrar una categoría
            url: '/delete/{categoriaId:int}',
            parent: 'categorias',
            param: {
                categoriaId: null
            },
            views: {
                'detailView': {
                    templateUrl: basePath + 'delete/categorias.delete.html',
                    controller: 'categoriaDeleteCtrl'
                }
            }
        });
    }]);
})(window.angular);