(function (ng) {
    // Definición del módulo
    var mod = ng.module("empresaModule", ['usuarioModule', 'ui.router']);

    // Configuración de los estados del módulo
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            // En basePath se encuentran los templates y controladores de módulo
            var basePath = 'src/modules/empresa/';
            // Mostrar la lista de empresas será el estado por defecto del módulo
            //Estados:
            //empresas - estado abstracto que funciona como base para mostrar todas las empresas
            //empresasList - Lista de las empresas en el sistema
            //empresasUsuario - Estado abstracto que funciona como base para estados relacionados con empresa de un usuario.
            //empresaDetail - Detalle de la empresa de un usuario.
            //empresasCreate - Crear una empresa para un usuario.
            //empresaUpdate - Modificar la empresa de un usuario.
            //empresaDelete - Borrar la empresa de un usuario.
            //empresaNotFound - Estado asociado a cuando un usuario no tiene una empresa asociada.
            $stateProvider.state('empresas', {
                // Url que aparecerá en el browser
                url: '/empresas',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'empresas.html',
                        controller: 'empresaListCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: []
                }
            }).state('empresasList', {
                // Url que aparecerá en el browser
                url: '/empresas',
                parent: 'empresas',
                views: {
                    'listView': {
                        templateUrl: basePath + 'empresas.list.html',
                        controller: 'empresaListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('empresasUsuario', {
                // Url que aparecerá en el browser
                url: '/empresa',
                abstract: true,
                parent: 'usuarioDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'empresas.html',
                        controller: 'empresaCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: []
                }
            }).state('empresaDetail', {
                url: '/empresa',
                parent: 'empresasUsuario',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'empresas.detail.html',
                        controller: 'empresaCtrl',
                        controllerAs: 'ctrl'
                    }

                }

            }).state('empresasCreate', {
                url: '/create',
                parent: 'empresasUsuario',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'empresas.new.html',
                        controller: 'empresaNewCtrl'
                    }
                }
            }).state('empresaUpdate', {
                url: '/update',
                parent: 'empresasUsuario',
                param: {
                    nitEmpresa: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'empresas.update.html',
                        controller: 'empresaUpdateCtrl'
                    }
                }
            }).state('empresaDelete', {
                url: '/delete',
                parent: 'empresasUsuario',
                param: {
                    nitEmpresa: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'empresas.delete.html',
                        controller: 'empresaDeleteCtrl'
                    }
                }
            }).state('empresaNotFound', {
                url: '/empresa404',
                parent: 'empresasUsuario',
                param: {
                    nitEmpresa: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'empresas.404.html',
                        controller: 'empresaCtrl',
                        controllerAs: 'ctrl'
                    }

                }

            });
        }
    ]);
})(window.angular);