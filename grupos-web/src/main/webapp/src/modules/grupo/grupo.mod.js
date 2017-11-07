/*
 * Modulo que guarda los estados de un grupo
 */
(function (ng) {
    var mod = ng.module("grupoModule", []);
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            //Paths útiles
            var basePath = 'src/modules/grupo/';
            var basePathCategorias = 'src/modules/categoria/';
            var basePathUsuarios = 'src/modules/usuario/';
            $urlRouterProvider.otherwise("/grupos");
            
            //Estado general del que los demás heredan
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
                //Estado que muestra la lista de grupos
                url: '/list',
                parent: 'grupos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'grupos.list.html'
                    }
                }
            }).state('grupoDetail', {
                //Estado que muestra un grupo detalladamente
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
                    }
                }
            }).state('asociarCategorias', {
                //Estado que permite seleccionar categorías para asociar a un grupo
                url: '/categorias/asociar',
                parent:'grupoDetail',
                views: {
                    'childrenView':{
                        templateUrl: basePathCategorias + 'deGrupo/categoriasNoMias.list.html'
                    }
                }
            }).state('asociarAdmins', {
                //Estado que permite seleccionar categorías para asociar a un grupo
                url: '/administradores/asociar',
                parent:'grupoDetail',
                views: {
                    'childrenView':{
                        templateUrl: basePathUsuarios + 'deGrupo/adminsNoMios.list.html'
                    }
                }
            }).state('categoriasDeGrupo', {
                //Estado que muestra las categorías actuales de un grupo
                url: '/categorias',
                parent:'grupoDetail',
                views: {
                    'childrenView':{
                        templateUrl: basePathCategorias + 'categorias.list.html'
                    }
                }
            }).state('grupoCreate', {
                //Estado que permite crear un grupo
                url: '/create',
                parent: 'grupos',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'new/grupos.new.html',
                        controller: 'grupoNewCtrl'
                    }
                }
            }).state('grupoUpdate', {
                //Estado que permite actualizar un grupo
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
                //Estado que permite borrar un grupo
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
