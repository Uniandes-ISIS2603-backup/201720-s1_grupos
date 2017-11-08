(function (ng) {
    // Definición del módulo
    var mod = ng.module("tarjetaModule", ['usuarioModule', 'ui.router']);

    // Configuración de los estados del módulo
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            // En basePath se encuentran los templates y controladores de módulo
            var basePath = 'src/modules/tarjeta/';
            // Mostrar la lista de tarjetas será el estado por defecto del módulo
            $urlRouterProvider.otherwise("/tarjetasList");
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
                }
            }).state('tarjetasList', {
                // Url que aparecerá en el browser
                url: '/list',
                parent: 'tarjetas',
                views: {
                    'listView': {
                        templateUrl: basePath + 'tarjetas.list.html'
                    }
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

                }

            }).state('tarjetasCreate', {
                url: '/create',
                parent: 'tarjetas',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'tarjetas.new.html',
                        controller: 'tarjetaNewCtrl'
                    }
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
                }
            });
        }
    ]);
})(window.angular);