(function (ng) {
    // Definición del módulo
    var mod = ng.module("tarjetaModule", ['usuarioModule', 'ui.router']);

    // Configuración de los estados del módulo
    mod.config(['$stateProvider', function ($stateProvider) {
            // En basePath se encuentran los templates y controladores de módulo
            var basePath = 'src/modules/tarjeta/';
            // Estados:
            //tarjetas - estado abstracto que funciona como padre para los demás estados
            //tarjetasList - lista de tarjetas de un usuario
            //tarjetaDetail - detalle de una de las tarjetas del usuario
            //tarjetasCreate - crear una tarjeta nueva para el usuario
            //tarjetaUpdate - actualizar una tarjeta del usuario
            //tarjetaDelete - eliminar una tarjeta del usuario
            $stateProvider.state('tarjetas', {
                // Url que aparecerá en el browser
                url: '/tarjetas',
                abstract: true,
                parent: 'usuarioDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'tarjetas.html',
                        controller: 'tarjetaCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']
                }
            }).state('tarjetasList', {
                // Url que aparecerá en el browser
                url: '/list',
                parent: 'tarjetas',
                views: {
                    'listView': {
                        templateUrl: basePath + 'tarjetas.list.html'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Administrador']
                }
            }).state('tarjetaDetail', {
                url: '/{numTarjeta:int}',
                parent: 'tarjetas',
                param: {
                    numTarjeta: null
                },
                views: {
                    'listView': {
                        templateUrl: basePath + 'tarjetas.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'tarjetas.detail.html',
                        controller: 'tarjetaCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']
                }

            }).state('tarjetasCreate', {
                url: '/create',
                parent: 'tarjetas',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'tarjetas.new.html',
                        controller: 'tarjetaNewCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']
                }
            }).state('tarjetaUpdate', {
                url: '/update/{numTarjeta:int}',
                parent: 'tarjetas',
                param: {
                    numTarjeta: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'tarjetas.update.html',
                        controller: 'tarjetaUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']
                }
            }).state('tarjetaDelete', {
                url: '/delete/{numTarjeta:int}',
                parent: 'tarjetas',
                param: {
                    numTarjeta: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'tarjetas.delete.html',
                        controller: 'tarjetaDeleteCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']
                }
            });
        }
    ]);
})(window.angular);