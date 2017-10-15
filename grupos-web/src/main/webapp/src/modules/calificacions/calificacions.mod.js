(function (ng) {
var mod = ng.module("calificacionsModule", []);
    mod.constant("calificacionsContext", "Stark/grupos/1000000/blogs/1000000/calificaciones");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/calificacions/';
            $urlRouterProvider.otherwise("/calificacionsList");

            $stateProvider.state('calificacionsList', {
                url: '/calificacions',
                views: {
                    'mainView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.list.html'
                    }
                }
            }).state('calificacionCreate', {
                url: '/calificacions/create',
                views: {
                    'mainView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.create.html'
                    }
                }

            }).state('calificacionEdit', {
                url: '/calificacions/:calificacionId',
                param: {
                    cityId: null
                },
                views: {
                    'mainView': {
                        controller: 'calificacionsCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'calificacions.create.html'
                    }
                }
            });
        }]);

})(window.angular);

