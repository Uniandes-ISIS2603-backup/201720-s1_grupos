(function (ng) {
    // Definición del módulo
    var mod = ng.module("empresaModule", ['usuarioModule', 'ui.router']);

    // Configuración de los estados del módulo
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            // En basePath se encuentran los templates y controladores de módulo
            var basePath = 'src/modules/empresa/';
            // Mostrar la lista de tarjetas será el estado por defecto del módulo
            $urlRouterProvider.otherwise("/empresasList");
            // Definición del estado 'tarjetasList' donde se listan las tarjetas
            $stateProvider.state('empresas', {
                // Url que aparecerá en el browser
                url: '/empresas',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'empresas.html',
                        controller: 'empresaCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('empresasList', {
                // Url que aparecerá en el browser
                url: '/empresas',
                parent: 'empresas',
                views: {
                    'listView': {
                        templateUrl: basePath + 'empresas.list.html'
                    }
                }
            }).state('empresaDetail', {
                url: '/empresa',
                parent: 'empresas',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'empresas.detail.html',
                        controller: 'empresaCtrl',
                        controllerAs: 'ctrl'
                    }

                }

            });
        }
    ]);
})(window.angular);