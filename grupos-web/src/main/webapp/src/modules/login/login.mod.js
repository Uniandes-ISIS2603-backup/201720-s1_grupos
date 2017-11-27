(function (ng) {
    // Definición del módulo
    var mod = ng.module("loginModule", ['ui.router']);

    // Configuración de los estados del módulo
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/login/';
            $urlRouterProvider.otherwise('/inicio');
           

            $stateProvider.state('login', {
                url: '/login',
                data: {
                    requireLogin: false
                },
                views: {
                    'mainView': {
                        templateUrl: basePath + 'login.html',
                        controller: 'loginCtrl',
                        controllerAs:'ctrl'
                    }
                }
            }).state('logout', {
                url: '/logout',
                data: {
                    requireLogin: true,
                    roles: ['Ciudadano','Administrador']    
                }
                ,
                views: {
                    'mainView': {
                        controller: 'logoutCtrl',
                        controllerAs:'ctrl'
                    }
                }
            }).state('inicio', {
                url: '/inicio',
                data: {
                    requireLogin: false,
                    roles: ['Ciudadano','Administrador']    
                }
                
            });
        }
    ]);
})(window.angular);

